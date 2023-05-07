package cart.controller;

import cart.auth.AuthInfo;
import cart.auth.BasicAuth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @GetMapping("/carts")
    public void cartList(@BasicAuth AuthInfo authInfo) {
        
    }
}
