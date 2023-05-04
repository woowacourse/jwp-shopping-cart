package cart.dao;

import cart.dao.entity.ProductEntity;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao {

    long add(Long memberId, Long productId);

    List<ProductEntity> findByMemberId(Long memberId);

    boolean isExistEntity(Long memberId, Long productId);

    int deleteById(Long memberId, Long productId);

    int deleteByProductId(Long productId);
}
