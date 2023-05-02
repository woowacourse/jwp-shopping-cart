package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.entity.CartEntity;
import cart.entity.product.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
        final List<Optional<ProductEntity>> products = productIds.stream()
            .map(productDao::findById)
            .collect(Collectors.toList());

        return products.stream()
            .map(product -> product.orElseThrow(
                () -> new IllegalArgumentException("장바구니에 담긴 상품의 Id에 해당하는 상품이 존재하지 않습니다."))
            ).collect(Collectors.toList());
    }

    @Transactional
    public void deleteByCustomerIdAndProductId(final Long customerId, final Long productID) {
        cartDao.deleteByCustomerIdAndProductId(customerId, productID);
    }
}
