package cart.controller;

import cart.domain.account.Account;
import cart.service.AccountService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountViewController {

    private final AccountService accountService;

    public AccountViewController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/settings")
    public String settingsPage(final Model model) {
        final List<Account> accounts = accountService.fetchAll();

        model.addAttribute("members", accounts);

        return "settings";
    }
}
