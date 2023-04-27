package cart.controller;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductDao productDao;

    public HomeController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public String productList(final Model model) {
        List<Product> products = productDao.findAll();
        model.addAttribute("products", products);
        return "index";
    }
}
