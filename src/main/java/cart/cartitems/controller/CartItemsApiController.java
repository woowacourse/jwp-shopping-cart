package cart.cartitems.controller;

import cart.cartitems.service.CartItemsService;
import cart.infrastructure.AuthInfo;
import cart.product.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart/items")
public class CartItemsApiController {

    private final CartItemsService cartItemsService;

    @Autowired
    public CartItemsApiController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getCartItems(HttpServletRequest request) {
        final AuthInfo authInfo = (AuthInfo) request.getAttribute("authInfo");
        List<ProductDto> items = cartItemsService.findItemsOfCart(authInfo);
        return ResponseEntity.ok(items);
    }
}
