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
        long memberId = findMemberId(memberEmail);
        List<CartItem> cartItemEntities = cartItemDao.selectAllByMemberId(memberId);
        List<CartItemDetailsDto> cartItemDetailsDtos = new ArrayList<>();
        for (CartItem cartItem : cartItemEntities) {
            Product product = productDao.selectById(cartItem.getProductId());
            cartItemDetailsDtos.add(CartItemDetailsDto.from(cartItem.getId(), product));
        }
        return cartItemDetailsDtos;
    }

    private long findMemberId(final String memberEmail) {
        Member member = memberDao.selectByEmail(memberEmail);
        if(member == null){
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        return member.getId();
    }

    @Transactional
    public long save(final String memberEmail, final long productId) {
        long memberId = findMemberId(memberEmail);
        return cartItemDao.insert(CartItem.of(memberId, productId));
    }

    @Transactional
    public void delete(final String memberEmail, final long cartItemId) {
        long memberId = findMemberId(memberEmail);
        int deletedRowCount = cartItemDao.deleteByIdAndMemberId(cartItemId, memberId);
        if (deletedRowCount == 0) {
            throw new IllegalArgumentException("장바구니에 없는 상품입니다.");
        }
    }

}
