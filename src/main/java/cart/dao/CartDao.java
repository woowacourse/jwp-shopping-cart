package cart.dao;

import cart.dto.entity.CartEntity;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<CartEntity> findByMemberId(int memberId) {
        final String sql = "SELECT * FROM cart WHERE member_id = ?";
        BeanPropertyRowMapper<CartEntity> mapper = BeanPropertyRowMapper.newInstance(CartEntity.class);
        return jdbcTemplate.query(sql, mapper, memberId);
    }

    public int save(CartEntity cartEntity) {
        GeneratedKeyHolder keyholder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO cart (member_id, product_id) VALUES (:member_id, :product_id)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(cartEntity);
        namedParameterJdbcTemplate.update(sql, namedParameters, keyholder, new String[]{"id"});
        return keyholder.getKey().intValue();
    }

    public int delete(int id) {
        final String sql = "DELETE FROM cart WHERE id=:id";
        MapSqlParameterSource mapSqlParameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.update(sql, mapSqlParameters);
    }
}
