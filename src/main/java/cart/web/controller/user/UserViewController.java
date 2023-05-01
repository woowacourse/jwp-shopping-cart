package cart.web.controller.user;

import cart.domain.product.ProductService;
import cart.domain.user.UserService;
import cart.web.controller.product.dto.ProductResponse;
import cart.web.controller.user.dto.UserResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserViewController {

    private final UserService userService;
    private final ProductService productService;

    public UserViewController(final UserService userService, final ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }


    @GetMapping("/")
    public String renderIndex(final Model model) {
        final List<ProductResponse> products = productService.getProducts();
        model.addAttribute("products", products);
        return "index.html";
    }

    @GetMapping("/settings")
    public String renderSettings(final Model model) {
        final List<UserResponse> userResponses = userService.getUsers();
        model.addAttribute("members", userResponses);
        return "settings.html";
    }
}
