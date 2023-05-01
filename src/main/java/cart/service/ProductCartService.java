package cart.service;

import cart.dao.ProductCartDao;
import cart.dao.ProductDao;
import cart.entity.Member;
import cart.entity.Product;
import cart.entity.ProductCart;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCartService {

    private final ProductCartDao productCartDao;
    private final ProductDao productDao;

    public ProductCartService(ProductCartDao productCartDao, ProductDao productDao) {
        this.productCartDao = productCartDao;
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<Product> findAllMyProductCart(Member member) {
        List<ProductCart> carts = productCartDao.findAllByMember(member);
        return carts.stream()
                .map(ProductCart::getProductId)
                .map(productDao::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
