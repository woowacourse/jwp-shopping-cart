package cart.controller.view;

import cart.controller.dto.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private final ProductService productService;

    public IndexController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
<<<<<<< HEAD
<<<<<<< HEAD
    public String getIndexPage(final Model model) {
=======
    public String index(final Model model) {
>>>>>>> 2c4cb820 (refactor: 예외 수정 및 변수 추출)
=======
    public String getIndexPage(final Model model) {
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
        List<ProductResponse> responses = productService.findAll();
        model.addAttribute("products", responses);
        return "index";
    }

}
