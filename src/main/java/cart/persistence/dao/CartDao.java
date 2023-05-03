package cart.persistence.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CartDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByCartId(Long cartId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE cart_id = :cart_id";
        var parameterSource = new MapSqlParameterSource("cart_id", cartId);

        Integer count = jdbcTemplate.queryForObject(sql, parameterSource, Integer.class);
        return count > 0;
    }

    public Optional<Long> findCartIdByMemberId(Long memberId) {
        String sql = "SELECT cart_id FROM cart WHERE member_id = :member_id";

        var parameterSource = new MapSqlParameterSource("member_id", memberId);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameterSource, Long.class));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Long createCart(Long memberId) {
        String sql = "INSERT INTO cart(member_id) VALUES(:member_id)";

        var parameterSource = new MapSqlParameterSource("member_id", memberId);

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameterSource, keyHolder);
        return (Long) keyHolder.getKey().longValue();
    }
}
