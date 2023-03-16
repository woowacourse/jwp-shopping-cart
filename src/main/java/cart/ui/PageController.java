package cart.ui;

import cart.application.ProductService;
import cart.dao.CartItemDao;
import cart.dao.MemberDao;
import cart.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class PageController {
    private final ProductService productService;
    private final CartItemDao cartItemDao;
    private final MemberDao memberDao;

    public PageController(ProductService productService, CartItemDao cartItemDao, MemberDao memberDao) {
        this.productService = productService;
        this.cartItemDao = cartItemDao;
        this.memberDao = memberDao;
    }

    @GetMapping("/")
    public String list(Member member, Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/settings")
    public String members(Model model) {
        model.addAttribute("members", memberDao.getAllMembers());
        return "settings";
    }
}
