package cart.service;

import cart.domain.cart.CartRepository;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.service.dto.UserAuthDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartService {

    private final UserService userService;
    private final CartRepository cartRepository;

    public CartService(UserService userService, CartRepository cartRepository) {
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    public List<Product> findProductsInCartByUser(UserAuthDto userAuthDto) {
        System.out.println("check");
        User signedUpUser = this.userService.signUp(userAuthDto);
        return this.cartRepository.findByUserId(signedUpUser.getId()).getProducts();
    }
}
