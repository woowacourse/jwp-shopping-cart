package cart.dao.cart;

import cart.entity.ItemEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Repository
public class JdbcCartDao implements CartDao {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Optional<String> findRawItems(final String memberEmail) {
        String sql = "SELECT cart FROM member WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, String.class, memberEmail));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public ItemEntity save(final String memberEmail, final ItemEntity item) {
        String sql = "update member set cart =? where email = ?";

        Optional<String> rawId = findRawItems(memberEmail);

        try {
            if (rawId.isEmpty()) {
                String json = objectMapper.writeValueAsString(List.of(item));
                jdbcTemplate.update(sql, json, memberEmail);
                return item;
            }
            List<ItemEntity> cart = convertRawItemToItemList(rawId);
            cart.add(item);
            String result = objectMapper.writeValueAsString(cart);
            jdbcTemplate.update(sql, result, memberEmail);
            return item;

        } catch (JsonProcessingException exception) {
            throw new RuntimeException("유효하지 않은 JSON 형식입니다.");
        }
    }

    private List<ItemEntity> convertRawItemToItemList(final Optional<String> rawId) throws JsonProcessingException {
        ItemEntity[] itemEntities = objectMapper.readValue(rawId.get(), ItemEntity[].class);
        return new ArrayList<>(Arrays.asList(itemEntities));
    }

    @Override
    public Optional<Map<ItemEntity, Long>> findAll(final String memberEmail) {
        Optional<String> rawItems = findRawItems(memberEmail);
        if (rawItems.isEmpty()) {
            return Optional.empty();
        }

        try {
            Map<ItemEntity, Long> collect = convertRawItemToItemList(rawItems).stream().collect(groupingBy(Function.identity(), counting()));
            return Optional.of(collect);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("유효하지 않은 JSON 형식입니다.");
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
    public void delete(final String memberEmail, final ItemEntity item) {
        String sql = "update member set cart = ? where email = ?";
        Optional<String> rawId = findRawItems(memberEmail);

        try {
            if (!rawId.isEmpty()) {
                List<ItemEntity> cart = convertRawItemToItemList(rawId);
                cart.remove(item);
                String result = objectMapper.writeValueAsString(cart);

                jdbcTemplate.update(sql, result, memberEmail);
            }
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("유효하지 않은 JSON 형식입니다.");
        }
    }
}
