package cart.controller;

import cart.persistance.dao.MemberDao;
import cart.persistance.dao.ProductDao;
import cart.persistance.entity.product.Product;
import cart.persistance.entity.user.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public final class ViewController {

    private final ProductDao productDao;
    private final MemberDao memberDao;

    public ViewController(final ProductDao productDao, final MemberDao memberDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    @GetMapping("/")
    public String homeProductList(final Model model) {
        List<Product> products = productDao.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String adminProductList(final Model model) {
        final List<Product> products = productDao.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/cart")
    public String cartProductList() {
        return "cart";
    }

    //TODO: 다른 방법?
    @GetMapping("/settings")
    public String memberList(final Model model) {
        final List<Member> members = memberDao.findAll();
        model.addAttribute("members", members);
        return "settings";
    }
}
