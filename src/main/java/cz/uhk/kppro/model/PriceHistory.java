package cz.uhk.kppro.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "crypto_asset_id", nullable = false)
    private CryptoAsset cryptoAsset;

    private LocalDateTime timestamp;
    private BigDecimal priceUSD;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public CryptoAsset getCryptoAsset() { return cryptoAsset; }
    public void setCryptoAsset(CryptoAsset cryptoAsset) { this.cryptoAsset = cryptoAsset; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public BigDecimal getPriceUSD() { return priceUSD; }
    public void setPriceUSD(BigDecimal priceUSD) { this.priceUSD = priceUSD; }
}
