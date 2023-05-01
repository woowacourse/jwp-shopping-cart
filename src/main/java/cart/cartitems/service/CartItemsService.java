package cart.cartitems.service;

import cart.cartitems.dao.CartItemDao;
import cart.cartitems.dto.CartItemDto;
import cart.cartitems.dto.request.ProductAddRequest;
import cart.infrastructure.AuthInfo;
import cart.infrastructure.AuthorizationService;
import cart.product.dto.ProductDto;
import cart.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        final List<Long> memberCartItemsIds = cartItemDao.findProductIdsByMemberId(getIdOfMember(authInfo));

        return memberCartItemsIds.stream()
                                 .map(productService::getById)
                                 .collect(Collectors.toUnmodifiableList());
    }

    public void addItemToCart(AuthInfo authInfo, ProductAddRequest productAddRequest) {
        final long productIdToAdd = productAddRequest.getProductId();

        productService.validateProductExist(productIdToAdd);

        try {
            cartItemDao.saveItemOfMember(new CartItemDto(getIdOfMember(authInfo), productIdToAdd));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("이미 카트에 존재하는 상품입니다");
        }
    }

    private long getIdOfMember(AuthInfo authInfo) {
        return authorizationService.authenticateMember(authInfo);
    }
}
