package cart.controller;

import cart.controller.dto.ProductDto;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final ProductDao productDao;

    public AdminController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/admin")
    public String findAllProducts(final Model model) {
        List<Product> products = productDao.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
        model.addAttribute("products", productDtos);

        return "admin";
    }
}
