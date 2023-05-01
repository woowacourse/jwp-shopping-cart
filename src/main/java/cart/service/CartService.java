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

import java.util.Collections;
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

    public Long putInCart(final MemberAuthDto memberAuthDto, final Long productId) {
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

    public List<CartProductResponseDto> findCartProductsByMember(final MemberAuthDto memberAuthDto) {
        final MemberEntity member = getMember(memberAuthDto);
        final List<CartEntity> cartEntities = cartDao.findAllByMemberId(member.getId());
        final List<Long> productIds = cartEntities.stream()
                .map(CartEntity::getProductId)
                .collect(Collectors.toList());
        if (productIds.isEmpty()) {
            return Collections.emptyList();
        }
        return productDao.findAllIn(productIds).stream()
                .map(CartProductResponseDto::from)
                .collect(Collectors.toList());
    }
}
