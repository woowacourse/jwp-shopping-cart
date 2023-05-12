package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dao.CartProductJdbcDao;
import cart.domain.cart.CartProduct;
import cart.domain.member.Member;
import cart.dto.CartProductDto;
import cart.dto.CartProductSaveRequest;
import cart.dto.ProductDto;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartProductService {

    private final CartProductJdbcDao cartProductDao;

    public CartProductService(final CartProductJdbcDao cartProductDao) {
        this.cartProductDao = cartProductDao;
    }

    public Long save(final Member member, final CartProductSaveRequest request) {
        final CartProduct cartProduct = new CartProduct(member.getId(), request.getProductId());
        return cartProductDao.saveAndGetId(cartProduct);
    }

    @Transactional(readOnly = true)
    public CartProductDto findAll(final Member member) {
        return cartProductDao.findAllProductByMember(member).stream()
                .map(ProductDto::from)
                .collect(Collectors.collectingAndThen(toList(), CartProductDto::new));
    }

    public void delete(final Long productId, final Member member) {
        final int affectedRowCount = cartProductDao.delete(productId, member);
        validateNoSuchProductId(affectedRowCount);
    }

    private void validateNoSuchProductId(final int affectedRowCount) {
        if (affectedRowCount == 0) {
            throw new IllegalArgumentException("해당 상품은 장바구니에 존재하지 않습니다.");
        }
    }
}
