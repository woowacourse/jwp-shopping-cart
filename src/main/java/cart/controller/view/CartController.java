package cart.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart")
<<<<<<< HEAD
    public String getCartPage() {
=======
    String cart() {
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
        return "cart";
    }

}
