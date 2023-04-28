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

    private final ProductDao productDao;

    public PageController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public String loadHome(Model model) {
        final List<ProductEntity> productEntities = productDao.findAll();
        List<ProductResponse> products = ResponseMapper.from(productEntities);

        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String loadAdmin(Model model){
        final List<ProductEntity> productEntities = productDao.findAll();
        List<ProductResponse> products = ResponseMapper.from(productEntities);

        model.addAttribute("products", products);
        return "admin";
    }
}
