package cart.service;

import cart.dao.AccountEntity;
import cart.dao.CartInProductDao;
import cart.dao.CartInProductEntity;
import cart.domain.account.AuthAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartInProductDao cartInProductDao;
    private final AccountService accountService;

    public CartService(final CartInProductDao cartInProductDao, final AccountService accountService) {
        this.cartInProductDao = cartInProductDao;
        this.accountService = accountService;
    }

    public void registerProductInCart(final AuthAccount authAccount, final Long productId) {

        final AccountEntity accountEntity = accountService.searchByEmailAndPassword(authAccount);

        final CartInProductEntity cartInProductEntity =
                new CartInProductEntity(accountEntity.getCartId(), productId);

        cartInProductDao.save(cartInProductEntity);
    }
}
