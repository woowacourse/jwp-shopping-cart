package cart.cartitems.service;

import cart.cartitems.dao.CartDao;
import cart.cartitems.dto.CartItemDto;
import cart.exception.DuplicateCartItemException;
import cart.product.dto.ProductDto;
import cart.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final int EXPECTED_DELETED_ITEMS_COUNT = 1;

    private final CartDao cartDao;
    private final ProductService productService;

    @Autowired
    public CartService(CartDao cartDao, ProductService productService) {
        this.cartDao = cartDao;
        this.productService = productService;
    }

    public List<ProductDto> findItemsOfCart(long memberId) {
        final List<Long> productIds = cartDao.findProductIdsByMemberId(memberId);

        return productIds.stream()
                         .map(productService::getProductById)
                         .collect(Collectors.toUnmodifiableList());
    }

    public CartItemDto addItemToCart(long memberIdToAdd, long productIdToAdd) {
        productService.validateProductExist(productIdToAdd);

        final CartItemDto itemToAdd = new CartItemDto(memberIdToAdd, productIdToAdd);
        if (cartDao.containsItem(itemToAdd)) {
            throw new DuplicateCartItemException("이미 담은 상품은 중복으로 담을 수 없습니다");
        }

        return cartDao.saveItem(itemToAdd);
    }

    public long deleteItemFromCart(long memberIdToDelete, long productIdToDelete) {
        final CartItemDto itemToDelete = new CartItemDto(memberIdToDelete, productIdToDelete);
        final int deletedItemsCount = cartDao.deleteItem(itemToDelete);

        validateItemDeleted(deletedItemsCount);

        return itemToDelete.getProductId();
    }

    private void validateItemDeleted(int deletedItemsCount) {
        if (deletedItemsCount != EXPECTED_DELETED_ITEMS_COUNT) {
            throw new NoSuchElementException("존재하지 않는 상품 번호입니다");
        }
    }
}
