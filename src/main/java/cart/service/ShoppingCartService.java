package cart.service;

import cart.entity.Product;
import cart.repository.MemberRepository;
import cart.repository.ShoppingCartRepository;
import cart.service.dto.MemberInfo;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private final MemberRepository memberRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(final MemberRepository memberRepository,
                               final ShoppingCartRepository shoppingCartRepository) {
        this.memberRepository = memberRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public List<Product> findAllProduct(final MemberInfo memberInfo) {
        long memberId = memberRepository.findId(memberInfo)
                .orElseThrow();
        return shoppingCartRepository.findAllProduct(memberId);
    }
}
