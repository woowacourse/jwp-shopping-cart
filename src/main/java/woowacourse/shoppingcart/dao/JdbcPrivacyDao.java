package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.PrivacyEntity;

@Repository
public class JdbcPrivacyDao implements PrivacyDao {
    private static final RowMapper<PrivacyEntity> PRIVACY_ENTITY_ROW_MAPPER = (rs, rowNum) -> new PrivacyEntity(
            rs.getInt("customer_id"),
            rs.getString("name"),
            rs.getString("gender"),
            rs.getTimestamp("birth_day").toLocalDateTime().toLocalDate(),
            rs.getString("contact")
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcPrivacyDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(PrivacyEntity privacyEntity) {
        String sql = "INSERT INTO privacy (customer_id, name, gender, birth_day, contact) VALUES(:customerId, :name, :gender, :birthday ,:contact)";

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(privacyEntity);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    @Override
    public PrivacyEntity findById(int id) {
        String sql = "SELECT customer_id, name, gender, birth_day, contact FROM privacy WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, PRIVACY_ENTITY_ROW_MAPPER, id);
    }

    @Override
    public void update(PrivacyEntity privacyEntity) {
        String sql = "UPDATE privacy SET name = :name, gender = :gender, birth_day = :birthday, contact = :contact WHERE customer_id = :customerId";

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(privacyEntity);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    @Override
    public void delete(int customerId) {
        String sql = "DELETE FROM privacy WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }
}
