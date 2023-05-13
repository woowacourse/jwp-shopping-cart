package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dto.CartItemDto;
import cart.dto.MemberDto;
import cart.dto.request.CartAddRequest;
import cart.exception.CartNotFoundException;
import cart.exception.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public long add(final CartAddRequest cartAddRequest, final MemberDto member) {
        Long productId = cartAddRequest.getProductId();
        validateProductId(productId);

        return cartDao.add(member.getId(), productId);
    }

    public void delete(final long cartId, final MemberDto member) {
        validateCartId(cartId);

        cartDao.deleteById(member.getId(), cartId);
    }

    @Transactional(readOnly = true)
    public List<CartItemDto> findAllByMemberId(final MemberDto member) {
        return cartDao.findAllByMemberId(member.getId()).stream()
                .map(CartItemDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private void validateProductId(final long productId) {
        if (!productDao.existsById(productId)) {
            throw new ProductNotFoundException("존재하지 않는 product id 입니다.");
        }
    }

    @Transactional(readOnly = true)
    private void validateCartId(final long cartId) {
        if (!cartDao.existsById(cartId)) {
            throw new CartNotFoundException("존재하지 않는 cart id 입니다.");
        }
    }
}
