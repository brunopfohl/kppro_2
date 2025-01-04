package cz.uhk.kppro.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    public enum TransactionType {
        BUY, SELL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "crypto_asset_id", nullable = false)
    private CryptoAsset cryptoAsset;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal quantity;
    private BigDecimal priceUSD;
    private LocalDateTime timestamp;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public CryptoAsset getCryptoAsset() { return cryptoAsset; }
    public void setCryptoAsset(CryptoAsset cryptoAsset) { this.cryptoAsset = cryptoAsset; }
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public BigDecimal getPriceUSD() { return priceUSD; }
    public void setPriceUSD(BigDecimal priceUSD) { this.priceUSD = priceUSD; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
