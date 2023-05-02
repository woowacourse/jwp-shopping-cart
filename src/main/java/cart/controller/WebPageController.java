package cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.controller.dto.ProductResponse;
import cart.dao.ProductEntity;
import cart.domain.Product;
import cart.repository.ProductRepository;

@Controller
public class WebPageController {

    private final ProductRepository productRepository;

    public WebPageController(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String renderStartPage(final Model model) {
        model.addAttribute("products", mapProducts(productRepository.getAll()));
        return "index";
    }

    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", mapProducts(productRepository.getAll()));
        return "admin";
    }

    private List<ProductResponse> mapProducts(List<Product> products) {
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getImage(),
                        product.getPrice())
                ).collect(Collectors.toList());
    }
}
