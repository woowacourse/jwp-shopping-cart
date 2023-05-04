package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dao.CartProductDao;
import cart.dao.MemberDao;
import cart.domain.CartProduct;
import cart.domain.Member;
import cart.dto.request.CartProductRequest;
import cart.dto.response.CartProductResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartProductService {

    private final CartProductDao cartProductDao;
    private final MemberDao memberDao;

    public CartProductService(CartProductDao cartProductDao, MemberDao memberDao) {
        this.cartProductDao = cartProductDao;
        this.memberDao = memberDao;
    }

    @Transactional
    public void save(String email, CartProductRequest productIdRequest) {
        Member member = memberDao.findByEmail(email);
        cartProductDao.save(member.getId(), productIdRequest.getProductId());
    }

    @Transactional(readOnly = true)
    public List<CartProductResponse> findAllByMemberEmail(String email) {
        Member member = memberDao.findByEmail(email);
        List<CartProduct> cartProducts = cartProductDao.findAllByMemberId(member.getId());
        return cartProducts.stream()
                .map(CartProductResponse::new)
                .collect(toList());
    }

    @Transactional
    public void deleteById(Long cartProductId) {
        cartProductDao.deleteById(cartProductId);
    }
}
