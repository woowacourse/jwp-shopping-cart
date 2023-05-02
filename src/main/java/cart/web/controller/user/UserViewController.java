package cart.web.controller.user;

import cart.domain.product.ProductService;
import cart.domain.user.UserService;
import cart.web.controller.product.dto.ProductResponse;
import cart.web.controller.user.dto.UserResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

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
        final List<ProductResponse> productResponses = productService.getProducts()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getProductNameValue(),
                        product.getImageUrlValue(), product.getPriceValue(), product.getCategory()))
                .collect(Collectors.toList());
        model.addAttribute("products", productResponses);
        return "index.html";
    }

    @GetMapping("/settings")
    public String renderSettings(final Model model) {
        final List<UserResponse> userResponses = userService.getUsers()
                .stream()
                .map(user -> new UserResponse(user.getUserEmailValue(), user.getUserPasswordValue()))
                .collect(Collectors.toList());
        model.addAttribute("members", userResponses);
        return "settings.html";
    }
}
