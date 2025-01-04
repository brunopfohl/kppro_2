package cz.uhk.kppro.service;

import cz.uhk.kppro.model.PriceHistory;
import cz.uhk.kppro.model.CryptoAsset;
import cz.uhk.kppro.repository.PriceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class PriceHistoryServiceImpl implements PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;

    @Autowired
    public PriceHistoryServiceImpl(PriceHistoryRepository priceHistoryRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
    }

    @Override
    public List<PriceHistory> getAllPriceHistory() {
        return priceHistoryRepository.findAll();
    }

    @Override
    public PriceHistory getPriceHistoryById(Integer id) {
        return priceHistoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<PriceHistory> getPriceHistoryByCryptoAsset(CryptoAsset cryptoAsset) {
        return priceHistoryRepository.findByCryptoAsset(cryptoAsset);
    }

    @Override
    public void savePriceHistory(PriceHistory priceHistory) {
        priceHistoryRepository.save(priceHistory);
    }

    @Override
    public void deletePriceHistory(Integer id) {
        priceHistoryRepository.deleteById(id);
    }

    @Override
    public BigDecimal getLatestPrice(CryptoAsset cryptoAsset) {
        List<PriceHistory> prices = priceHistoryRepository.findByCryptoAssetOrderByTimestampDesc(cryptoAsset);
        if (prices.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return prices.get(0).getPriceUSD();
    }

    @Override
    public BigDecimal getPriceAt(CryptoAsset cryptoAsset, LocalDateTime timestamp) {
        // Find the latest price recorded before the requested timestamp
        List<PriceHistory> prices = priceHistoryRepository
            .findFirstByCryptoAssetAndTimestampBeforeOrderByTimestampDesc(cryptoAsset, timestamp);
        
        if (prices.isEmpty()) {
            // If no earlier price found, try to get the earliest available price
            prices = priceHistoryRepository.findByCryptoAssetOrderByTimestampDesc(cryptoAsset);
            return prices.isEmpty() ? BigDecimal.ZERO : prices.get(prices.size() - 1).getPriceUSD();
        }
        
        return prices.get(0).getPriceUSD();
    }

    @Override
    public List<PriceHistory> getPriceHistoryForPeriod(CryptoAsset cryptoAsset, LocalDateTime start, LocalDateTime end) {
        return priceHistoryRepository.findByCryptoAssetAndTimestampBetween(cryptoAsset, start, end);
    }

    @Override
    public LocalDateTime getEarliestPriceDate(CryptoAsset cryptoAsset) {
        List<PriceHistory> prices = priceHistoryRepository.findByCryptoAssetOrderByTimestampDesc(cryptoAsset);
        if (prices.isEmpty()) {
            return LocalDateTime.now(); // Return current time if no prices found
        }
        return prices.stream()
                .map(PriceHistory::getTimestamp)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());
    }
}
