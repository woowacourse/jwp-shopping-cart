package cart.controller;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        final List<ProductEntity> productEntities = productDao.findAll();
        List<ProductResponse> products = ResponseMapper.from(productEntities);

        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping("/products")
    public String create(@RequestBody ProductRequest product) {
        productDao.add(product);
        return "redirect:/admin";
    }

    @PutMapping("/products/{id}")
    public String update(@PathVariable Long id, @RequestBody ProductRequest product) {
        productDao.updateById(id, product);
        return "admin";
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable Long id) {
        productDao.deleteById(id);
        return "admin";
    }
}
