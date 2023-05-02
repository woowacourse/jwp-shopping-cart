package cart.dao;

import cart.dto.MemberRequestDto;
import cart.dto.entity.CartEntity;
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

    public List<MemberCartEntity> findCartByMember(MemberRequestDto memberRequestDto) {
        MemberEntity member = memberDao.findByEmail(new MemberEntity(memberRequestDto.getEmail(), memberRequestDto.getPassword()));
        return cartDao.findCartByMember(member.getId());
    }

    public void createCartByMember(MemberRequestDto memberRequestDto, Long id) {
        MemberEntity member = memberDao.findByEmail(new MemberEntity(memberRequestDto.getEmail(), memberRequestDto.getPassword()));
        cartDao.save(new CartEntity(id, member.getId()));
    }

    public void delete(MemberRequestDto memberRequestDto, Long id) {
        MemberEntity member = memberDao.findByEmail(new MemberEntity(memberRequestDto.getEmail(), memberRequestDto.getPassword()));
        cartDao.delete(new CartEntity(id, member.getId()));
    }
}
