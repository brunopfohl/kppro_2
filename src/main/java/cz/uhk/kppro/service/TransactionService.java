package cz.uhk.kppro.service;

import cz.uhk.kppro.model.Transaction;
import cz.uhk.kppro.model.CryptoAsset;
import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(Integer id);
    List<Transaction> getTransactionsByCryptoAsset(CryptoAsset cryptoAsset);
    void saveTransaction(Transaction transaction);
    void deleteTransaction(Integer id);
}
