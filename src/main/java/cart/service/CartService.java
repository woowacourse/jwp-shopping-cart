package cart.service;

import cart.domain.Item;
import cart.domain.product.Product;
import cart.dto.response.CartResponse;
import cart.dto.response.ItemResponse;
import cart.dto.response.ProductResponse;
import cart.persistence.CartDao;
import cart.persistence.ProductsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public CartResponse readAllItemsByMemberId(Long memberId) {
        List<Long> productIds = cartDao.findAllItems(memberId);
        List<Product> products = new ArrayList<>();

        if (!productIds.isEmpty()) {
            products = productsDao.findAll(productIds);
        }

        List<ProductResponse> responses = products.stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImageUrl()))
                .collect(Collectors.toList());

        return new CartResponse(memberId, responses);
    }
}
