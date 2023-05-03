package cart.repository.cart.mapper;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.entiy.cart.CartEntity;
import cart.entiy.cart.CartEntityId;
import cart.entiy.cart.CartProductEntity;
import cart.entiy.cart.CartProductEntityId;
import cart.entiy.product.ProductEntityId;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CartMapper {

    private CartMapper() {
    }

    public static List<CartProductEntity> toCartProductEntities(final List<CartProduct> cartProducts,
            final CartEntityId cartEntityId) {
        return cartProducts.stream()
                .map(cartProduct -> new CartProductEntity(
                        CartProductEntityId.from(cartProduct.getCartProductId()),
                        cartEntityId,
                        ProductEntityId.from(cartProduct.getProduct().getProductId())))
                .collect(Collectors.toList());
    }

    public static Cart toCart(
            final CartEntity cartEntity,
            final List<CartProductEntity> cartProductEntities,
            final User user,
            final List<Product> products) {
        final List<CartProduct> cartProducts = cartProductEntities.stream()
                .map(findCartProduct(products))
                .collect(Collectors.toList());
        return new Cart(cartEntity.getCartId().toDomain(), user, cartProducts);
    }

    private static Function<CartProductEntity, CartProduct> findCartProduct(final List<Product> products) {
        return cartProductEntity -> {
            final Product product = products.stream()
                    .filter(p -> p.getProductId().equals(cartProductEntity.getProductEntityId().toDomain()))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            return new CartProduct(cartProductEntity.getCartProductEntityId().toDomain(), product);
        };
    }
}
