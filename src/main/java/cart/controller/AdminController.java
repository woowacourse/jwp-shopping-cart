package cart.controller;

import cart.dao.ProductDao;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductDao productDao;

    public AdminController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public String loadHome(Model model) {
        List<ProductResponse> products = productDao.findAll()
            .stream()
            .map(entity -> new ProductResponse(entity.getId(), entity.getName(), entity.getPrice(),
                entity.getImageUrl())).collect(
                Collectors.toList());

        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping("/products")
    public ResponseEntity create(@RequestBody ProductRequest product) {
        productDao.add(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ProductRequest product) {
        productDao.updateById(id, product);
        return ResponseEntity.ok().build();
    }
}
