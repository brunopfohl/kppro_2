package cz.uhk.kppro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
@Table(name = "portfolio_entries")
public class PortfolioEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "crypto_asset_id", nullable = false)
    private CryptoAsset cryptoAsset;

    @DecimalMin(value = "0.0")
    private BigDecimal quantity;

    @DecimalMin(value = "0.0")
    private BigDecimal purchasePrice;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public CryptoAsset getCryptoAsset() { return cryptoAsset; }
    public void setCryptoAsset(CryptoAsset cryptoAsset) { this.cryptoAsset = cryptoAsset; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public BigDecimal getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }
}
