package cart.controller;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Transactional
@Controller
public class ViewController {

    private final ProductDao productDao;

    public ViewController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public String viewHome(final Model model) {
        List<Product> products = productDao.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String viewAdmin(final Model model) {
        final List<Product> products = productDao.findAll();
        model.addAttribute("products", products);
        return "admin";
    }
}
