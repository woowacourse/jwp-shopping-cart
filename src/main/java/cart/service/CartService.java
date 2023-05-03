package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dao.entity.CartEntity;
import cart.dao.entity.MemberEntity;
import cart.dao.entity.ProductEntity;
import cart.dto.AuthDto;
import cart.dto.response.CartResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final MemberService memberService;
    private final ProductService productService;
    private final CartDao cartDao;

    @Autowired
    public CartService(final MemberService memberService, final CartDao cartDao, final ProductService productService) {
        this.memberService = memberService;
        this.cartDao = cartDao;
        this.productService = productService;
    }

    @Transactional
    public void insert(final Long productId, final AuthDto authDto) {
        final MemberEntity memberEntity = memberService.findMember(authDto);
        final Optional<CartEntity> cartEntity = cartDao.findCart(productId, memberEntity.getId());
        if (cartEntity.isPresent()) {
            throw new IllegalArgumentException("이미 카트에 담겨진 제품입니다.");
        }
        cartDao.insert(productId, memberEntity.getId());
    }

    @Transactional
    public void delete(final Long productId, final AuthDto authDto) {
        final MemberEntity memberEntity = memberService.findMember(authDto);
        final Optional<CartEntity> cartEntity = cartDao.findCart(productId, memberEntity.getId());
        if (cartEntity.isEmpty()) {
            throw new IllegalArgumentException("삭제하려는 카트가 존재하지 않습니다");
        }
        cartDao.delete(cartEntity.get());
    }

    public List<CartResponse> selectCart(final AuthDto authDto) {
        final MemberEntity memberEntity = memberService.findMember(authDto);
        final Long memberId = memberEntity.getId();
        final Optional<List<CartEntity>> cartEntities = cartDao.findAllByMemberId(memberId);
        if (cartEntities.isEmpty()) {
            return List.of();
        }
        final List<ProductEntity> productEntities = cartEntities.get().stream()
                .map(cartEntity -> productService.findById(cartEntity.getProductId()))
                .collect(Collectors.toUnmodifiableList());
        return productEntities.stream()
                .map(productEntity -> new CartResponse(
                        productEntity.getId(),
                        productEntity.getName(),
                        productEntity.getPrice(),
                        productEntity.getImage()
                )).collect(Collectors.toUnmodifiableList());
    }
}
