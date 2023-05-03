package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dto.AuthInfo;
import cart.dto.ProductResponse;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {

    private final ProductService productService;
    private final MemberDao memberDao;
    private final CartDao cartDao;

    public void addToCart(final AuthInfo authInfo, final long productId) {
        final MemberEntity member = findBy(authInfo);

        cartDao.insert(member.getId(), productId);
    }

    public List<ProductResponse> showProductsBy(final AuthInfo authInfo) {
        final MemberEntity member = findBy(authInfo);

        final List<CartEntity> cartOfUser = cartDao.findByMemberId(member.getId());
        final List<Long> productIds = cartOfUser.stream()
                .map(CartEntity::getProductId)
                .collect(Collectors.toList());

        final List<ProductEntity> savedProducts = productService.findByProductIds(productIds);
        return savedProducts.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteProductBy(final AuthInfo authInfo, final long productId) {
        final MemberEntity member = findBy(authInfo);

        cartDao.delete(member.getId(), productId);
    }

    private MemberEntity findBy(final AuthInfo authInfo) {
        final Optional<MemberEntity> result = memberDao.findByAuthInfo(authInfo);

        if (result.isPresent()) {
            return result.get();
        }
        throw new IllegalArgumentException("존재하지 않는 회원입니다.");
    }
}
