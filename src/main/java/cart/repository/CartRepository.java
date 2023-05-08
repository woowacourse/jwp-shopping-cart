package cart.repository;

import cart.dao.CartDao;
import cart.dao.CartWithProduct;
import cart.domain.Cart;
import cart.domain.Product;
import cart.entity.CartEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CartRepository {

    private final CartDao cartDao;

    public CartRepository(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Cart getCartOf(final Integer memberId) {
        return new Cart(memberId, getProductsOf(memberId));
    }

    private List<Product> getProductsOf(final int memberId) {
        final List<CartWithProduct> cartWithProducts = cartDao.cartWithProducts(memberId);
        return extractProducts(cartWithProducts);
    }

    private List<Product> extractProducts(final List<CartWithProduct> cartWithProducts) {
        final List<Product> products = new ArrayList<>();
        for (CartWithProduct cartWithProduct : cartWithProducts) {
            products.add(new Product(
                            cartWithProduct.getProductId(),
                            cartWithProduct.getProductName(),
                            cartWithProduct.getProductImage(),
                            cartWithProduct.getProductPrice()
                    )
            );
        }
        return products;
    }

    public void save(final Cart cart) {
        final List<Product> previousCartItems = getProductsOf(cart.getMemberId());
        cartDao.deleteProducts(makeEntitiesOf(
                        cart.getMemberId(),
                        getDeletedCartItems(cart, previousCartItems)
                )
        );
        cartDao.insertProducts(makeEntitiesOf(
                        cart.getMemberId(),
                        getInsertedCartItems(cart, previousCartItems)
                )
        );
    }

    private List<CartEntity> makeEntitiesOf(final int memberId, final List<Integer> productIds) {
        return productIds.stream()
                .map(productId -> new CartEntity(memberId, productId))
                .collect(Collectors.toList());
    }

    private List<Integer> getDeletedCartItems(final Cart cart, final List<Product> previousCartItems) {
        final List<Product> cartProducts = cart.getProducts();
        return previousCartItems.stream()
                .filter(product -> !cartProducts.contains(product))
                .map((Product::getId))
                .collect(Collectors.toList());
    }

    private List<Integer> getInsertedCartItems(final Cart cart, final List<Product> previousCartItems) {
        final List<Product> cartProducts = cart.getProducts();
        return cartProducts.stream()
                .filter(product -> !previousCartItems.contains(product))
                .map((Product::getId))
                .collect(Collectors.toList());
    }
}
