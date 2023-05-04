package cart.service;

import cart.domain.cart.Cart;
import cart.domain.cart.CartItems;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.product.ProductsResponseDto;
import cart.exception.ProductNotFoundException;
import cart.repository.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartService(final CartRepository cartRepository, final ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Transactional
    public ProductsResponseDto findAllCartItems(final Member member) {
        checkMemberHasCartOrCreateEmptyCart(member);
        Cart cart = cartRepository.findCartByMember(member);
        return getProductsResponseDto(cart.getCartItems());
    }

    @Transactional
    public Long addCartItem(final Member member, final Long productId) {
        checkMemberHasCartOrCreateEmptyCart(member);

        Cart cart = cartRepository.findCartByMember(member);
        Product product = productService.findById(productId);

        cart.addCartItem(product);
        return cartRepository.saveCartItem(cart);
    }

    @Transactional
    public void deleteCartItem(final Member member, final Long productId) {
        Cart cart = cartRepository.findCartByMember(member);
        Product product = productService.findById(productId);

        CheckCartHasRequestProduct(product, cart);
        Product deletedProduct = cart.removeCartItem(product);

        cartRepository.deleteCartItem(cart, deletedProduct);
    }

    private ProductsResponseDto getProductsResponseDto(final CartItems cartItems) {
        return ProductsResponseDto.from(cartItems.getCartItems());
    }

    private void CheckCartHasRequestProduct(final Product product, final Cart cart) {
        if (!cart.containsCartItem(product)) {
            throw new ProductNotFoundException();
        }
    }

    private void checkMemberHasCartOrCreateEmptyCart(final Member member) {
        if (!cartRepository.existCart(member)) {
            cartRepository.createCart(Cart.createEmptyCart(member));
        }
    }
}
