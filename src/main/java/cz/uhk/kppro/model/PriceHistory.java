package cz.uhk.kppro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Crypto asset must be selected")
    @ManyToOne
    @JoinColumn(name = "crypto_asset_id", nullable = false)
    private CryptoAsset cryptoAsset;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
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
