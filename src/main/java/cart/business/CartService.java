package cart.business;

import cart.entity.ProductEntity;
import cart.persistence.CartDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private CartDao cartDao;
    private ProductService productService;

    public CartService(CartDao cartDao, ProductService productService) {
        this.cartDao = cartDao;
        this.productService = productService;
    }

    public List<ProductEntity> findProductsByMemberId(Integer memberId) {
        return cartDao.findAllProductsByMemberId(memberId);
    }
}
