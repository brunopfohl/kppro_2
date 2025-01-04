package cz.uhk.kppro.service;

import cz.uhk.kppro.model.PriceHistory;
import cz.uhk.kppro.model.CryptoAsset;
import cz.uhk.kppro.repository.PriceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
