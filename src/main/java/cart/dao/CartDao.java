package cart.dao;

import cart.dao.entity.ProductEntity;
import cart.domain.Member;
import java.util.List;

public interface CartDao {

    long add(Long memberId, Long productId);

    List<ProductEntity> findByMember(Member member);

    boolean isExistEntity(Long memberId, Long productId);

    int deleteById(Long memberId, Long productId);
}
