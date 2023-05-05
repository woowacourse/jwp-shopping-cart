package cart.controller;

import cart.auth.Login;
import cart.auth.MemeberDto;
import cart.dao.MemeberDao;
import cart.dao.ProductDao;
import cart.domain.Cart;
import cart.domain.Member;
import cart.domain.Product;
import cart.dto.CartDto;
import cart.service.CartService;
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
    private final MemeberDao memeberDao;
    private final ProductDao productDao;


    public CartController(CartService cartService, MemeberDao memeberDao, ProductDao productDao) {
        this.cartService = cartService;
        this.memeberDao = memeberDao;
        this.productDao = productDao;
    }

    @GetMapping("/carts/products")
    @ResponseBody
    public List<Product> get(@Login MemeberDto memeberDto) {
        Member member = memeberDao.findByEmail(memeberDto.getEmail());
        List<Cart> carts = cartService.findAll(member.getId());
        List<Product> products = new ArrayList<>();

        for (Cart cart : carts) {
            products.add(productDao.findById(cart.getProductId()));
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
