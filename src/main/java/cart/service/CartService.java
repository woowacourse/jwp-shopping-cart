package cart.service;

import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.entity.ProductEntity;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<ProductResponse> findAllByMemberId(Long memberId) {
        List<ProductEntity> productEntities = cartDao.findAllByMemberId(memberId);
        return ProductResponse.from(productEntities);
    }

    @Transactional
    public long add(Long memberId, Long productId) {
        validateDuplicate(memberId, productId);
        return cartDao.add(memberId, productId);
    }

    private void validateDuplicate(Long memberId, Long productId) {
        Optional<Long> cartId = cartDao.findCartIdByMemberIdAndProductId(
            memberId,
            productId);
        if (cartId.isPresent()) {
            throw new IllegalStateException("장바구니에 이미 상품이 존재합니다.");
        }
    }

    @Transactional
    public void delete(Long memberId, Long productId) {
        int deleteCount = cartDao.deleteByMemberIdAndProductId(memberId, productId);
        validateChange(deleteCount);
    }

    private void validateChange(int changeColumnCount) {
        if (changeColumnCount == 0) {
            throw new NoSuchElementException();
        }
    }
}
