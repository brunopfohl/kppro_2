package cz.uhk.kppro.service;

import cz.uhk.kppro.model.PortfolioEntry;
import cz.uhk.kppro.model.CryptoAsset;
import cz.uhk.kppro.repository.PortfolioEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioEntryServiceImpl implements PortfolioEntryService {
    private final PortfolioEntryRepository portfolioEntryRepository;

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
}
