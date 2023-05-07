package cart.service;

import cart.auth.AuthInfo;
import cart.dao.CartDao;
import cart.dto.entity.CartEntity;
import cart.dto.entity.MemberEntity;
import cart.dto.entity.ProductEntity;
import cart.dto.response.CartResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartDao cartDao;
    private final ProductService productService;
    private final MemberService memberService;

    public CartService(CartDao cartDao, ProductService productService, MemberService memberService) {
        this.cartDao = cartDao;
        this.productService = productService;
        this.memberService = memberService;
    }

    public List<CartResponse> findAllByEmailWithPassword(AuthInfo authInfo) {
        MemberEntity member = memberService.findByEmailWithPassword(
                authInfo.getEmail(),
                authInfo.getPassword()
        );

        List<CartEntity> carts = cartDao.findByMemberId(member.getId());

        return carts.stream()
                .map(cartEntity -> {
                    ProductEntity product = productService.findById(cartEntity.getProduct_id());
                    return new CartResponse(
                            cartEntity.getId(),
                            product.getName(),
                            product.getImgUrl(),
                            product.getPrice());
                })
                .collect(Collectors.toList());
    }

    public int save(AuthInfo authInfo, int productId) {
        MemberEntity member = memberService.findByEmailWithPassword(
                authInfo.getEmail(),
                authInfo.getPassword()
        );
        return cartDao.save(new CartEntity(member.getId(), productId));
    }
}
