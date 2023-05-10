package cart.service;

import cart.auth.AuthAccount;
import cart.dao.AccountEntity;
import cart.dao.CartEntity;
import cart.dao.CartInProductDao;
import cart.dao.CartInProductEntity;
import cart.service.dto.CartInProductDeleteRequest;
import cart.service.dto.CartInProductRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartCommandService {

    private final CartInProductDao cartInProductDao;
    private final AccountQueryService accountQueryService;

    public CartCommandService(
            final CartInProductDao cartInProductDao,
            final AccountQueryService accountQueryService
    ) {
        this.cartInProductDao = cartInProductDao;
        this.accountQueryService = accountQueryService;
    }

    public void registerProductInCart(
            final AuthAccount authAccount,
            final CartInProductRegisterRequest cartInProductRegisterRequest
    ) {
        final AccountEntity accountEntity = accountQueryService.searchByEmailAndPassword(authAccount);

        final CartInProductEntity cartInProductEntity =
                new CartInProductEntity(accountEntity.getCartId(), cartInProductRegisterRequest.getProductId());

        cartInProductDao.save(cartInProductEntity);
    }

    public void deleteProductInCart(
            final AuthAccount authAccount,
            final CartInProductDeleteRequest cartInProductDeleteRequest
    ) {
        final AccountEntity accountEntity = accountQueryService.searchByEmailAndPassword(authAccount);

        final CartEntity cartEntity = new CartEntity(accountEntity.getCartId());

        cartInProductDao.deleteProductByCartAndProductId(cartEntity, cartInProductDeleteRequest.getProductId());
    }
}
