package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.entity.CartEntity;
import cart.entity.product.ProductEntity;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @Transactional
    public Long add(final CartEntity cartEntity) {
        return cartDao.save(cartEntity);
    }

    @Transactional
    public void delete(final Long id) {
        cartDao.delete(id);
    }

    public List<ProductEntity> findAllById(final Long customerId) {
        final List<Long> productIds = cartDao.findAllProductIdsBy(customerId);
        if (productIds.size() == 0) {
            return Collections.emptyList();
        }
        final List<ProductEntity> products = productDao.findAllByIdIn(productIds);
        products.sort(Comparator.comparing(ProductEntity::getName).reversed());
        return products;
    }

    @Transactional
    public void deleteByCustomerIdAndProductId(final Long customerId, final Long productID) {
        cartDao.deleteByCustomerIdAndProductId(customerId, productID);
    }

    public Long findFirstIdBy(final Long customerId, final Long productId) {
        final List<Long> cartIds = cartDao.findIdsBy(customerId, productId);
        if (cartIds.size() == 0) {
            throw new IllegalArgumentException("해당 고객과 상품을 가진 장바구니가 존재하지 않습니다.");
        }
        return cartIds.get(0);
    }
}
