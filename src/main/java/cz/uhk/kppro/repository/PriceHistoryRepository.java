package cz.uhk.kppro.repository;

import cz.uhk.kppro.model.PriceHistory;
import cz.uhk.kppro.model.CryptoAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {
    List<PriceHistory> findByCryptoAsset(CryptoAsset cryptoAsset);
    List<PriceHistory> findByCryptoAssetOrderByTimestampDesc(CryptoAsset cryptoAsset);
    List<PriceHistory> findByCryptoAssetAndTimestampBetween(CryptoAsset cryptoAsset, LocalDateTime start, LocalDateTime end);
}
