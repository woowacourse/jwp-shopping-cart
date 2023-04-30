package cart.persistence.dao;

import cart.persistence.entity.CartEntity;
import cart.persistence.entity.MemberCartEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(final CartEntity cartEntity) {
        final String query = "INSERT INTO cart(member_id, product_id) VALUES (?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setLong(1, cartEntity.getMemberId());
            ps.setLong(2, cartEntity.getProductId());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<CartEntity> findById(final Long id) {
        final String query = "SELECT c.id, c.member_id, c.product_id FROM cart c WHERE c.id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, (result, count) ->
                    new CartEntity(result.getLong("id"), result.getLong("member_id"),
                            result.getLong("product_id")), id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<MemberCartEntity> getProductsByMemberId(final Long memberId) {
        final String query = "SELECT c.id, c.member_id, c.product_id, p.name, p.image_url, p.price, p.category " +
                "FROM cart c JOIN product p ON c.product_id = p.id WHERE c.member_id = ?";
        return jdbcTemplate.query(query, (result, count) ->
                new MemberCartEntity(result.getLong("id"), result.getLong("member_id"),
                        result.getLong("product_id"), result.getString("name"),
                        result.getString("image_url"), result.getInt("price"),
                        result.getString("category")), memberId);
    }

    public int deleteByMemberId(final Long memberId, final Long productId) {
        final String query = "DELETE FROM cart c WHERE c.member_id = ? and c.product_id = ?";
        return jdbcTemplate.update(query, memberId, productId);
    }
}
