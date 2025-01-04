package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.PriceHistory;
import cz.uhk.kppro.service.PriceHistoryService;
import cz.uhk.kppro.service.CryptoAssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/price-history")
public class PriceHistoryController {
    private final PriceHistoryService priceHistoryService;
    private final CryptoAssetService cryptoAssetService;

    @Autowired
    public PriceHistoryController(PriceHistoryService priceHistoryService,
                                CryptoAssetService cryptoAssetService) {
        this.priceHistoryService = priceHistoryService;
        this.cryptoAssetService = cryptoAssetService;
    }

    @GetMapping("")
    public String listAll(Model model) {
        model.addAttribute("priceHistory", priceHistoryService.getAllPriceHistory());
        return "price-history/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        PriceHistory priceHistory = priceHistoryService.getPriceHistoryById(id);
        if (priceHistory == null) {
            return "redirect:/price-history";
        }
        model.addAttribute("priceHistory", priceHistory);
        return "price-history/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setTimestamp(LocalDateTime.now());  // Set current timestamp by default
        model.addAttribute("priceHistory", priceHistory);
        model.addAttribute("cryptoAssets", cryptoAssetService.getAllCryptoAssets());
        model.addAttribute("edit", false);
        return "price-history/edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        PriceHistory priceHistory = priceHistoryService.getPriceHistoryById(id);
        if (priceHistory == null) {
            return "redirect:/price-history";
        }
        model.addAttribute("priceHistory", priceHistory);
        model.addAttribute("cryptoAssets", cryptoAssetService.getAllCryptoAssets());
        model.addAttribute("edit", true);
        return "price-history/edit";
    }

    @PostMapping("/save")
    public String save(@Valid PriceHistory priceHistory, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cryptoAssets", cryptoAssetService.getAllCryptoAssets());
            model.addAttribute("edit", priceHistory.getId() != null);
            return "price-history/edit";
        }
        
        priceHistoryService.savePriceHistory(priceHistory);
        return "redirect:/price-history";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        priceHistoryService.deletePriceHistory(id);
        return "redirect:/price-history";
    }
}
