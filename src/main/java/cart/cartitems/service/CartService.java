package cart.cartitems.service;

import cart.authorization.AuthInfo;
import cart.authorization.AuthorizationService;
import cart.cartitems.dao.CartDao;
import cart.cartitems.dto.CartItemDto;
import cart.cartitems.dto.request.CartItemAddRequest;
import cart.exception.DuplicateCartItemException;
import cart.product.dto.ProductDto;
import cart.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartService {

    private final AuthorizationService authorizationService;
    private final CartDao cartDao;
    private final ProductService productService;

    @Autowired
    public CartService(AuthorizationService authorizationService, CartDao cartDao, ProductService productService) {
        this.authorizationService = authorizationService;
        this.cartDao = cartDao;
        this.productService = productService;
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

    public long deleteItemFromCart(AuthInfo authInfo, Long productId) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("잘못된 상품 번호입니다");
        }

        final CartItemDto toDelete = new CartItemDto(getIdOfMember(authInfo), productId);
        final int deletedItemsCount = cartDao.deleteItem(toDelete);

        if (deletedItemsCount != 1) {
            throw new NoSuchElementException("존재하지 않는 상품 번호입니다");
        }

        return toDelete.getProductId();
    }

    private long getIdOfMember(AuthInfo authInfo) {
        return authorizationService.authenticateMember(authInfo);
    }
}
