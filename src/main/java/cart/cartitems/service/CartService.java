package cart.cartitems.service;

import cart.authorization.AuthInfo;
import cart.authorization.AuthorizationService;
import cart.cartitems.dao.CartDao;
import cart.cartitems.dto.CartItemDto;
import cart.cartitems.dto.request.CartItemAddRequest;
import cart.exception.DuplicateCartItemException;
import cart.product.dto.ProductDto;
import cart.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductService productService;
    private final AuthorizationService authorizationService;

    @Autowired
    public CartService(CartDao cartDao, ProductService productService, AuthorizationService authorizationService) {
        this.cartDao = cartDao;
        this.productService = productService;
        this.authorizationService = authorizationService;
    }

    public List<ProductDto> findItemsOfCart(AuthInfo authInfo) {
        final List<Long> productIds = cartDao.findProductIdsByMemberId(getIdOfMember(authInfo));

        return productIds.stream()
                         .map(productService::getProductById)
                         .collect(Collectors.toUnmodifiableList());
    }

    public CartItemDto addItemToCart(AuthInfo authInfo, CartItemAddRequest cartItemAddRequest) {
        final long productIdToAdd = cartItemAddRequest.getProductId();

        productService.validateProductExist(productIdToAdd);

        final CartItemDto itemToAdd = new CartItemDto(getIdOfMember(authInfo), productIdToAdd);
        if (cartDao.containsItem(itemToAdd)) {
            throw new DuplicateCartItemException("이미 담은 상품은 중복으로 담을 수 없습니다");
        }

        return cartDao.saveItem(itemToAdd);
    }

    public long deleteItemFromCart(AuthInfo authInfo, Long productIdToDelete) {
        if (Objects.isNull(productIdToDelete)) {
            throw new IllegalArgumentException("잘못된 상품 번호입니다");
        }

        final CartItemDto itemToDelete = new CartItemDto(getIdOfMember(authInfo), productIdToDelete);
        final int deletedItemsCount = cartDao.deleteItem(itemToDelete);

        if (deletedItemsCount != 1) {
            throw new NoSuchElementException("존재하지 않는 상품 번호입니다");
        }

        return itemToDelete.getProductId();
    }

    private long getIdOfMember(AuthInfo authInfo) {
        return authorizationService.authenticateMember(authInfo);
    }
}
