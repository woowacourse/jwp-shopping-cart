package cart.controller.view;

<<<<<<< HEAD
<<<<<<< HEAD
import cart.controller.dto.response.ProductResponse;
=======
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)
=======
import cart.controller.dto.response.ProductResponse;
>>>>>>> 2c4cb820 (refactor: 예외 수정 및 변수 추출)
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< HEAD
<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)
=======
import java.util.List;

>>>>>>> 2c4cb820 (refactor: 예외 수정 및 변수 추출)
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
    public String getAdminPage(Model model) {
        List<ProductResponse> responses = productService.findAll();
        model.addAttribute("products", responses);
=======
    public String admin(Model model) {
<<<<<<< HEAD
        model.addAttribute("products", productService.findAll());
>>>>>>> 0606d2cf (refactor: View Controller와 ApiController 분리)
=======
        List<ProductResponse> responses = productService.findAll();
        model.addAttribute("products", responses);
>>>>>>> 2c4cb820 (refactor: 예외 수정 및 변수 추출)
        return "admin";
    }

}
