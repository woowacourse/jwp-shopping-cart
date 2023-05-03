package cart.controller.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartViewController {


    @GetMapping("/cart")
    public String getCartPage() {
        return "cart";
    }
}
