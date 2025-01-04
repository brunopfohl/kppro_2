package cz.uhk.kppro.repository;

import cz.uhk.kppro.model.PortfolioEntry;
import cz.uhk.kppro.model.CryptoAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioEntryRepository extends JpaRepository<PortfolioEntry, Integer> {
    List<PortfolioEntry> findByCryptoAsset(CryptoAsset cryptoAsset);
}
