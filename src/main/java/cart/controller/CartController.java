package cart.controller;

import cart.auth.Login;
import cart.dao.member.MemeberDao;
import cart.domain.Cart;
import cart.domain.Member;
import cart.domain.Product;
import cart.dto.CartDto;
import cart.dto.MemeberDto;
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
    private final MemeberDao memeberDao;

    public CartController(CartService cartService, ProductService productService, MemeberDao memeberDao) {
        this.cartService = cartService;
        this.productService = productService;
        this.memeberDao = memeberDao;
    }

    @GetMapping("/carts/products")
    @ResponseBody
    public List<Product> get(@Login MemeberDto memeberDto) {
        Member member = memeberDao.findByEmail(memeberDto.getEmail());
        List<Cart> carts = cartService.findAll(member.getId());
        List<Product> products = new ArrayList<>();

        for (Cart cart : carts) {
            products.add(productService.findById(cart.getProductId()));
        }

        return products;
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @PostMapping("/carts/{productId}")
    @ResponseBody
    public ResponseEntity<String> add(@PathVariable Long productId, @Login MemeberDto memeberDto) {
        Member member = memeberDao.findByEmail(memeberDto.getEmail());
        cartService.add(new CartDto(member.getId(), productId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/carts/{productId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long productId, @Login MemeberDto memeberDto) {
        cartService.delete(productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
