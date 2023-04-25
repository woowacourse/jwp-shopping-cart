package cart.controller;

import cart.service.ProductService;
import cart.service.dto.ProductModifyRequest;
import cart.service.dto.ProductRegisterRequest;
import cart.service.dto.ProductSearchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

//TODO : URL 에 따른 컨트롤러 분리할 것
@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerProduct(@RequestBody final ProductRegisterRequest productRegisterRequest) {
        productService.registerProduct(productRegisterRequest);
    }

    @GetMapping("/admin")
    public String searchProduct(final Model model) {
        List<ProductSearchResponse> productSearchResponses = productService.searchAllProducts();
        model.addAttribute("products", productSearchResponses);
        return "admin";
    }

    @PatchMapping("/products/{product-id}")
    public String modifyProduct(
            @PathVariable("product-id") final Long productId,
            @RequestBody final ProductModifyRequest productModifyRequest) {
        productService.modifyProduct(productId, productModifyRequest);

        return "admin";
    }

    @DeleteMapping("/products/{product-id}")
    public String deleteProduct(@PathVariable("product-id") final Long productId) {
        productService.deleteProduct(productId);

        return "admin";
    }

    @GetMapping("/")
    public String showWelcomePage(final Model model) {
        List<ProductSearchResponse> productSearchResponses = productService.searchAllProducts();
        model.addAttribute("products", productSearchResponses);
        return "index";
    }
}
