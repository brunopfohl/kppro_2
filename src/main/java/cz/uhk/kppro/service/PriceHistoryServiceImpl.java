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
        // Find prices within a 5-minute window of the requested timestamp
        LocalDateTime start = timestamp.minusMinutes(5);
        LocalDateTime end = timestamp.plusMinutes(5);
        
        List<PriceHistory> prices = priceHistoryRepository.findByCryptoAssetAndTimestampBetween(
            cryptoAsset, start, end);
        
        if (prices.isEmpty()) {
            // If no price found in the window, get the closest earlier price
            prices = priceHistoryRepository.findByCryptoAssetOrderByTimestampDesc(cryptoAsset);
            return prices.isEmpty() ? BigDecimal.ZERO : prices.get(0).getPriceUSD();
        }
        
        // Return the price closest to the requested timestamp
        return prices.stream()
            .min((p1, p2) -> {
                long diff1 = Math.abs(p1.getTimestamp().toEpochSecond(ZoneOffset.UTC) - timestamp.toEpochSecond(ZoneOffset.UTC));
                long diff2 = Math.abs(p2.getTimestamp().toEpochSecond(ZoneOffset.UTC) - timestamp.toEpochSecond(ZoneOffset.UTC));
                return Long.compare(diff1, diff2);
            })
            .map(PriceHistory::getPriceUSD)
            .orElse(BigDecimal.ZERO);
    }

    @Override
    public List<PriceHistory> getPriceHistoryForPeriod(CryptoAsset cryptoAsset, LocalDateTime start, LocalDateTime end) {
        return priceHistoryRepository.findByCryptoAssetAndTimestampBetween(cryptoAsset, start, end);
    }
}
