package cart.service;

import cart.auth.AuthMember;
import cart.auth.AuthenticationException;
import cart.dao.MemberDao;
import cart.dao.ProductCartDao;
import cart.dao.ProductDao;
import cart.dto.CartsResponse;
import cart.dto.ProductCartResponse;
import cart.entity.Member;
import cart.entity.Product;
import cart.entity.ProductCart;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCartService {

    private final ProductCartDao productCartDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;

    public ProductCartService(ProductCartDao productCartDao, ProductDao productDao, MemberDao memberDao) {
        this.productCartDao = productCartDao;
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public CartsResponse findAllMyProductCart(AuthMember authMember) {
        Member member = getAuthenticationedMember(authMember);
        List<ProductCart> carts = productCartDao.findAllByMemberId(member.getId());
        List<Long> cartIds = carts.stream()
                .map(ProductCart::getId)
                .collect(Collectors.toList());

        List<Product> products = getProducts(carts);

        return CartsResponse.of(products, cartIds);
    }

    private Member getAuthenticationedMember(AuthMember authMember) {
        Member member = memberDao.findByEmail(authMember.getEmail())
                .orElseThrow(AuthenticationException::new);
        if (!member.matchingPassword(authMember.getPassword())) {
            throw new AuthenticationException();
        }
        return member;
    }

    private List<Product> getProducts(List<ProductCart> carts) {
        List<Long> productIds = carts.stream()
                .map(ProductCart::getProductId)
                .collect(Collectors.toList());
        return productDao.findByIds(productIds);
    }

    @Transactional
    public ProductCartResponse addCart(Long productId, AuthMember authMember) {
        Member member = getAuthenticationedMember(authMember);
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다"));
        ProductCart savedProductCart = productCartDao.save(new ProductCart(product.getId(), member.getId()));
        return ProductCartResponse.from(savedProductCart);
    }

    @Transactional
    public void deleteProductInMyCart(Long cartId, AuthMember authMember) {
        Member member = getAuthenticationedMember(authMember);
        productCartDao.deleteByIdAndMemberId(cartId, member.getId());
    }
}
