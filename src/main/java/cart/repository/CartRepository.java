package cart.repository;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dao.entity.CartEntity;
import cart.dao.entity.CartProductEntity;
import cart.dao.entity.MemberEntity;
import cart.dao.entity.ProductEntity;
import cart.domain.Cart;
import cart.domain.CartProduct;
import cart.domain.Product;
import cart.domain.Products;
import cart.dto.auth.AuthInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Repository
public class CartRepository {

    private final ProductDao productDao;
    private final MemberDao memberDao;
    private final CartDao cartDao;

    public CartRepository(ProductDao productDao, MemberDao memberDao, CartDao cartDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
        this.cartDao = cartDao;
    }

    public Products getAllProducts() {
        List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
                .map(Product::new)
                .collect(collectingAndThen(toList(), Products::new));
    }

    public Cart getCartProductsByMemberId(final Long memberId) {
        List<CartProductEntity> productEntities = cartDao.findProductsByMemberId(memberId);
        return productEntities.stream()
                .map(CartProduct::new)
                .collect(collectingAndThen(toList(), Cart::new));
    }

    public void addProductToCart(final Long productId, final Long memberId) {
        cartDao.insert(new CartEntity.Builder()
                .productId(productId)
                .memberId(memberId)
                .build());
    }

    public Long findIdByAuthInfo(final AuthInfo authInfo) {
        final MemberEntity memberEntity = new MemberEntity.Builder()
                .email(authInfo.getEmail())
                .password(authInfo.getPassword())
                .build();
        return memberDao.findIdByAuthInfo(memberEntity);
    }

    public CartProduct getCartProductByCartId(final Long cartId) {
        final ProductEntity productEntity = productDao.findProductByCartId(cartId)
                .orElseThrow(() -> new NoSuchElementException("디버깅: 없는 프로덕트를 찾으려고 합니다"));
        return new CartProduct(cartId, new Product(productEntity));
    }

    public void deleteProductFromCart(final Long cartId) {
        cartDao.deleteProductFromCart(cartId);
    }

    public void insertNewProduct(final Product product) {
        final ProductEntity newProductEntity = new ProductEntity.Builder()
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
        productDao.insert(newProductEntity);
    }

    public Product getProductById(final Long productId) {
        final ProductEntity productEntity = productDao.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("찾는 상품이 없습니다."));
        return new Product(productEntity);
    }

    public void updateProduct(final Long productId, final Product updatedProduct) {
        final ProductEntity productEntity = new ProductEntity.Builder()
                .id(productId)
                .name(updatedProduct.getName())
                .price(updatedProduct.getPrice())
                .image(updatedProduct.getImage())
                .build();
        productDao.update(productEntity);
    }

    public List<ProductDto> getAllProductsWithId() {
        final List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public void deleteProduct(final Long productId) {
        productDao.delete(productId);
    }
}
