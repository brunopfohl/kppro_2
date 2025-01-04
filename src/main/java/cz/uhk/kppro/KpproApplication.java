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

            // Seed price history
            addPriceHistory(btc, "2021-01-01", new BigDecimal("29374.15"));
            addPriceHistory(btc, "2022-01-01", new BigDecimal("47686.81"));
            addPriceHistory(btc, "2023-01-01", new BigDecimal("16547.50"));
            addPriceHistory(btc, "2024-01-01", new BigDecimal("30000.00"));

            addPriceHistory(eth, "2021-01-01", new BigDecimal("730.37"));
            addPriceHistory(eth, "2022-01-01", new BigDecimal("3769.70"));
            addPriceHistory(eth, "2023-01-01", new BigDecimal("1197.89"));
            addPriceHistory(eth, "2024-01-01", new BigDecimal("2000.00"));

            addPriceHistory(sol, "2021-01-01", new BigDecimal("1.84"));
            addPriceHistory(sol, "2022-01-01", new BigDecimal("178.52"));
            addPriceHistory(sol, "2023-01-01", new BigDecimal("9.96"));
            addPriceHistory(sol, "2024-01-01", new BigDecimal("98.00"));

            // Add price history for the last year (monthly data)
            addPriceHistoryForLastYear(btc, new BigDecimal[] {
                    new BigDecimal("30000.00"),  // Jan 2024
                    new BigDecimal("28500.00"),  // Dec 2023
                    new BigDecimal("27000.00"),  // Nov 2023
                    new BigDecimal("25000.00"),  // Oct 2023
                    new BigDecimal("24000.00"),  // Sep 2023
                    new BigDecimal("26000.00"),  // Aug 2023
                    new BigDecimal("25500.00"),  // Jul 2023
                    new BigDecimal("24000.00"),  // Jun 2023
                    new BigDecimal("23000.00"),  // May 2023
                    new BigDecimal("22000.00"),  // Apr 2023
                    new BigDecimal("21000.00"),  // Mar 2023
                    new BigDecimal("20000.00"),  // Feb 2023
                    new BigDecimal("16547.50")   // Jan 2023
            });

            addPriceHistoryForLastYear(eth, new BigDecimal[] {
                    new BigDecimal("2000.00"),   // Jan 2024
                    new BigDecimal("1900.00"),   // Dec 2023
                    new BigDecimal("1800.00"),   // Nov 2023
                    new BigDecimal("1700.00"),   // Oct 2023
                    new BigDecimal("1600.00"),   // Sep 2023
                    new BigDecimal("1700.00"),   // Aug 2023
                    new BigDecimal("1800.00"),   // Jul 2023
                    new BigDecimal("1750.00"),   // Jun 2023
                    new BigDecimal("1600.00"),   // May 2023
                    new BigDecimal("1650.00"),   // Apr 2023
                    new BigDecimal("1500.00"),   // Mar 2023
                    new BigDecimal("1400.00"),   // Feb 2023
                    new BigDecimal("1197.89")    // Jan 2023
            });

            addPriceHistoryForLastYear(sol, new BigDecimal[] {
                    new BigDecimal("98.00"),     // Jan 2024
                    new BigDecimal("90.00"),     // Dec 2023
                    new BigDecimal("80.00"),     // Nov 2023
                    new BigDecimal("70.00"),     // Oct 2023
                    new BigDecimal("60.00"),     // Sep 2023
                    new BigDecimal("55.00"),     // Aug 2023
                    new BigDecimal("50.00"),     // Jul 2023
                    new BigDecimal("45.00"),     // Jun 2023
                    new BigDecimal("40.00"),     // May 2023
                    new BigDecimal("35.00"),     // Apr 2023
                    new BigDecimal("30.00"),     // Mar 2023
                    new BigDecimal("20.00"),     // Feb 2023
                    new BigDecimal("9.96")       // Jan 2023
            });

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