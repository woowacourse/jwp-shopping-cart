package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AdminController {

    private final ProductManagementService productManagementService;

    public AdminController(final ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @GetMapping(value = {"/admin", "/admin/products"})
    public void readProducts(final Model model) {
        final List<ProductDto> allProduct = productManagementService.findAllProduct();
        model.addAttribute("products", allProduct);
    }

    @PostMapping("/admin/products")
    public void createProduct(@RequestBody final ProductDto productDto) {
        productManagementService.addProduct(productDto);
    }

    @PatchMapping("/admin/products/{product_id}")
    public void updateProduct(@PathVariable("product_id") final Long id, @RequestBody final ProductDto productDto) {
        System.out.println(productDto);
        final ProductDto updatedProductDto
                = new ProductDto(id, productDto.getName(), productDto.getImageUrl(), productDto.getPrice());
        productManagementService.updateProduct(updatedProductDto);
    }

    @DeleteMapping("/admin/products/{product_id}")
    public void deleteProduct(@PathVariable("product_id") final Long id) {
        productManagementService.deleteProduct(id);
    }
}
