package cart.web.controller.cart;

import cart.domain.user.User;
import cart.web.controller.auth.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartViewController {

    @GetMapping("/cart")
    public String renderCart(@Login User user) {
        return "cart.html";
    }
}
