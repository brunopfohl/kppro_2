package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.Transaction;
import cz.uhk.kppro.service.PortfolioEntryService;
import cz.uhk.kppro.service.PriceHistoryService;
import cz.uhk.kppro.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Map;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Controller
public class IndexController {
    private final PortfolioEntryService portfolioEntryService;
    private final PriceHistoryService priceHistoryService;
    private final TransactionService transactionService;

    @Autowired
    public IndexController(PortfolioEntryService portfolioEntryService,
                         PriceHistoryService priceHistoryService,
                         TransactionService transactionService) {
        this.portfolioEntryService = portfolioEntryService;
        this.priceHistoryService = priceHistoryService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Portfolio data
        model.addAttribute("portfolioEntries", portfolioEntryService.getAllPortfolioEntries());
        model.addAttribute("totalValue", portfolioEntryService.calculateTotalPortfolioValue());
        model.addAttribute("profitLoss", portfolioEntryService.calculateTotalProfitLoss());
        model.addAttribute("change24h", portfolioEntryService.calculate24HourChange());
        
        // Get 30-day portfolio history
        Map<LocalDateTime, BigDecimal> portfolioHistory = portfolioEntryService.getPortfolioValueHistory(30);
        model.addAttribute("portfolioHistory", portfolioHistory);
        
        // Asset distribution
        model.addAttribute("assetDistribution", portfolioEntryService.calculateAssetDistribution());
        
        // Recent transactions - limit to last 5
        model.addAttribute("recentTransactions", 
            transactionService.getAllTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(5)
                .collect(Collectors.toList())
        );
        
        return "index";
    }

    @GetMapping("/403")
    @ResponseBody
    public String forbidden() {
        return "<h1 style='color: red'>Forbidden</h1>";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "<h1 style='color: green'>You are admin!</h1>";
    }
}
