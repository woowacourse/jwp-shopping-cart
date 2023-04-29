package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {

    private final ProductDao productDao;

    public CartController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/")
    public String findAllProducts(final Model model) {
        List<Product> products = productDao.findAll();
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("products", productResponses);

        return "index";
    }
}
