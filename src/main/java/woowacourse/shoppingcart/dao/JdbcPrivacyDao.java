package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
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

    public JdbcPrivacyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(int customerId, Privacy privacy) {
        String sql = "INSERT INTO privacy (customer_id, name, gender, birth_day, contact) VALUES(?, ?, ?, ? ,?)";
        jdbcTemplate.update(sql, customerId, privacy.getName().getValue(), privacy.getGender().getValue(),
                privacy.getBirthday().getValue(), privacy.getContact().getValue());
    }

    @Override
    public PrivacyEntity findById(int customerId) {
        String sql = "SELECT customer_id, name, gender, birth_day, contact FROM privacy WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, PRIVACY_ENTITY_ROW_MAPPER, customerId);
    }

    @Override
    public void update(int customerId, Privacy privacy) {
        String sql = "UPDATE privacy SET name = ?, gender = ?, birth_day = ?, contact = ? WHERE customer_id = ?";
        jdbcTemplate.update(sql, privacy.getName().getValue(), privacy.getGender().getValue(),
                privacy.getBirthday().getValue(),
                privacy.getContact().getValue(), customerId);
    }

    @Override
    public void delete(int customerId) {
        String sql = "DELETE FROM privacy WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }
}
