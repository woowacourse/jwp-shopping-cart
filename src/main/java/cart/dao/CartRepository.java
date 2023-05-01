package cart.dao;

import cart.controller.dto.ProductResponse;
import cart.dao.entity.ProductEntity;
import cart.domain.Member;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Base64Utils;

@Repository
public class CartRepository {

    private final CartDao mySQLCartDao;
    private final MemberDao mySQLMemberDao;

    public CartRepository(CartDao mySQLCartDao, MemberDao mySQLMemberDao) {
        this.mySQLCartDao = mySQLCartDao;
        this.mySQLMemberDao = mySQLMemberDao;
    }

    public List<ProductResponse> getProducts(HttpServletRequest request) {
        final List<ProductEntity> productEntities = mySQLCartDao.findByMember(
            extractMember(request));

        return ProductResponse.from(productEntities);
    }

    public long add(Long productId, HttpServletRequest request) {
        Member member = extractMember(request);
        Long memberId = mySQLMemberDao.findIdByMember(member);
        if (mySQLCartDao.isExistEntity(memberId, productId)) {
            throw new DataIntegrityViolationException("");
        }
        return mySQLCartDao.add(memberId, productId);
    }

    public int remove(Long productId, HttpServletRequest request){
        Member member = extractMember(request);
        Long memberId = mySQLMemberDao.findIdByMember(member);
        return mySQLCartDao.deleteById(memberId, productId);
    }

    public Member extractMember(HttpServletRequest request) {
        String credentials = request.getHeader("authorization").substring("Basic ".length());
        String[] decoded = new String(Base64Utils.decode(credentials.getBytes())).split(":");
        if (decoded.length != 2) {
            throw new IllegalArgumentException();
        }
        return new Member(decoded[0], decoded[1]);
    }

}
