package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dto.CartResponse;
import cart.dto.MemberAuthRequest;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import cart.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;

    public CartService(final CartDao cartDao, final MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    public long saveProduct(MemberAuthRequest memberAuthRequest, long productId) {
        String email = memberAuthRequest.getEmail();
        String password = memberAuthRequest.getPassword();
        checkMemberExistByMemberInfo(email, password);
        MemberEntity findMemberEntity = memberDao.selectByEmailAndPassword(email, password);
        CartEntity cartEntity = new CartEntity.Builder()
                .memberId(findMemberEntity.getMemberId())
                .productId(productId)
                .build();
        return cartDao.insert(cartEntity);
    }

    private void checkMemberExistByMemberInfo(String email, String password) {
        if (memberDao.isNotExistByEmailAndPassword(email, password)) {
            throw new MemberNotFoundException("사용자 인증 정보에 해당하는 사용자가 존재하지 않습니다.");
        }
    }

    public List<CartResponse> findAllProductByMemberInfo(MemberAuthRequest memberAuthRequest) {
        String email = memberAuthRequest.getEmail();
        String password = memberAuthRequest.getPassword();
        checkMemberExistByMemberInfo(email, password);
        MemberEntity findMemberEntity = memberDao.selectByEmailAndPassword(email, password);
        List<ProductEntity> productEntities = cartDao.selectAllProductByMemberId(findMemberEntity.getMemberId());
        return productEntities.stream()
                .map(productEntity -> new CartResponse(productEntity.getProductId(), productEntity.getImgUrl(),
                        productEntity.getName(), productEntity.getPrice()))
                .collect(Collectors.toUnmodifiableList());
    }
}
