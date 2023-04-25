package cart.controller;

import cart.dao.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final ProductDao productDao;

    public MainController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("products", productDao.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        model.addAttribute("products", productDao.findAll());
        return "admin";
    }
}
