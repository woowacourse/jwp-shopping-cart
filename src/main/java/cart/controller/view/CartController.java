package cart.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart")
<<<<<<< HEAD
<<<<<<< HEAD
    public String getCartPage() {
=======
    String cart() {
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
=======
    public String getCartPage() {
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
        return "cart";
    }

}
