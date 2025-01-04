package cz.uhk.kppro.controller;

import cz.uhk.kppro.service.PortfolioEntryService;
import cz.uhk.kppro.service.PriceHistoryService;
import cz.uhk.kppro.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        model.addAttribute("portfolioEntries", portfolioEntryService.getAllPortfolioEntries());
        model.addAttribute("recentTransactions", transactionService.getAllTransactions());
        // Add more data as needed for the dashboard
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
