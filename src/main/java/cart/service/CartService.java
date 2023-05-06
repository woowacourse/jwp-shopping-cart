package cart.service;

import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final MemberDao memberDao;
    private final CartDao cartDao;

    public CartService(final MemberDao memberDao, final CartDao cartDao) {
        this.memberDao = memberDao;
        this.cartDao = cartDao;
    }

    public void save(final String email, final Long productId) {
        Long memberId = memberDao.findByEmail(email);

        cartDao.existsByMemberIdAndProductId(memberId, productId)
                .ifPresent((action) -> {
                    throw new IllegalArgumentException("장바구니에 이미 담겨있는 상품입니다.");
                });
        cartDao.save(memberId, productId);
    }

    public List<ProductResponse> findByEmail(final String email) {
        List<Product> productsByEmail = cartDao.findByEmail(email);
        return productsByEmail.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public void delete(final String email, final Long productId) {
        Long memberId = memberDao.findByEmail(email);
        cartDao.deleteByMemberIdAndProductId(memberId, productId);
    }
}
