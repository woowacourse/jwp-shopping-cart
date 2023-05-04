package cart.controller;

import cart.auth.MemberInfo;
import cart.auth.Principal;
import cart.request.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartController {

    @PostMapping
    public ResponseEntity<Void> addProduct(
            @Principal MemberInfo memberInfo,
            @Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.created(URI.create("/carts")).build();
    }
}
