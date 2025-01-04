package cz.uhk.kppro.service;

import cz.uhk.kppro.model.PriceHistory;
import cz.uhk.kppro.model.CryptoAsset;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PriceHistoryService {
    List<PriceHistory> getAllPriceHistory();
    PriceHistory getPriceHistoryById(Integer id);
    List<PriceHistory> getPriceHistoryByCryptoAsset(CryptoAsset cryptoAsset);
    void savePriceHistory(PriceHistory priceHistory);
    void deletePriceHistory(Integer id);
    BigDecimal getLatestPrice(CryptoAsset cryptoAsset);
    BigDecimal getPriceAt(CryptoAsset cryptoAsset, LocalDateTime timestamp);
    List<PriceHistory> getPriceHistoryForPeriod(CryptoAsset cryptoAsset, LocalDateTime start, LocalDateTime end);
    LocalDateTime getEarliestPriceDate(CryptoAsset cryptoAsset);
}
