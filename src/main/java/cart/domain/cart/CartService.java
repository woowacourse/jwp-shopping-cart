package cart.domain.cart;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.domain.cart.dto.ProductResponse;
import cart.domain.member.Member;
import cart.domain.member.MemberDao;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, MemberDao memberDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public Long addProductToCart(String email, Long productId) {
        Member member = memberDao.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        ProductEntity productEntity = productDao.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품입니다.")
        );

        Cart cart = new Cart(productEntity.getId(), member.getId());
        return cartDao.addProduct(cart);
    }

    public List<ProductResponse> findProductsByUserIdOnCart(String email) {
        Member member = memberDao.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        List<Product> products = cartDao.findProductsByUserId(member.getId());
        return products.stream()
                .map(p -> new ProductResponse(p.getName(), p.getImageUrl(), p.getPrice()))
                .collect(Collectors.toList());
    }

    public void deleteCartItem(String email, Long productId) {
        Member member = memberDao.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        int affectedRow = cartDao.deleteCartItem(member, productId);
        if (affectedRow != 1) {
            throw new IllegalArgumentException("잘못된 삭제 요청입니다.");
        }
    }
}
