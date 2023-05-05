package cart.service;

import cart.domain.Cart;
import cart.domain.Member;
import cart.domain.MemberPassword;
import cart.domain.Product;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.MemberDao;
import cart.persistence.dto.CartDto;
import cart.persistence.entity.CartEntity;
import cart.persistence.entity.MemberEntity;
import cart.service.dto.CartResponse;
import cart.service.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;

    public CartService(CartDao cartDao, MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    @Transactional
    public long addCart(final String memberEmail, final Long productId) {
        final MemberEntity memberEntity = getMemberEntity(memberEmail);
        final CartEntity cartEntity = new CartEntity(memberEntity.getId(), productId);
        return cartDao.insert(cartEntity);
    }

    @Transactional
    public void deleteCart(final String memberEmail, final Long productId) {
        final MemberEntity memberEntity = getMemberEntity(memberEmail);
        int deletedCount = cartDao.deleteByMemberId(memberEntity.getId(), productId);
        if (deletedCount != 1) {
            throw new GlobalException(ErrorCode.CART_INVALID_DELETE);
        }
    }

    public CartResponse getProductsByMemberEmail(final String memberEmail) {
        final MemberEntity memberEntity = getMemberEntity(memberEmail);
        final List<CartDto> cartDtos = cartDao.getProductsByMemberId(memberEntity.getId());
        final Cart cart = convertToCart(memberEntity, cartDtos);
        final List<ProductResponse> productResponses = convertToProductResponse(cart);
        final int productCount = cart.getProductCount();
        return new CartResponse(productCount, productResponses);
    }

    private MemberEntity getMemberEntity(final String memberEmail) {
        return memberDao.findByEmail(memberEmail)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Cart convertToCart(final MemberEntity memberEntity, final List<CartDto> cartDtos) {
        final Member member = convertToMember(memberEntity);
        final List<Product> products = convertToProducts(cartDtos);
        return new Cart(member, products);
    }

    private Member convertToMember(final MemberEntity memberEntity) {
        return Member.create(memberEntity.getEmail(), MemberPassword.decodePassword(memberEntity.getPassword()),
            memberEntity.getNickname(), memberEntity.getTelephone(), memberEntity.getRole());
    }

    private List<Product> convertToProducts(final List<CartDto> cartDtos) {
        return cartDtos.stream()
            .map(cartDto -> Product.createWithId(cartDto.getProductId(), cartDto.getProductName(),
                cartDto.getProductImageUrl(), cartDto.getProductPrice(), cartDto.getProductCategory()))
            .collect(Collectors.toUnmodifiableList());
    }

    private List<ProductResponse> convertToProductResponse(final Cart cart) {
        final List<Product> products = cart.getProducts();
        return products.stream()
            .map(product -> new ProductResponse(product.getId(), product.getName(), product.getImageUrl(),
                product.getPrice(), product.getCategory().name()))
            .collect(Collectors.toUnmodifiableList());
    }
}
