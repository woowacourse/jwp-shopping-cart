package cart.service;

import cart.domain.Item;
import cart.dto.response.ItemResponse;
import cart.persistence.CartDao;
import cart.persistence.ProductsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartDao cartDao;
    private final ProductsDao productsDao;

    public CartService(CartDao cartDao, ProductsDao productsDao) {
        this.cartDao = cartDao;
        this.productsDao = productsDao;
    }

    public ItemResponse createItem(Long memberId, Long productId) {
        Long registeredItemId = cartDao.createItem(memberId, productId);
        Item registered = cartDao.findItemById(registeredItemId);

        return new ItemResponse(
                registered.getId(),
                registered.getCartId(),
                registered.getProductId()
        );
    }
}
