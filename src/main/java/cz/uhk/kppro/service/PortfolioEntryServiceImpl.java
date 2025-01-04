package cz.uhk.kppro.service;

import cz.uhk.kppro.model.PortfolioEntry;
import cz.uhk.kppro.model.CryptoAsset;
import cz.uhk.kppro.repository.PortfolioEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PortfolioEntryServiceImpl implements PortfolioEntryService {
    private final PortfolioEntryRepository portfolioEntryRepository;

    @Autowired
    private PriceHistoryService priceHistoryService;

    @Autowired
    public PortfolioEntryServiceImpl(PortfolioEntryRepository portfolioEntryRepository) {
        this.portfolioEntryRepository = portfolioEntryRepository;
    }

    @Override
    public List<PortfolioEntry> getAllPortfolioEntries() {
        return portfolioEntryRepository.findAll();
    }

    @Override
    public PortfolioEntry getPortfolioEntryById(Integer id) {
        return portfolioEntryRepository.findById(id).orElse(null);
    }

    @Override
    public List<PortfolioEntry> getPortfolioEntriesByCryptoAsset(CryptoAsset cryptoAsset) {
        return portfolioEntryRepository.findByCryptoAsset(cryptoAsset);
    }

    @Override
    public void savePortfolioEntry(PortfolioEntry portfolioEntry) {
        portfolioEntryRepository.save(portfolioEntry);
    }

    @Override
    public void deletePortfolioEntry(Integer id) {
        portfolioEntryRepository.deleteById(id);
    }

    @Override
    public BigDecimal calculateTotalPortfolioValue() {
        BigDecimal total = BigDecimal.ZERO;
        for (PortfolioEntry entry : getAllPortfolioEntries()) {
            BigDecimal latestPrice = priceHistoryService.getLatestPrice(entry.getCryptoAsset());
            total = total.add(latestPrice.multiply(entry.getQuantity()));
        }
        return total;
    }

    @Override
    public BigDecimal calculateTotalProfitLoss() {
        BigDecimal totalValue = calculateTotalPortfolioValue();
        BigDecimal totalCost = BigDecimal.ZERO;
        
        for (PortfolioEntry entry : getAllPortfolioEntries()) {
            totalCost = totalCost.add(entry.getPurchasePrice().multiply(entry.getQuantity()));
        }
        
        return totalValue.subtract(totalCost);
    }

    @Override
    public Map<String, BigDecimal> calculateAssetDistribution() {
        Map<String, BigDecimal> distribution = new HashMap<>();
        BigDecimal totalValue = calculateTotalPortfolioValue();
        
        for (PortfolioEntry entry : getAllPortfolioEntries()) {
            BigDecimal entryValue = priceHistoryService.getLatestPrice(entry.getCryptoAsset())
                .multiply(entry.getQuantity());
            BigDecimal percentage = entryValue.divide(totalValue, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            distribution.put(entry.getCryptoAsset().getSymbol(), percentage);
        }
        
        return distribution;
    }

    @Override
    public BigDecimal calculate24HourChange() {
        BigDecimal currentValue = calculateTotalPortfolioValue();
        BigDecimal previousValue = BigDecimal.ZERO;
        
        for (PortfolioEntry entry : getAllPortfolioEntries()) {
            BigDecimal oldPrice = priceHistoryService.getPriceAt(
                entry.getCryptoAsset(), 
                LocalDateTime.now().minusHours(24)
            );
            previousValue = previousValue.add(oldPrice.multiply(entry.getQuantity()));
        }
        
        // If either value is zero, return zero to avoid division by zero
        if (previousValue.compareTo(BigDecimal.ZERO) == 0 || currentValue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return currentValue.subtract(previousValue)
            .divide(previousValue, 4, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(100));
    }

    @Override
    public Map<LocalDateTime, BigDecimal> getPortfolioValueHistory(int numberOfDays) {
        Map<LocalDateTime, BigDecimal> history = new TreeMap<>();
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(numberOfDays);
        
        // Get daily snapshots
        for (LocalDateTime date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            BigDecimal totalValue = BigDecimal.ZERO;
            for (PortfolioEntry entry : getAllPortfolioEntries()) {
                BigDecimal priceAtDate = priceHistoryService.getPriceAt(entry.getCryptoAsset(), date);
                totalValue = totalValue.add(priceAtDate.multiply(entry.getQuantity()));
            }
            history.put(date, totalValue);
        }
        
        
        return history;
    }

    @Override
    public Map<LocalDateTime, BigDecimal> getFullPortfolioHistory() {
        Map<LocalDateTime, BigDecimal> history = new TreeMap<>();
        
        // Find the earliest price record date
        LocalDateTime startDate = getAllPortfolioEntries().stream()
            .map(entry -> priceHistoryService.getEarliestPriceDate(entry.getCryptoAsset()))
            .min(LocalDateTime::compareTo)
            .orElse(LocalDateTime.now().minusYears(1));
            
        LocalDateTime endDate = LocalDateTime.now();
        
        // Get daily snapshots from the earliest date to now
        for (LocalDateTime date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            BigDecimal totalValue = BigDecimal.ZERO;
            for (PortfolioEntry entry : getAllPortfolioEntries()) {
                BigDecimal priceAtDate = priceHistoryService.getPriceAt(entry.getCryptoAsset(), date);
                totalValue = totalValue.add(priceAtDate.multiply(entry.getQuantity()));
            }
            history.put(date, totalValue);
        }
        
        return history;
    }
}