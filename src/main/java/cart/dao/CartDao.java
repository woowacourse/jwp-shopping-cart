package cart.dao;

import cart.controller.dto.MemberRequest;
import cart.dao.entity.ProductEntity;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao {

    long add(Long memberId, Long productId);

    List<ProductEntity> findByMember(MemberRequest request);

    boolean isExistEntity(Long memberId, Long productId);

    int deleteById(Long memberId, Long productId);

    int deleteByProductId(Long productId);
}
