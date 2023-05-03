package cart.service;

import cart.dao.ProductCartDao;
import cart.dao.ProductDao;
import cart.dto.CartsResponse;
import cart.dto.ProductCartResponse;
import cart.entity.Member;
import cart.entity.Product;
import cart.entity.ProductCart;
import java.util.List;
import java.util.NoSuchElementException;
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
    public CartsResponse findAllMyProductCart(Member member) {
        List<ProductCart> carts = productCartDao.findAllByMember(member);
        List<Long> cartIds = carts.stream()
                .map(ProductCart::getId)
                .collect(Collectors.toList());

        List<Product> products = getProducts(carts);

        return CartsResponse.of(products, cartIds);
    }

    private List<Product> getProducts(List<ProductCart> carts) {
        return carts.stream()
                .map(ProductCart::getProductId)
                .map(productDao::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductCartResponse addCart(Long productId, Member member) {
        Product product = productDao.findById(productId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("해당 상품이 없습니다");
                });
        ProductCart savedProductCart = productCartDao.save(new ProductCart(product.getId(), member.getId()));
        return ProductCartResponse.from(savedProductCart);
    }

    @Transactional
    public void deleteProductInMyCart(Long cartId, Member member) {
        if (!productCartDao.existByCartIdAndMember(cartId, member)) {
            throw new NoSuchElementException("장바구니에 물품이 없습니다");
        }
        productCartDao.deleteById(cartId);
    }
}
