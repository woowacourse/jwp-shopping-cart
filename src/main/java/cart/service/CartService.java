package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dao.entity.CartEntity;
import cart.dao.entity.ProductEntity;
import cart.domain.Cart;
import cart.domain.Product;
import cart.dto.AddCartRequestDto;
import cart.dto.auth.AuthInfo;
import cart.dto.request.RequestCreateProductDto;
import cart.dto.request.RequestUpdateProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartService {

    private static final int MINIMUM_AFFECTED_ROWS = 1;

    private final ProductDao productDao;
    private final MemberDao memberDao;
    private final CartDao cartDao;

    @Autowired
    public CartService(final ProductDao productDao, final MemberDao memberDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
        this.cartDao = cartDao;
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    @Transactional
    public void insert(final RequestCreateProductDto requestCreateProductDto) {
        final ProductEntity newProductEntity = new ProductEntity.Builder()
                .name(requestCreateProductDto.getName())
                .price(requestCreateProductDto.getPrice())
                .image(requestCreateProductDto.getImage())
                .build();
        productDao.insert(newProductEntity);
    }

    @Transactional
    public void update(final Long id, final RequestUpdateProductDto requestUpdateProductDto) {
        final ProductEntity oldProductEntity = productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("찾는 상품이 없습니다."));
        final ProductEntity productEntity = new ProductEntity.Builder()
                .id(id)
                .name(requestUpdateProductDto.getName().orElse(oldProductEntity.getName()))
                .price(requestUpdateProductDto.getPrice().orElse(oldProductEntity.getPrice()))
                .image(requestUpdateProductDto.getImage().orElse(oldProductEntity.getImage()))
                .build();
        final int updatedRows = productDao.update(productEntity);
        validateAffectedRowsCount(updatedRows);
    }

    private void validateAffectedRowsCount(final int affectedRows) {
        if (affectedRows < MINIMUM_AFFECTED_ROWS) {
            throw new IllegalArgumentException("접근하려는 데이터가 존재하지 않습니다.");
        }
    }

    @Transactional
    public void delete(final Long id) {
        final int affectedRows = productDao.delete(id);
        validateAffectedRowsCount(affectedRows);
    }

    @Transactional
    public void addProductToCart(final AddCartRequestDto addCartRequestDto, final Long memberId) {
        final List<ProductEntity> productEntities = cartDao.findProductsByMemberId(memberId);

        final Cart cart = Cart.from(productEntities);
        final Product addingProduct = new Product(productDao.findById(addCartRequestDto.getProductId()).get());
        cart.addProduct(addingProduct);

        cartDao.insert(new CartEntity.Builder()
                .productId(addCartRequestDto.getProductId())
                .memberId(memberId)
                .build()
        );
    }

    @Transactional
    public void deleteProductFromCart(final Long cartId) {
        cartDao.deleteProductFromCart(cartId);
    }

    public List<CartEntity> findCarts(AuthInfo authInfo) {
        return cartDao.findCartsByMemberId(memberDao.findIdByAuthInfo(authInfo.getEmail(), authInfo.getPassword()));
    }

    public ProductEntity findProduct(Long productId) {
        return productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("디버깅: 상품 테이블에 없는 상품 ID에 접근을 시도하고 있습니다."));
    }
}
