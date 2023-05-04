package cart.controller;

import cart.auth.MemberInfo;
import cart.auth.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartController {

    @PostMapping
    public ResponseEntity<Void> addProduct(@Principal MemberInfo memberInfo) {
        return ResponseEntity.created(URI.create("/carts")).build();
    }
}
