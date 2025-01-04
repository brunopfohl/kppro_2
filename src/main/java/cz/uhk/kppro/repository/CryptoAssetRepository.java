package cz.uhk.kppro.repository;

import cz.uhk.kppro.model.CryptoAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CryptoAssetRepository extends JpaRepository<CryptoAsset, Integer> {
    Optional<CryptoAsset> findBySymbol(String symbol);
    List<CryptoAsset> findByNameContainingIgnoreCase(String name);
}
