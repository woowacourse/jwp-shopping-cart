package cart.controller;

import cart.domain.product.Product;
import cart.persistance.dao.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public final class ViewController {

    private final ProductDao productDao;

    public ViewController(final ProductDao productDao) {
        this.productDao = productDao;
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
}
