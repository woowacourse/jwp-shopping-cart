package cart.cartitems.service;

import cart.cartitems.dao.CartItemDao;
import cart.cartitems.dto.CartItemDto;
import cart.cartitems.dto.request.CartItemAddRequest;
import cart.infrastructure.AuthInfo;
import cart.infrastructure.AuthorizationService;
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
public class CartItemsService {

    private final AuthorizationService authorizationService;
    private final CartItemDao cartItemDao;
    private final ProductService productService;

    @Autowired
    public CartItemsService(AuthorizationService authorizationService, CartItemDao cartItemDao, ProductService productService) {
        this.authorizationService = authorizationService;
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    public List<ProductDto> findItemsOfCart(AuthInfo authInfo) {
        final List<Long> itemsIds = cartItemDao.findProductIdsByMemberId(getIdOfMember(authInfo));

        return itemsIds.stream()
                       .map(productService::getById)
                       .collect(Collectors.toUnmodifiableList());
    }

    public CartItemDto addItemToCart(AuthInfo authInfo, CartItemAddRequest cartItemAddRequest) {
        final long productIdToAdd = cartItemAddRequest.getProductId();

        productService.validateProductExist(productIdToAdd);

        try {
            final CartItemDto toAdd = new CartItemDto(getIdOfMember(authInfo), productIdToAdd);
            return cartItemDao.saveItemOfMember(toAdd);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("이미 카트에 존재하는 상품입니다");
        }
    }

    public long deleteItemFromCart(AuthInfo authInfo, Long productId) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("잘못된 상품번호입니다");
        }

        final CartItemDto toDelete = new CartItemDto(getIdOfMember(authInfo), productId);
        final int deletedItemsCount = cartItemDao.deleteItem(toDelete);

        if (deletedItemsCount != 1) {
            throw new NoSuchElementException("잘못된 상품 번호입니다");
        }

        return toDelete.getProductId();
    }

    private long getIdOfMember(AuthInfo authInfo) {
        return authorizationService.authenticateMember(authInfo);
    }
}
