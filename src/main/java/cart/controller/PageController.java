package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final ProductDao mySQLProductDao;

    public PageController(ProductDao mySQLProductDao) {
        this.mySQLProductDao = mySQLProductDao;
    }

    @GetMapping
    public String loadHome(Model model) {
        final List<ProductEntity> productEntities = mySQLProductDao.findAll();

        List<ProductResponse> products = ProductResponse.from(productEntities);

        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String loadAdmin(Model model) {
        final List<ProductEntity> productEntities = mySQLProductDao.findAll();
        List<ProductResponse> products = ProductResponse.from(productEntities);

        model.addAttribute("products", products);
        return "admin";
    }
}
