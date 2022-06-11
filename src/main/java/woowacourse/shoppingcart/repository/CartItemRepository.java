package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.Entity.CartEntity;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.custum.ResourceNotFoundException;
import woowacourse.shoppingcart.repository.dao.CartItemDao;
import woowacourse.shoppingcart.repository.dao.CustomerDao;
import woowacourse.shoppingcart.repository.dao.ProductDao;

@Repository
public class CartItemRepository {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final CustomerDao customerDao;

    public CartItemRepository(final CartItemDao cartItemDao,
                              ProductDao productDao, CustomerDao customerDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
        this.customerDao = customerDao;
    }

    public Long create(final Long customerId, final Long productId) {
        return cartItemDao.findIdByProductId(productId)
                .map(this::plusQuantity)
                .orElseGet(() -> cartItemDao.create(customerId, productId));
    }

    private Long plusQuantity(Long id) {
        CartEntity cartItemDaoById = cartItemDao.findById(id);
        cartItemDao.update(cartItemDaoById.plusQuantity());
        return id;
    }

    public void validateCustomerId(final Long customerId) {
        customerDao.findById(customerId).orElseThrow(ResourceNotFoundException::new);
    }

    public void validateProductId(Long productId) {
        productDao.findById(productId);
    }

    public Cart findById(final Long id) {
        return toCart(cartItemDao.findById(id));
    }

    private Cart toCart(CartEntity cartEntity) {
        return new Cart(cartEntity.getId(), cartEntity.getQuantity(), productDao.findById(
                cartEntity.getProductId()));
    }

    private List<Cart> toCarts(List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .map(this::toCart)
                .collect(Collectors.toList());
    }

    public List<Cart> findCartsByCustomerId(final Long customerId) {
        return toCarts(cartItemDao.findCartsByCustomerId(customerId));
    }

    public Cart update(CartEntity cartEntity) {
        int updatedRowNum = cartItemDao.update(cartEntity);
        if (updatedRowNum == 0) {
            throw new InvalidCartItemException();
        }
        return toCart(cartEntity);
    }

    public void deleteById(final Long id) {
        int deletedRowCount = cartItemDao.deleteById(id);
        if (deletedRowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteByIds(List<Long> cartIds) {
        int[] deletedRowNums = cartItemDao.deleteByIds(cartIds);
        if (deletedRowNums.length == 0) {
            throw new InvalidCartItemException();
        }
    }
}
