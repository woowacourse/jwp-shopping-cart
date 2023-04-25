package cart.controller;

import cart.dao.ProductDao;
import java.util.List;
import java.util.stream.Collectors;
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
        List<ProductResponse> products = productDao.findAll()
            .stream()
            .map(entity -> new ProductResponse(entity.getId(), entity.getName(), entity.getPrice(),
                entity.getImageUrl())).collect(
                Collectors.toList());

        model.addAttribute("products", products);
        return "index";
    }
}
