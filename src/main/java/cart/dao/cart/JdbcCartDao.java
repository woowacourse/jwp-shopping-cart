package cart.dao.cart;

import cart.entity.ItemEntity;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<String> findRawId(final String memberEmail) {
        String sql = "SELECT cart FROM member WHERE email = ?";

        try {
            String query = jdbcTemplate.queryForObject(sql, String.class, memberEmail);
            if(query == null){
                return new ArrayList<>();
            }
            return new ArrayList<>(List.of(query.split(",")));
        } catch (IncorrectResultSizeDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Long save(final String memberEmail, final Long itemId) {
        String sql = "update member set cart =? where email = ?";

        List<String> rawId = findRawId(memberEmail);

        if (rawId.isEmpty()) {
            jdbcTemplate.update(sql, itemId.toString(), memberEmail);
            return itemId;
        }
        rawId.add(itemId.toString());
        String currentId = rawId.stream().collect(Collectors.joining(","));
        jdbcTemplate.update(sql, currentId, memberEmail);
        return itemId;
    }

    @Override
    public Optional<List<ItemEntity>> findAll(final String memberEmail) {
        List<String> rawId = findRawId(memberEmail);
        if (rawId.isEmpty()) {
            return Optional.empty();
        }

        String currentId = rawId.stream().collect(Collectors.joining(","));
        System.out.println(currentId);
        try {
            String findAllItemsSQL = "SELECT * FROM item WHERE id IN (" + currentId + ") GROUP BY id;";
            return Optional.of(jdbcTemplate.query(findAllItemsSQL, mapRow()));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
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
        List<String> rawId = findRawId(memberEmail);

        if (!rawId.isEmpty()) {
            rawId.remove(itemId.toString());
            String currentId = rawId.stream().collect(Collectors.joining(","));
            jdbcTemplate.update(sql, currentId, memberEmail);
        }
    }
}
