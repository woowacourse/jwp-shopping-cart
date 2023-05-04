package cart.service;

import cart.auth.AuthAccount;
import cart.dao.AccountEntity;
import cart.dao.CartEntity;
import cart.dao.CartInProductDao;
import cart.dao.CartInProductEntity;
import cart.service.dto.ProductSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartQueryService {

    private final CartInProductDao cartInProductDao;
    private final AccountService accountService;
    private final ProductQueryService productQueryService;

    public CartQueryService(final CartInProductDao cartInProductDao, final AccountService accountService,
                            final ProductQueryService productQueryService) {
        this.cartInProductDao = cartInProductDao;
        this.accountService = accountService;
        this.productQueryService = productQueryService;
    }

    public List<ProductSearchResponse> findAllProductsInCart(final AuthAccount authAccount) {
        final AccountEntity accountEntity = accountService.searchByEmailAndPassword(authAccount);

        final CartEntity cartEntity = new CartEntity(accountEntity.getCartId());

        final List<CartInProductEntity> productsInCart = cartInProductDao.findProductsByCart(cartEntity);

        if (productsInCart.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Long> productIds =
                productsInCart.stream()
                              .map(CartInProductEntity::getProductId)
                              .collect(Collectors.toList());

        return productQueryService.searchProductsByIds(productIds);
    }
}
