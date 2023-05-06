package cart.service.cart;

import cart.service.cart.domain.Cart;
import cart.service.cart.dto.CartServiceRequest;
import cart.service.cart.dto.ProductResponse;
import cart.service.member.MemberDao;
import cart.service.member.domain.Member;
import cart.service.product.ProductDao;
import cart.service.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, MemberDao memberDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public Long createCartItem(CartServiceRequest cartServiceRequest) {
        Member member = memberDao.findByEmail(cartServiceRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        productDao.findById(cartServiceRequest.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품입니다.")
        );

        Cart cart = new Cart(cartServiceRequest.getProductId(), member.getId());
        return cartDao.addProduct(cart);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProductsByUserIdOnCart(CartServiceRequest cartServiceRequest) {
        Member member = memberDao.findByEmail(cartServiceRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        List<Product> products = cartDao.findProductsByUserId(member.getId());
        return products.stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getImageUrl(), p.getPrice()))
                .collect(Collectors.toList());
    }

    public void deleteCartItem(CartServiceRequest cartServiceRequest) {
        Member member = memberDao.findByEmail(cartServiceRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        Long cartId = cartDao.findOneCartItem(member, cartServiceRequest.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품은 삭제할 수 없습니다.")
        );
        cartDao.deleteCartItem(cartId);
    }
}
