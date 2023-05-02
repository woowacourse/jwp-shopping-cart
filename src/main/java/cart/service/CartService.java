package cart.service;

import cart.dao.CartMemberRepository;
import cart.dto.entity.MemberCartEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartMemberRepository cartMemberRepository;

    public CartService(CartMemberRepository cartMemberRepository) {
        this.cartMemberRepository = cartMemberRepository;
    }

    public List<MemberCartEntity> findAll(String email) {
        return cartMemberRepository.findCartByMember(email);
    }

}
