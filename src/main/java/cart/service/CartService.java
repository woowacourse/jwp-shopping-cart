package cart.service;

import cart.auth.AuthMember;
import cart.controller.dto.response.CartResponse;
import cart.controller.dto.response.ProductResponse;
import cart.dao.CartDao;
import cart.dao.CartEntity;
import cart.dao.MemberDao;
import cart.dao.MemberEntity;
import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.domain.Member;
import cart.domain.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final ProductDao productDao;
    private final MemberDao memberDao;
    private final CartDao cartDao;

    public CartService(final ProductDao productDao, final MemberDao memberDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
        this.cartDao = cartDao;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findAll(final AuthMember requestMember) {
        Optional<MemberEntity> findMemberEntity = memberDao.findMemberById(requestMember.getId());
        Member findMember = makeFinishedValidateExistMember(findMemberEntity, requestMember);

        List<CartEntity> cartEntities = cartDao.findCartByMemberId(findMember.getId());
        return cartEntities.stream()
                .map(cartEntity -> {
                    int productId = cartEntity.getProductId();
                    Product product = makeProduct(productDao.findById(productId).get());
                    ProductResponse productResponse = new ProductResponse(product.getId(), product.getName(),
                            product.getImageUrl(), product.getPrice());
                    return new CartResponse(cartEntity.getId(), productResponse);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void addCart(final int productId, final AuthMember requestMember) {
        checkExistProduct(productId);
        Optional<MemberEntity> findMemberEntity = memberDao.findMemberById(requestMember.getId());
        Member findMember = makeFinishedValidateExistMember(findMemberEntity, requestMember);
        findMember.checkPassword(requestMember.getPassword());

        cartDao.addCart(makeCartEntity(productId, findMember));
    }

    @Transactional
    public void deleteCart(final int cartId, final AuthMember requestMember) {
        Optional<MemberEntity> findMemberEntity = memberDao.findMemberById(requestMember.getId());
        makeFinishedValidateExistMember(findMemberEntity, requestMember);
        cartDao.deleteById(cartId);
    }

    private void checkExistProduct(final int id) {
        productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 id를 확인해주세요."));
    }

    private CartEntity makeCartEntity(final int productId, final Member member) {
        return new CartEntity(productId, member.getId());
    }

    private Product makeProduct(final ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(),
                productEntity.getPrice());
    }

    private Member makeMember(final MemberEntity memberEntity) {
        return new Member(memberEntity.getId(), memberEntity.getEmail(), memberEntity.getPassword());
    }

    private Member makeFinishedValidateExistMember(final Optional<MemberEntity> findMember,
                                                   final AuthMember requestMember) {
        if (findMember.isEmpty()) {
            throw new IllegalArgumentException("해당 id 멤버가 없습니다. 다시 확인해주세요.");
        }

        Member member = makeMember(findMember.get());
        member.checkEmail(requestMember.getEmail());
        member.checkPassword(requestMember.getPassword());

        return member;
    }

}
