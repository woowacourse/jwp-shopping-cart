package cart.controller;

import cart.domain.Product;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ViewController {

    private final ProductService productService;

    public ViewController(ProductService productService) {
        this.productService = productService;
    }

    // todo : 뷰에 productDto로 감싸서 넘겨줘야 하는지 생각해보기
    @GetMapping("/")
    public String productPageView(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String adminPageView(Model model) {
        //todo: 뷰 컨트롤러와 코드가 중복되는데, 어떻게 고칠지 고민꼭 해보기!!!!!!!!!
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "admin";
    }


}
