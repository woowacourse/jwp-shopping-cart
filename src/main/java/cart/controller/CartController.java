package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.dao.ProductDao;
import cart.domain.Product;
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
        List<Product> products = productDao.findAll();
        ProductResponse productResponse = new ProductResponse(products);
        model.addAttribute("products", productResponse.getProducts());

        return "index";
    }
}
