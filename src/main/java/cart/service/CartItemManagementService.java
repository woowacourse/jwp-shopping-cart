package cart.service;

import cart.dao.CartItemDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.entity.CartItem;
import cart.domain.entity.Member;
import cart.domain.entity.Product;
import cart.dto.CartItemDetailsDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemManagementService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;


    public CartItemManagementService(final CartItemDao cartItemDao, final ProductDao productDao, final MemberDao memberDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<CartItemDetailsDto> findAll(final String memberEmail) {
        long memberId = findMember(memberEmail).getId();
        List<CartItem> cartItems = cartItemDao.selectAllByMemberId(memberId);
        List<CartItemDetailsDto> cartItemDetailsDtos = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            cartItemDetailsDtos.add(CartItemDetailsDto.from(cartItem.getId(), cartItem.getProduct()));
        }
        return cartItemDetailsDtos;
    }

    private Member findMember(final String memberEmail) {
        return memberDao.selectByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public long save(final String memberEmail, final long productId) {
        Member member = findMember(memberEmail);
        Product product = productDao.selectById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        return cartItemDao.insert(CartItem.of(member, product));
    }

    @Transactional
    public void delete(final String memberEmail, final long cartItemId) {
        long memberId = findMember(memberEmail).getId();
        cartItemDao.selectById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 없는 상품입니다."));
        int deletedRowCount = cartItemDao.deleteByIdAndMemberId(cartItemId, memberId);
        if (deletedRowCount == 0) {
            throw new IllegalArgumentException("해당 장바구니 상품의 삭제 권한이 없습니다.");
        }
    }

}
