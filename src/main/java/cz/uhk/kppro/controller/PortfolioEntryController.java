package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.PortfolioEntry;
import cz.uhk.kppro.service.PortfolioEntryService;
import cz.uhk.kppro.service.CryptoAssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/portfolio")
public class PortfolioEntryController {
    private final PortfolioEntryService portfolioEntryService;
    private final CryptoAssetService cryptoAssetService;

    @Autowired
    public PortfolioEntryController(PortfolioEntryService portfolioEntryService, 
                                  CryptoAssetService cryptoAssetService) {
        this.portfolioEntryService = portfolioEntryService;
        this.cryptoAssetService = cryptoAssetService;
    }

    @GetMapping("")
    public String listAll(Model model) {
        model.addAttribute("entries", portfolioEntryService.getAllPortfolioEntries());
        return "portfolio/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        PortfolioEntry entry = portfolioEntryService.getPortfolioEntryById(id);
        if (entry == null) {
            return "redirect:/portfolio";
        }
        model.addAttribute("entry", entry);
        return "portfolio/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("entry", new PortfolioEntry());
        model.addAttribute("cryptoAssets", cryptoAssetService.getAllCryptoAssets());
        model.addAttribute("edit", false);
        return "portfolio/edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        PortfolioEntry entry = portfolioEntryService.getPortfolioEntryById(id);
        if (entry == null) {
            return "redirect:/portfolio";
        }
        model.addAttribute("entry", entry);
        model.addAttribute("cryptoAssets", cryptoAssetService.getAllCryptoAssets());
        model.addAttribute("edit", true);
        return "portfolio/edit";
    }

    @PostMapping("/save")
    public String save(@Valid PortfolioEntry entry, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cryptoAssets", cryptoAssetService.getAllCryptoAssets());
            model.addAttribute("edit", entry.getId() != null);
            return "portfolio/edit";
        }
        portfolioEntryService.savePortfolioEntry(entry);
        return "redirect:/portfolio";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        portfolioEntryService.deletePortfolioEntry(id);
        return "redirect:/portfolio";
    }
}
