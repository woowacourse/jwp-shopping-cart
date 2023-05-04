package cart.dao;

import cart.dao.entity.ProductEntity;
import cart.domain.Member;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    private static final String MEMBER_NOT_FOUND_MESSAGE = "존재하지 않는 회원 정보입니다.";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "존재하지 않는 상품 정보입니다.";
    private static final String ALREADY_EXIST_MESSAGE = "장바구니에 해당 제품이 이미 존재합니다.";

    private final CartDao mySQLCartDao;
    private final MemberDao mySQLMemberDao;
    private final ProductDao mySQLProductDao;

    public CartRepository(CartDao mySQLCartDao, MemberDao mySQLMemberDao,
        ProductDao mySQLProductDao) {
        this.mySQLCartDao = mySQLCartDao;
        this.mySQLMemberDao = mySQLMemberDao;
        this.mySQLProductDao = mySQLProductDao;
    }

    public List<ProductEntity> getProducts(Member member) {
        return mySQLCartDao.findByMember(member);
    }

    public long add(Long productId, Member member) {
        Long memberId = mySQLMemberDao.findIdByMember(member)
            .orElseThrow(() -> new NoSuchElementException(MEMBER_NOT_FOUND_MESSAGE));
        validateIfCartAleadyExist(productId, memberId);
        validateIfProductExist(productId);
        return mySQLCartDao.add(memberId, productId);
    }

    private void validateIfCartAleadyExist(Long productId, Long memberId) {
        if (mySQLCartDao.isExistEntity(memberId, productId)) {
            throw new DataIntegrityViolationException(ALREADY_EXIST_MESSAGE);
        }
    }

    private void validateIfProductExist(Long productId) {
        if (mySQLProductDao.findById(productId).isEmpty()) {
            throw new NoSuchElementException(PRODUCT_NOT_FOUND_MESSAGE);
        }
    }

    public int remove(Long productId, Member member) {
        Long memberId = mySQLMemberDao.findIdByMember(member).orElseThrow(() -> new
            NoSuchElementException(MEMBER_NOT_FOUND_MESSAGE));
        validateIfProductExist(productId);
        return mySQLCartDao.deleteById(memberId, productId);
    }

}
