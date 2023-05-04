package cart.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import cart.dto.AuthInfo;
import cart.repository.entity.ProductEntity;
import cart.service.CartService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String readCart() {
        return "cart";
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<ProductEntity>> readProductsInCart(final HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(AUTHORIZATION);

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        final List<ProductEntity> cartItems = cartService.findCartItemsByAuthInfo(new AuthInfo(email, password));

        return ResponseEntity.ok().body(cartItems);
    }

    @PostMapping("/cart")
    public ResponseEntity<Void> addProductInCart(@RequestParam final Long productId, final HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(AUTHORIZATION);

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        cartService.addProductByAuthInfo(productId, new AuthInfo(email, password));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cart")
    public ResponseEntity<Void> deleteProductInCart(@RequestParam final Long productId, final HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(AUTHORIZATION);

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        cartService.deleteProductByAuthInfo(productId, new AuthInfo(email, password));
        return ResponseEntity.ok().build();
    }
}
