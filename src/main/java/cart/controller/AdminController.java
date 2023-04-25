package cart.controller;

import cart.dto.ProductRequestDto;
import cart.service.ProductService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String admin() {
        return "admin";
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@RequestBody final ProductRequestDto productRequestDto) {
        System.out.println(productRequestDto.getName());
        Long id = productService.saveProduct(productRequestDto);
        return ResponseEntity.created(URI.create("admin/products/"+id)).build();
    }


}
