package cart.controller;

import cart.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/settings")
    public String showAllAccounts(Model model) {
        model.addAttribute("accounts", accountService.searchAllAccounts());

        return "settings";
    }
}
