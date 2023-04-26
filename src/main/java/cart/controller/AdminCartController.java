package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCartController {

    private final ProductDao productDao;

    public AdminCartController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping("/products")
    public String createProduct(@Valid @RequestBody final ProductRequest productRequest) {
        productDao.save(productRequest);

        return "admin";
    }

    @GetMapping
    public String findAllProducts(final Model model) {
        List<Product> products = productDao.findAll();
        model.addAttribute("products", products);

        return "admin";
    }

    @PutMapping("/products/{productId}")
    public String modifyProduct(@PathVariable final Long productId, @Valid @RequestBody final ProductRequest productRequest) {
        productDao.updateById(productId, productRequest);

        return "admin";
    }

    @DeleteMapping("/products/{productId}")
    public String removeProduct(@PathVariable final Long productId) {
        productDao.deleteById(productId);

        return "admin";
    }
}
