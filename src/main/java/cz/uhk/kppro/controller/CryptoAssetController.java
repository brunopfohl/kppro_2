package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.CryptoAsset;
import cz.uhk.kppro.service.CryptoAssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/crypto-assets")
public class CryptoAssetController {
    private final CryptoAssetService cryptoAssetService;

    @Autowired
    public CryptoAssetController(CryptoAssetService cryptoAssetService) {
        this.cryptoAssetService = cryptoAssetService;
    }

    @GetMapping("")
    public String listAllCryptoAssets(Model model) {
        model.addAttribute("cryptoAssets", this.cryptoAssetService.getAllCryptoAssets());
        return "crypto_assets_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        CryptoAsset cryptoAsset = this.cryptoAssetService.getCryptoAssetById(id);

        if (cryptoAsset == null) {
            return "redirect:/crypto-assets";
        }

        model.addAttribute("cryptoAsset", cryptoAsset);
        return "crypto_asset_detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("cryptoAsset", new CryptoAsset());
        model.addAttribute("edit", false);
        return "crypto_asset_edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        CryptoAsset cryptoAsset = this.cryptoAssetService.getCryptoAssetById(id);

        if (cryptoAsset == null) {
            return "redirect:/crypto-assets";
        }

        model.addAttribute("cryptoAsset", cryptoAsset);
        model.addAttribute("edit", true);
        return "crypto_asset_edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        this.cryptoAssetService.deleteCryptoAsset(id);
        return "redirect:/crypto-assets";
    }

    @PostMapping("/save")
    public String save(@Valid CryptoAsset cryptoAsset, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", cryptoAsset.getId() != null);
            return "crypto_asset_edit";
        }

        this.cryptoAssetService.saveCryptoAsset(cryptoAsset);
        return "redirect:/crypto-assets";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        model.addAttribute("cryptoAssets", this.cryptoAssetService.searchCryptoAssetsByName(query));
        return "crypto_assets_list";
    }
}
