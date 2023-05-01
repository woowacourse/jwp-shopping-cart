package cart.cartitems.service;

import cart.cartitems.dao.CartItemDao;
import cart.infrastructure.AuthInfo;
import cart.infrastructure.AuthorizationService;
import cart.product.dto.ProductDto;
import cart.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        final long memberId = authorizationService.authenticateMember(authInfo);
        final List<Long> memberCartItemsIds = cartItemDao.findProductIdsByMemberId(memberId);

        return memberCartItemsIds.stream()
                                 .map(productService::getById)
                                 .collect(Collectors.toUnmodifiableList());
    }
}
