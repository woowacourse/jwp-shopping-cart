package cart.dao;

import cart.dto.CartRequestDto;
import cart.dto.entity.MemberCartEntity;
import cart.dto.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartMemberRepository {
    private final CartDao cartDao;
    private final MemberDao memberDao;

    public CartMemberRepository(CartDao cartDao, MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    public List<MemberCartEntity> findCartByMember(String email) {
        MemberEntity member = memberDao.findByEmail(email);
        return cartDao.findCartByMember(member.getId());
    }

    public void createCartByMember(CartRequestDto cartRequestDto) {
        MemberEntity member = memberDao.findByEmail(cartRequestDto.getEmail());
        cartDao.save(cartRequestDto.getProductId(), member.getId());
    }
}
