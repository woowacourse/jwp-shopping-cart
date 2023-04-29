package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductManagementService productManagementService;

    public AdminController(final ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @GetMapping(value = {"", "/products"})
    public String readProducts(final Model model) {
        final List<ProductDto> allProduct = productManagementService.findAllProduct();
        model.addAttribute("products", allProduct);
        return "admin";
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody @Valid final ProductDto productDto) {
        productManagementService.addProduct(productDto);
    }

    @PatchMapping("/products/{product_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProduct(@PathVariable("product_id") final Long id,
                              @RequestBody @Valid final ProductDto productDto) {
        final ProductDto updatedProductDto
                = new ProductDto(id, productDto.getName(), productDto.getImageUrl(), productDto.getPrice());
        productManagementService.updateProduct(updatedProductDto);
    }

    @DeleteMapping("/products/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable("product_id") final Long id) {
        productManagementService.deleteProduct(id);
    }
}
