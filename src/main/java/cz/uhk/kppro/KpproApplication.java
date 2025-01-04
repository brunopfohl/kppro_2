package cz.uhk.kppro;

import cz.uhk.kppro.model.CryptoAsset;
import cz.uhk.kppro.model.PriceHistory;
import cz.uhk.kppro.model.User;
import cz.uhk.kppro.service.CryptoAssetService;
import cz.uhk.kppro.service.PriceHistoryService;
import cz.uhk.kppro.service.UserService;

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

    @Autowired
    public KpproApplication(UserService userService, 
                          PasswordEncoder passwordEncoder,
                          CryptoAssetService cryptoAssetService,
                          PriceHistoryService priceHistoryService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.cryptoAssetService = cryptoAssetService;
        this.priceHistoryService = priceHistoryService;
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
            addPriceHistory(btc, "2024-01-01", new BigDecimal("42000.00"));

            addPriceHistory(eth, "2021-01-01", new BigDecimal("730.37"));
            addPriceHistory(eth, "2022-01-01", new BigDecimal("3769.70"));
            addPriceHistory(eth, "2023-01-01", new BigDecimal("1197.89"));
            addPriceHistory(eth, "2024-01-01", new BigDecimal("2300.00"));

            addPriceHistory(sol, "2021-01-01", new BigDecimal("1.84"));
            addPriceHistory(sol, "2022-01-01", new BigDecimal("178.52"));
            addPriceHistory(sol, "2023-01-01", new BigDecimal("9.96"));
            addPriceHistory(sol, "2024-01-01", new BigDecimal("98.00"));
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

    public static void main(String[] args) {
        SpringApplication.run(KpproApplication.class, args);
    }
}