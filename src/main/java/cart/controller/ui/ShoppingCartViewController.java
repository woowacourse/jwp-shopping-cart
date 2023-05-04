package cart.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class ShoppingCartViewController {

    //TODO : view에 있는 메서드의 네이밍 수정하기
    @GetMapping
    public String serveCartPage() {
        return "cart";
    }
}
