package cz.uhk.kppro;

import cz.uhk.kppro.model.CryptoAsset;
import cz.uhk.kppro.model.PortfolioEntry;
import cz.uhk.kppro.model.PriceHistory;
import cz.uhk.kppro.model.User;
import cz.uhk.kppro.service.CryptoAssetService;
import cz.uhk.kppro.service.PriceHistoryService;
import cz.uhk.kppro.service.UserService;
import cz.uhk.kppro.service.PortfolioEntryService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KpproApplication {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CryptoAssetService cryptoAssetService;
    private final PriceHistoryService priceHistoryService;
    private final PortfolioEntryService portfolioEntryService;

    @Autowired
    public KpproApplication(UserService userService, 
                          PasswordEncoder passwordEncoder,
                          CryptoAssetService cryptoAssetService,
                          PriceHistoryService priceHistoryService,
                          PortfolioEntryService portfolioEntryService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.cryptoAssetService = cryptoAssetService;
        this.priceHistoryService = priceHistoryService;
        this.portfolioEntryService = portfolioEntryService;
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            // Seed users
            addUser("admin", "heslo", "ADMIN");
            addUser("user", "heslo", "USER");

            // Seed crypto assets
            CryptoAsset btc = addCryptoAsset("Bitcoin", "BTC");
            CryptoAsset eth = addCryptoAsset("Ethereum", "ETH");
            CryptoAsset sol = addCryptoAsset("Solana", "SOL");

            // Bitcoin (BTC) Quarterly Closing Prices
            addPriceHistory(btc, "2020-03-31", new BigDecimal("6400.00"));
            addPriceHistory(btc, "2020-06-30", new BigDecimal("9100.00"));
            addPriceHistory(btc, "2020-09-30", new BigDecimal("10700.00"));
            addPriceHistory(btc, "2020-12-31", new BigDecimal("28900.00"));
            addPriceHistory(btc, "2021-03-31", new BigDecimal("59000.00"));
            addPriceHistory(btc, "2021-06-30", new BigDecimal("35000.00"));
            addPriceHistory(btc, "2021-09-30", new BigDecimal("43000.00"));
            addPriceHistory(btc, "2021-12-31", new BigDecimal("47000.00"));
            addPriceHistory(btc, "2022-03-31", new BigDecimal("45000.00"));
            addPriceHistory(btc, "2022-06-30", new BigDecimal("20000.00"));
            addPriceHistory(btc, "2022-09-30", new BigDecimal("19000.00"));
            addPriceHistory(btc, "2022-12-31", new BigDecimal("16500.00"));
            addPriceHistory(btc, "2023-03-31", new BigDecimal("28000.00"));
            addPriceHistory(btc, "2023-06-30", new BigDecimal("30000.00"));
            addPriceHistory(btc, "2023-09-30", new BigDecimal("26000.00"));
            addPriceHistory(btc, "2023-12-31", new BigDecimal("40000.00"));
            addPriceHistory(btc, "2024-03-31", new BigDecimal("48000.00"));
            addPriceHistory(btc, "2024-06-30", new BigDecimal("48000.00"));
            addPriceHistory(btc, "2024-09-30", new BigDecimal("52000.00"));
            addPriceHistory(btc, "2024-12-31", new BigDecimal("60000.00"));

            // Ethereum (ETH) Quarterly Closing Prices
            addPriceHistory(eth, "2020-03-31", new BigDecimal("110.00"));
            addPriceHistory(eth, "2020-06-30", new BigDecimal("225.00"));
            addPriceHistory(eth, "2020-09-30", new BigDecimal("360.00"));
            addPriceHistory(eth, "2020-12-31", new BigDecimal("730.00"));
            addPriceHistory(eth, "2021-03-31", new BigDecimal("1800.00"));
            addPriceHistory(eth, "2021-06-30", new BigDecimal("2100.00"));
            addPriceHistory(eth, "2021-09-30", new BigDecimal("3000.00"));
            addPriceHistory(eth, "2021-12-31", new BigDecimal("3700.00"));
            addPriceHistory(eth, "2022-03-31", new BigDecimal("3400.00"));
            addPriceHistory(eth, "2022-06-30", new BigDecimal("1000.00"));
            addPriceHistory(eth, "2022-09-30", new BigDecimal("1400.00"));
            addPriceHistory(eth, "2022-12-31", new BigDecimal("1100.00"));
            addPriceHistory(eth, "2023-03-31", new BigDecimal("1800.00"));
            addPriceHistory(eth, "2023-06-30", new BigDecimal("1900.00"));
            addPriceHistory(eth, "2023-09-30", new BigDecimal("1600.00"));
            addPriceHistory(eth, "2023-12-31", new BigDecimal("2200.00"));
            addPriceHistory(eth, "2024-03-31", new BigDecimal("2500.00"));
            addPriceHistory(eth, "2024-06-30", new BigDecimal("2800.00"));
            addPriceHistory(eth, "2024-09-30", new BigDecimal("3000.00"));
            addPriceHistory(eth, "2024-12-31", new BigDecimal("3500.00"));

            // Solana (SOL) Quarterly Closing Prices
            addPriceHistory(sol, "2020-03-31", new BigDecimal("0.50")); // Solana launched in March 2020
            addPriceHistory(sol, "2020-06-30", new BigDecimal("0.60"));
            addPriceHistory(sol, "2020-09-30", new BigDecimal("1.50"));
            addPriceHistory(sol, "2020-12-31", new BigDecimal("1.80"));
            addPriceHistory(sol, "2021-03-31", new BigDecimal("19.00"));
            addPriceHistory(sol, "2021-06-30", new BigDecimal("35.00"));
            addPriceHistory(sol, "2021-09-30", new BigDecimal("140.00"));
            addPriceHistory(sol, "2021-12-31", new BigDecimal("170.00"));
            addPriceHistory(sol, "2022-03-31", new BigDecimal("110.00"));
            addPriceHistory(sol, "2022-06-30", new BigDecimal("40.00"));
            addPriceHistory(sol, "2022-09-30", new BigDecimal("35.00"));
            addPriceHistory(sol, "2022-12-31", new BigDecimal("10.00"));
            addPriceHistory(sol, "2023-03-31", new BigDecimal("20.00"));
            addPriceHistory(sol, "2023-06-30", new BigDecimal("25.00"));
            addPriceHistory(sol, "2023-09-30", new BigDecimal("30.00"));
            addPriceHistory(sol, "2023-12-31", new BigDecimal("50.00"));
            addPriceHistory(sol, "2024-03-31", new BigDecimal("70.00"));
            addPriceHistory(sol, "2024-06-30", new BigDecimal("90.00"));
            addPriceHistory(sol, "2024-09-30", new BigDecimal("110.00"));
            addPriceHistory(sol, "2024-12-31", new BigDecimal("130.00"));


            // Add portfolio entries
            addPortfolioEntry(btc, new BigDecimal("0.5"), new BigDecimal("28500.00"));  // ~$14,250 initial investment
            addPortfolioEntry(eth, new BigDecimal("5"), new BigDecimal("1800.00"));     // ~$9,000 initial investment
            addPortfolioEntry(sol, new BigDecimal("100"), new BigDecimal("35.00"));     // ~$3,500 initial investment
        };
    }

    private void addUser(String username, String password, String role) {
        if (userService.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userService.save(user);
        }
    }

    private CryptoAsset addCryptoAsset(String name, String symbol) {
        CryptoAsset asset = cryptoAssetService.getCryptoAssetBySymbol(symbol);
        if (asset == null) {
            asset = new CryptoAsset();
            asset.setName(name);
            asset.setSymbol(symbol);
            cryptoAssetService.saveCryptoAsset(asset);
        }
        return asset;
    }

    private void addPriceHistory(CryptoAsset asset, String date, BigDecimal price) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setCryptoAsset(asset);
        priceHistory.setTimestamp(LocalDateTime.parse(date + "T00:00:00"));
        priceHistory.setPriceUSD(price);
        priceHistoryService.savePriceHistory(priceHistory);
    }

    private void addPriceHistoryForLastYear(CryptoAsset asset, BigDecimal[] prices) {
        LocalDateTime date = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
        for (BigDecimal price : prices) {
            PriceHistory priceHistory = new PriceHistory();
            priceHistory.setCryptoAsset(asset);
            priceHistory.setTimestamp(date);
            priceHistory.setPriceUSD(price);
            priceHistoryService.savePriceHistory(priceHistory);
            date = date.minusMonths(1);
        }
    }

    private void addPortfolioEntry(CryptoAsset asset, BigDecimal quantity, BigDecimal purchasePrice) {
        PortfolioEntry entry = new PortfolioEntry();
        entry.setCryptoAsset(asset);
        entry.setQuantity(quantity);
        entry.setPurchasePrice(purchasePrice);
        portfolioEntryService.savePortfolioEntry(entry);
    }

    public static void main(String[] args) {
        SpringApplication.run(KpproApplication.class, args);
    }
}