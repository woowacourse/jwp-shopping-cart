package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dto.MemberAuthDto;
import cart.dto.response.CartProductResponseDto;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.product.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;

    public CartService(final CartDao cartDao, final ProductDao productDao, final MemberDao memberDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    public Long putInCart(final Long productId, final MemberAuthDto memberAuthDto) {
        final ProductEntity product = getProduct(productId);
        final MemberEntity member = getMember(memberAuthDto);
        return cartDao.save(new CartEntity(member.getId(), product.getId()));
    }

    private ProductEntity getProduct(final Long productId) {
        return productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    private MemberEntity getMember(final MemberAuthDto memberAuthDto) {
        return memberDao.findByEmailAndPassword(memberAuthDto.getEmail(), memberAuthDto.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 회원입니다."));
    }

    public List<CartProductResponseDto> findCartItemsForMember(final MemberAuthDto memberAuthDto) {
        final MemberEntity member = getMember(memberAuthDto);
        final List<CartEntity> cartEntities = cartDao.findAllByMemberId(member.getId());
        return cartEntities.stream()
                .map(cartEntity -> {
                    final ProductEntity product = getProduct(cartEntity.getProductId());
                    return CartProductResponseDto.from(cartEntity.getId(), product);
                })
                .collect(Collectors.toList());
    }

    public void removeCartItem(final Long cartId, final MemberAuthDto memberAuthDto) {
        cartDao.delete(cartId);
    }
}
