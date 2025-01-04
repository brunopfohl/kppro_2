package cz.uhk.kppro.repository;

import cz.uhk.kppro.model.Transaction;
import cz.uhk.kppro.model.CryptoAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByCryptoAsset(CryptoAsset cryptoAsset);
    List<Transaction> findByType(Transaction.TransactionType type);
    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
