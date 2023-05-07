package cart.service;

import cart.controller.dto.CartResponse;
import cart.controller.dto.MemberRequest;
import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.Cart;
import cart.domain.Member;
import cart.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public List<CartResponse> getCartProductByMember(MemberRequest memberRequest) {
        Long memberId = memberDao.findByEmail(memberRequest.getEmail()).getId();
        List<Cart> carts = cartDao.findByMemberId(memberId);
        return carts
                .stream()
                .map(cart -> toResponse(cart.getId(), productDao.findById(cart.getProductId()).get()))
                .collect(Collectors.toList());
    }

    private CartResponse toResponse(Long cartId, Product product) {
        System.out.println("c: " + product.getImageUrl() + product.getName() + product.getPrice());
        return new CartResponse(cartId, product.getImageUrl(), product.getName(), product.getPrice());
    }

    @Transactional
    public Long addCart(Long productId, MemberRequest memberRequest) {
        Long memberId = memberDao.findByEmail(memberRequest.getEmail()).getId();
        return cartDao.save(new Cart(memberId, productId));
    }

    public void deleteCart(Long id) {
        cartDao.deleteById(id);
    }
}
