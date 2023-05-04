package cart.controller;

import cart.service.AccountQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    private final AccountQueryService accountQueryService;

    public AccountController(final AccountQueryService accountQueryService) {
        this.accountQueryService = accountQueryService;
    }

    @GetMapping("/settings")
    public String showAllAccounts(Model model) {
        model.addAttribute("accounts", accountQueryService.searchAllAccounts());

        return "settings";
    }
}
