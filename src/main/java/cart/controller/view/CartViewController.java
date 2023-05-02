package cart.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class CartViewController {

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
