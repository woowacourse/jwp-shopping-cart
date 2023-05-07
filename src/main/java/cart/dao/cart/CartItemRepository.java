package cart.dao.cart;

import cart.dao.product.ProductEntity;
import cart.domain.Id;
import cart.domain.cart.CartItem;
import cart.domain.product.ImageUrl;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.domain.product.ProductName;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemRepository {

    private final CartItemDao cartItemDao;

    public CartItemRepository(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public void addProduct(Long memberId, CartItem cartItem) {
        cartItemDao.insert(new CartItemEntity(memberId, cartItem.getProductId())); // amount담긴 CartProductEntity반환토록 할지 고민하기
    }

    public List<Product> getAllProducts(Long memberId) {
        List<ProductEntity> productEntities = cartItemDao.findAll(memberId);

        return productEntities.stream()
                .map(entity -> new Product(new Id(entity.getId()),
                        new ProductName(entity.getName()),
                        new ImageUrl(entity.getImageUrl()),
                        new Price(entity.getPrice())))
                .collect(Collectors.toList());
    }

    public void removeProduct(Long memberId, CartItem cartItem) {
        cartItemDao.delete(new CartItemEntity(memberId, cartItem.getProductId()));
    }

}
