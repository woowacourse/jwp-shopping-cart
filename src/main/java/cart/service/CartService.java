package cart.service;

import cart.dao.ProductsDao;
import cart.dao.cartAddedProductDao;
import cart.entity.CartAddedProduct;
import cart.entity.Product;
import cart.entity.vo.Email;
import cart.exception.UserForbiddenException;
import cart.service.dto.ProductInCart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final cartAddedProductDao cartAddedProductDao;
    private final ProductsDao productsDao;

    public CartService(final cartAddedProductDao cartAddedProductDao, final ProductsDao productsDao) {
        this.cartAddedProductDao = cartAddedProductDao;
        this.productsDao = productsDao;
    }

    public long addProductToUser(final Email userEmail, final long productId) {
        final Product product = productsDao.findById(productId);
        return cartAddedProductDao.insert(userEmail, product);
    }


    public List<ProductInCart> findAllProductsInCartByUser(final Email userEmail) {
        final List<CartAddedProduct> cartAddedProducts = cartAddedProductDao.findProductsByUserEmail(userEmail);
        return cartAddedProducts.stream()
                .map(cartAddedProduct -> new ProductInCart(
                        cartAddedProduct.getId(),
                        cartAddedProduct.getProduct().getName(),
                        cartAddedProduct.getProduct().getPrice(),
                        cartAddedProduct.getProduct().getImageUrl()
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteCartItem(final Email userEmail, final Long id) {
        final CartAddedProduct cartAddedProduct = cartAddedProductDao.findById(id);

        if (!cartAddedProduct.getUserEmail().equals(userEmail)) {
            throw new UserForbiddenException("해당 장바구니의 사용자가 아닙니다.");
        }

        cartAddedProductDao.delete(cartAddedProduct.getId());
    }
}
