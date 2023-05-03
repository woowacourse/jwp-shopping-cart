package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dto.ProductDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public void addProduct(int memberId, int productId) {
        cartDao.save(memberId, productId);
    }

    public List<ProductDto> findAllProduct(int memberId) {
        return cartDao.findAllByMemberId(memberId)
                .stream()
                .map(it -> new ProductDto(it.getId(), it.getName(), it.getImgUrl(), it.getPrice()))
                .collect(Collectors.toList());
    }

    public void deleteProduct(int memberId, int productId) {
        cartDao.delete(memberId, productId);
    }
}
