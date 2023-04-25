package cart.controller;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
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
    public String loadHome(Model model) {
        final List<ProductEntity> productEntities = productDao.findAll();
        List<ProductResponse> products = ResponseMapper.from(productEntities);

        model.addAttribute("products", products);
        return "index";
    }
}
