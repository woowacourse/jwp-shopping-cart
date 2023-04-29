package cart.controller;

import cart.dao.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {

    private final ProductDao productDao;

    public ProductViewController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping(path = "/")
    public String home(Model model) {
        model.addAttribute("products", productDao.findAll());

        return "index";
    }

    @GetMapping(path = "/admin")
    public String admin(Model model) {
        model.addAttribute("products", productDao.findAll());

        return "admin";
    }
}
