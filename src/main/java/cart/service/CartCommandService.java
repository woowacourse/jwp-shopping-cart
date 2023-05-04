package cart.service;

import cart.auth.AuthAccount;
import cart.dao.AccountEntity;
import cart.dao.CartEntity;
import cart.dao.CartInProductDao;
import cart.dao.CartInProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartCommandService {

    private final CartInProductDao cartInProductDao;
    private final AccountService accountService;

    public CartCommandService(final CartInProductDao cartInProductDao, final AccountService accountService) {
        this.cartInProductDao = cartInProductDao;
        this.accountService = accountService;
    }

    public void registerProductInCart(final AuthAccount authAccount, final Long productId) {

        final AccountEntity accountEntity = accountService.searchByEmailAndPassword(authAccount);

        final CartInProductEntity cartInProductEntity =
                new CartInProductEntity(accountEntity.getCartId(), productId);

        cartInProductDao.save(cartInProductEntity);
    }

    public void deleteProductInCart(final AuthAccount authAccount, final Long productId) {
        final AccountEntity accountEntity = accountService.searchByEmailAndPassword(authAccount);

        final CartEntity cartEntity = new CartEntity(accountEntity.getCartId());

        cartInProductDao.deleteProductByCartAndProductId(cartEntity, productId);
    }
}
