package cart.controller;

import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final ProductDao productDao;

    public AdminController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/admin")
    public String findAllProducts(final Model model) {
        List<Product> products = productDao.findAll();
        model.addAttribute("products", products);

        return "admin";
    }
}
