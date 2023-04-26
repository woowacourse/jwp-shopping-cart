package cart.controller.view;

import cart.dao.ProductDao;
import cart.domain.Product;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final ProductDao productDao;

    public AdminController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/admin")
    String admin(final Model model) {
        final List<Product> all = productDao.findAll();
        model.addAttribute("products", all);
        return "admin";
    }
}
