package cart.controller;

import cart.controller.dto.ProductDto;
import cart.dao.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CartController {

    private final ProductDao productDao;

    public CartController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/")
    public String findAllProducts(final Model model) {
        List<ProductDto> productDtos = productDao.findAll();
        model.addAttribute("products", productDtos);

        return "index";
    }
}
