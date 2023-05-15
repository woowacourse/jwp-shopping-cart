package cart.service;

import cart.dao.cart.CartDao;
import cart.dao.cart.CartEntity;
import cart.dao.cart.CartProductDto;
import cart.dao.member.MemberEntity;
import cart.global.exception.cart.ProductNotFoundInCartException;
import cart.global.infrastructure.Credential;
import cart.service.dto.cart.CartAddProductRequest;
import cart.service.dto.cart.CartAllProductSearchResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartService {

    private final AuthService authService;
    private final CartDao cartDao;

    public CartService(final AuthService authService, final CartDao cartDao) {
        this.authService = authService;
        this.cartDao = cartDao;
    }

    @Transactional(readOnly = true)
    public List<CartAllProductSearchResponse> searchAllCartProducts(final Credential credential) {
        final MemberEntity member = authService.getMemberEntity(credential);
        List<CartProductDto> cartProductDtos = cartDao.findProductsByMemberId(member.getId());

        return cartProductDtos.stream()
                .map(entity -> new CartAllProductSearchResponse(
                        entity.getCartId(),
                        entity.getName(),
                        entity.getPrice(),
                        entity.getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    public Long save(final Credential credential, final CartAddProductRequest request) {
        final MemberEntity member = authService.getMemberEntity(credential);
        CartEntity saveCart = new CartEntity(member.getId(), request.getProductId());

        return cartDao.save(saveCart);
    }

    public void deleteProduct(final Long cartProductId) {
        int affectedRow = cartDao.deleteById(cartProductId);

        if (affectedRow == 0) {
            throw new ProductNotFoundInCartException();
        }
    }
}
