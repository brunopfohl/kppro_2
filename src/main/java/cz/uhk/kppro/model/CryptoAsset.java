package cz.uhk.kppro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "crypto_assets")
public class CryptoAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Symbol cannot be empty")
    private String symbol;

    @OneToMany(mappedBy = "cryptoAsset")
    private List<PortfolioEntry> portfolioEntries;

    @OneToMany(mappedBy = "cryptoAsset")
    private List<PriceHistory> priceHistory;

    @OneToMany(mappedBy = "cryptoAsset")
    private List<Transaction> transactions;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
}
