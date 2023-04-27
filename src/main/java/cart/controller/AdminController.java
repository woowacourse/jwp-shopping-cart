package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    
    @GetMapping
    public ModelAndView admin(ModelAndView modelAndView) {
        modelAndView.setViewName("admin");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.save(productRequest);
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        productService.update(id, productRequest);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }
}
