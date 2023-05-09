package cart.service;

import cart.dao.CartRepository;
import cart.dao.MemberDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Member;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private static final String MEMBER_NOT_FOUND_MESSAGE = "존재하지 않는 회원 정보입니다.";


    private final CartRepository cartRepository;
    private final MemberDao mySQLMemberDao;

    public CartService(CartRepository cartRepository, MemberDao mySQLMemberDao) {
        this.cartRepository = cartRepository;
        this.mySQLMemberDao = mySQLMemberDao;
    }

    public List<ProductEntity> loadCartProducts(Member member){
        Long memberId = mySQLMemberDao.findIdByMember(member)
            .orElseThrow(() -> new NoSuchElementException(MEMBER_NOT_FOUND_MESSAGE));
        return cartRepository.loadProductsByMember(memberId);
    }

    public long addCart(Long productId, Member member) {
        Long memberId = mySQLMemberDao.findIdByMember(member)
            .orElseThrow(() -> new NoSuchElementException(MEMBER_NOT_FOUND_MESSAGE));
        return cartRepository.add(productId, memberId);
    }

    public int removeCart(Long productId, Member member) {
        Long memberId = mySQLMemberDao.findIdByMember(member).orElseThrow(() -> new
            NoSuchElementException(MEMBER_NOT_FOUND_MESSAGE));
        return cartRepository.remove(productId, memberId);
    }


}
