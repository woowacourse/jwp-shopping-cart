package cart.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cart.product.dto.ExceptionResponse;
import cart.product.dto.ProductRequest;
import cart.product.dto.ProductResponse;
import cart.product.service.ProductService;

@Controller
@RequestMapping("/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = {"/", "/admin"})
    public String displayHome(Model model, HttpServletRequest request) {
        model.addAttribute("products", productService.findAll());
        if (request.getRequestURI().equals("/admin")) {
            return "admin";
        }
        return "index";
    }

    @PostMapping("/products")
    @ResponseBody
    public ResponseEntity<ProductResponse> createProducts(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.saveProducts(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @PutMapping("/products/{id}")
    @ResponseBody
    public ResponseEntity<ProductResponse> updateProducts(@PathVariable Long id,
                                                          @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProducts(id, productRequest);

        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @DeleteMapping("/products/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteProducts(@PathVariable Long id) {
        productService.deleteProductsById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBindException(Exception exception) {
        final String exceptionMessage = exception.getMessage();
        return ResponseEntity.badRequest().body(new ExceptionResponse(exceptionMessage));
    }
}
