package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dto.product.ProductDto;
import cart.exception.ProductNotFoundException;
import cart.repository.CartDao;
import cart.repository.ProductDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public void addToCart(Long memberId, Long productId) {
        validateId(productId);
        cartDao.save(memberId, productId);
    }

    public List<ProductDto> findAllProducts(Long memberId) {
        return cartDao.findAllProductByMemberId(memberId).stream()
                .map(ProductDto::fromEntity)
                .collect(toList());
    }

    private void validateId(Long productId) {
        if (!productDao.existsById(productId)) {
            throw new ProductNotFoundException("존재하지 않는 상품의 ID 입니다.");
        }
    }

    public void deleteProduct(Long memberId, Long productId) {
        validateId(productId);
        cartDao.delete(memberId, productId);
    }
}
