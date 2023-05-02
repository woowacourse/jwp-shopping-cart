package cart.service;

import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.product.ProductsResponseDto;
import cart.exception.ProductNotFoundException;
import cart.repository.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    @Autowired
    public CartService(final CartRepository cartRepository, final ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public ProductsResponseDto findAll(final Member member) {
        List<Cart> carts = cartRepository.findAllByMember(member);
        return getProductsResponseDto(carts);
    }

    private ProductsResponseDto getProductsResponseDto(final List<Cart> carts) {
        List<Product> products = carts.stream()
                .map(cart -> productService.findById(cart.getProductId()))
                .collect(Collectors.toList());

        return ProductsResponseDto.from(products);
    }

    @Transactional
    public Long addCart(final Member member, final Long productId) {
        Product product = productService.findById(productId);
        Cart cart = Cart.from(member, product);

        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(final Member member, final Long productId) {
        Product product = productService.findById(productId);
        List<Cart> memberCarts = cartRepository.findAllByMember(member);

        validateCartHasRequestProduct(product, memberCarts);

        cartRepository.delete(member, product);
    }

    private void validateCartHasRequestProduct(final Product product, final List<Cart> memberCarts) {
        List<Product> memberCartProducts = memberCarts.stream()
                .map(cart -> productService.findById(cart.getProductId()))
                .collect(Collectors.toList());

        if (!memberCartProducts.contains(product)) {
            throw new ProductNotFoundException();
        }
    }
}
