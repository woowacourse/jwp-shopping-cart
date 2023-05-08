package cart.controller;

import cart.auth.Login;
import cart.domain.Cart;
import cart.domain.Member;
import cart.dto.CartDto;
import cart.dto.response.ProductResponse;
import cart.service.CartService;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/carts/products")
    @ResponseBody
    public ResponseEntity<List<ProductResponse>> get(@Login Member member) {
        List<Cart> carts = cartService.findAll(member.getId());
        List<ProductResponse> productResponses = new ArrayList<>();

        for (Cart cart : carts) {
            productResponses.add(new ProductResponse(productService.findById(cart.getProductId())));
        }

        return ResponseEntity.status(HttpStatus.OK).body(productResponses);
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @PostMapping("/carts/{productId}")
    @ResponseBody
    public ResponseEntity<String> add(@PathVariable Long productId, @Login Member member) {
        cartService.add(new CartDto(member.getId(), productId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/carts/{productId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long productId, @Login Member member) {
        cartService.delete(new CartDto(member.getId(), productId));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
