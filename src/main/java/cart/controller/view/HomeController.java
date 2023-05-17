package cart.controller.view;

import cart.controller.dto.ProductResponse;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ProductDao productDao;

    public HomeController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/")
    String index(final Model model) {
        final List<Product> products = productDao.findAll();
        final List<ProductResponse> all = products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getImageUrl(), product.getName(), product.getPrice()))
                .collect(Collectors.toList());
        model.addAttribute("products", all);
        return "index";
    }
}
