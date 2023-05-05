package cart.service;

import cart.auth.Credential;
import cart.dao.cart.CartDao;
import cart.dao.member.MemberEntity;
import cart.dao.product.ProductEntity;
import cart.service.dto.CartAllProductSearchResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final AuthService authService;
    private final CartDao cartDao;

    public CartService(final AuthService authService, final CartDao cartDao) {
        this.authService = authService;
        this.cartDao = cartDao;
    }

    public List<CartAllProductSearchResponse> searchAllCartProducts(final Credential credential) {
        final MemberEntity memberEntity = authService.getMemberEntity(credential);
        final List<ProductEntity> productEntities = cartDao.findProductsByMemberId(memberEntity.getId());

        return productEntities.stream()
                .map(entity -> new CartAllProductSearchResponse(
                        entity.getId(),
                        entity.getName(),
                        entity.getPrice(),
                        entity.getImageUrl()
                ))
                .collect(Collectors.toList());
    }
}
