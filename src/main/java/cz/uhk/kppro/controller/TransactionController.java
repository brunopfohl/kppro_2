package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.Transaction;
import cz.uhk.kppro.service.TransactionService;
import cz.uhk.kppro.service.CryptoAssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final CryptoAssetService cryptoAssetService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                               CryptoAssetService cryptoAssetService) {
        this.transactionService = transactionService;
        this.cryptoAssetService = cryptoAssetService;
    }

    @GetMapping("")
    public String listAll(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        return "transactions/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction == null) {
            return "redirect:/transactions";
        }
        model.addAttribute("transaction", transaction);
        return "transactions/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(LocalDateTime.now());
        model.addAttribute("transaction", transaction);
        model.addAttribute("cryptoAssets", cryptoAssetService.getAllCryptoAssets());
        model.addAttribute("transactionTypes", Transaction.TransactionType.values());
        model.addAttribute("edit", false);
        return "transactions/edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction == null) {
            return "redirect:/transactions";
        }
        model.addAttribute("transaction", transaction);
        model.addAttribute("cryptoAssets", cryptoAssetService.getAllCryptoAssets());
        model.addAttribute("transactionTypes", Transaction.TransactionType.values());
        model.addAttribute("edit", true);
        return "transactions/edit";
    }

    @PostMapping("/save")
    public String save(@Valid Transaction transaction, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cryptoAssets", cryptoAssetService.getAllCryptoAssets());
            model.addAttribute("transactionTypes", Transaction.TransactionType.values());
            model.addAttribute("edit", transaction.getId() != null);
            return "transactions/edit";
        }
        transactionService.saveTransaction(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return "redirect:/transactions";
    }
}
