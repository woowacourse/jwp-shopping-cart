package cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.controller.dto.AuthInfo;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.Product;
import cart.infrastructure.BasicAuthorizationExtractor;

@Controller
public class CartController {

    private final ProductDao productDao;
    private final CartDao cartDao;
    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    public CartController(final ProductDao productDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    @GetMapping("/")
    public String findAllProducts(final Model model) {
        List<Product> products = productDao.findAll();
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("products", productResponses);

        return "index";
    }

    @GetMapping("/cart")
    public String renderCart() {
        return "cart";
    }

    @GetMapping("/cart/products")
    public ResponseEntity<List<CartResponse>> findAllProductsByMember(final HttpServletRequest request, final Model model) {
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        List<Product> cartItems = cartDao.findAllByMemberEmail(email);
        System.out.println(cartItems.get(0).getName());
        List<CartResponse> cartResponses = cartItems.stream()
                .map(cartItem -> new CartResponse(cartItem.getName(), cartItem.getImageUrl(), cartItem.getPrice()))
                .collect(Collectors.toList());

        model.addAttribute("cartItems", cartResponses);

        return ResponseEntity.ok().body(cartResponses);
    }
}
