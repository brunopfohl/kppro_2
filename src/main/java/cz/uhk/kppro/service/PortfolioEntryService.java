package cz.uhk.kppro.service;

import cz.uhk.kppro.model.PortfolioEntry;
import cz.uhk.kppro.model.CryptoAsset;
import java.util.List;

public interface PortfolioEntryService {
    List<PortfolioEntry> getAllPortfolioEntries();
    PortfolioEntry getPortfolioEntryById(Integer id);
    List<PortfolioEntry> getPortfolioEntriesByCryptoAsset(CryptoAsset cryptoAsset);
    void savePortfolioEntry(PortfolioEntry portfolioEntry);
    void deletePortfolioEntry(Integer id);
}
