package cart.dao.member;

import cart.entity.ItemEntity;

import java.util.Map;
import java.util.Optional;

public interface MemberCartDao {

    Long save(String memberEmail, Long itemId);

    Optional<Map<ItemEntity, Long>> findAll(String memberEmail);

    void delete(String memberEmail, Long itemId);
}
