package cart.controller.view;

<<<<<<< HEAD
import cart.controller.dto.response.ProductResponse;
=======
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
<<<<<<< HEAD
    public String getAdminPage(Model model) {
        List<ProductResponse> responses = productService.findAll();
        model.addAttribute("products", responses);
=======
    public String admin(Model model) {
        model.addAttribute("products", productService.findAll());
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)
        return "admin";
    }

}