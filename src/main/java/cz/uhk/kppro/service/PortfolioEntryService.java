package cz.uhk.kppro.service;

import cz.uhk.kppro.model.PortfolioEntry;
import cz.uhk.kppro.model.CryptoAsset;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

public interface PortfolioEntryService {
    List<PortfolioEntry> getAllPortfolioEntries();
    PortfolioEntry getPortfolioEntryById(Integer id);
    List<PortfolioEntry> getPortfolioEntriesByCryptoAsset(CryptoAsset cryptoAsset);
    void savePortfolioEntry(PortfolioEntry portfolioEntry);
    void deletePortfolioEntry(Integer id);
    
    BigDecimal calculateTotalPortfolioValue();
    BigDecimal calculateTotalProfitLoss();
    Map<String, BigDecimal> calculateAssetDistribution();
    BigDecimal calculate24HourChange();
    Map<LocalDateTime, BigDecimal> getPortfolioValueHistory(int numberOfDays);
    Map<LocalDateTime, BigDecimal> getFullPortfolioHistory();
}
