package cart.dao.cart;

import cart.entity.ItemEntity;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class JdbcCartDao implements CartDao {

    public static final String DELIMITER = ",";

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<String> findIdsByEmail(final String email) {
        String sql = "SELECT cart FROM member WHERE email = ?";

        try {
            String query = jdbcTemplate.queryForObject(sql, String.class, email);
            if (query == null) {
                return new ArrayList<>();
            }
            return new ArrayList<>(List.of(query.split(DELIMITER)));
        } catch (IncorrectResultSizeDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Long save(final String memberEmail, final Long itemId) {
        String sql = "update member set cart =? where email = ?";

        List<String> rawId = findIdsByEmail(memberEmail);
        rawId.add(itemId.toString());
        String currentId = String.join(DELIMITER, rawId);
        jdbcTemplate.update(sql, currentId, memberEmail);
        return itemId;
    }

    @Override
    public Optional<Map<ItemEntity, Long>> findAll(final String memberEmail) {
        List<String> rawId = findIdsByEmail(memberEmail);
        String currentId = String.join(DELIMITER, rawId);
        try {
            String sql = "SELECT * FROM item WHERE id IN (" + currentId + ") GROUP BY id;";
            List<ItemEntity> query = jdbcTemplate.query(sql, mapRow());

            return Optional.of(countItemsByEntity(rawId, query));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    private Map<ItemEntity, Long> countItemsByEntity(final List<String> rawId, final List<ItemEntity> query) {
        Map<Long, ItemEntity> items = query.stream()
                .collect(Collectors.toMap(ItemEntity::getId, Function.identity()));

        return rawId.stream()
                .map(id->items.get(Long.parseLong(id)))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private RowMapper<ItemEntity> mapRow() {
        return (rs, rowNum) -> {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            String itemUrl = rs.getString(3);
            int price = rs.getInt(4);

            return new ItemEntity(id, name, itemUrl, price);
        };
    }

    @Override
    public void delete(final String memberEmail, final Long itemId) {
        String sql = "update member set cart = ? where email = ?";

        List<String> rawId = findIdsByEmail(memberEmail);
        rawId.remove(itemId.toString());
        String currentId = String.join(DELIMITER, rawId);

        jdbcTemplate.update(sql, currentId, memberEmail);
    }
}
