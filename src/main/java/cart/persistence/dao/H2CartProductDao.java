package cart.persistence.dao;

import cart.persistence.entity.CartProductEntity;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartProductDao implements CartProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<CartProductEntity> rowMapper = (resultSet, rowNumber) -> CartProductEntity.create(
            resultSet.getLong("id"),
            resultSet.getLong("member_id"),
            resultSet.getLong("product_id")
    );

    public H2CartProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long save(CartProductEntity cartProductEntityToSave) {

        String sql = "SELECT id, member_id, product_id FROM cart_product WHERE member_id = ? AND product_id = ?";
        try {
            CartProductEntity cartProductEntity = this.jdbcTemplate.queryForObject(
                    sql,
                    rowMapper,
                    cartProductEntityToSave.getMemberId(),
                    cartProductEntityToSave.getProductId()
            );
            return cartProductEntity.getId();
        } catch (EmptyResultDataAccessException ignored) {
            final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartProductEntityToSave);
            return this.simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        }
    }

    @Override
    public List<CartProductEntity> findAllByUserId() {
        return null;
    }
}
