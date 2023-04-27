package cart.controller;

import cart.controller.dto.SaveRequest;
import cart.dao.ProductDao;
import cart.domain.product.Product;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final ProductDao productDao;

    public AdminController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping("/product")
    public String saveProduct(@RequestBody final SaveRequest saveRequest, final HttpServletResponse response) {
        Product product = Product.createWithoutId(
                saveRequest.getName(),
                saveRequest.getPrice(),
                saveRequest.getImageUrl()
        );
        productDao.save(product);
        response.setStatus(HttpStatus.CREATED.value());
        return "admin";
    }

    @GetMapping
    public String productList(final Model model) {
        final List<Product> products = productDao.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @PutMapping("/product/{id}")
    public String updateProduct(@RequestBody final SaveRequest saveRequest, @PathVariable Long id,
                                final HttpServletResponse response) {
        final Product product = Product.create(id, saveRequest.getName(), saveRequest.getPrice(),
                saveRequest.getImageUrl());
        productDao.update(product);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "admin";
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productDao.deleteById(id);
        return "admin";
    }
}
