package cart.service;

import cart.dto.CartItemDto;
import cart.entity.MemberEntity;
import cart.exception.CartNotFoundException;
import cart.exception.ProductNotFoundException;
import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
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
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public long add(final String email, final long productId) {
        MemberEntity member = memberDao.findByEmail(email);
        validateProductId(productId);

        return cartDao.add(member.getId(), productId);
    }

    public void delete(final String email, final long cartId) {
        MemberEntity member = memberDao.findByEmail(email);
        validateCartId(cartId);

        cartDao.deleteById(member.getId(), cartId);
    }

    @Transactional(readOnly = true)
    public List<CartItemDto> findAllByMemberId(final String email) {
        MemberEntity member = memberDao.findByEmail(email);
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
