package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.PrivacyEntity;

@Repository
public class JdbcPrivacyDao implements PrivacyDao {
    private static final String CUSTOMER_ID_COLUMN = "customer_id";
    private static final String NAME_COLUMN = "name";
    private static final String GENDER_COLUMN = "gender";
    private static final String BIRTH_DAY_COLUMN = "birth_day";
    private static final String CONTACT_COLUMN = "contact";

    private static final RowMapper<PrivacyEntity> PRIVACY_ENTITY_ROW_MAPPER = (rs, rowNum) -> new PrivacyEntity(
            rs.getLong(CUSTOMER_ID_COLUMN),
            rs.getString(NAME_COLUMN),
            rs.getString(GENDER_COLUMN),
            rs.getTimestamp(BIRTH_DAY_COLUMN).toLocalDateTime().toLocalDate(),
            rs.getString(CONTACT_COLUMN)
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcPrivacyDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(long customerId, PrivacyEntity privacyEntity) {
        String sql = "INSERT INTO privacy (customer_id, name, gender, birth_day, contact) VALUES(:customer_id, :name, :gender, :birth_day, :contact)";

        Map<String, Object> params = new HashMap<>();
        params.put(CUSTOMER_ID_COLUMN, customerId);
        params.put(NAME_COLUMN, privacyEntity.getName());
        params.put(GENDER_COLUMN, privacyEntity.getGender());
        params.put(BIRTH_DAY_COLUMN, privacyEntity.getBirthday());
        params.put(CONTACT_COLUMN, privacyEntity.getContact());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<PrivacyEntity> findById(long customerId) {
        try {
            String sql = "SELECT customer_id, name, gender, birth_day, contact FROM privacy WHERE customer_id = ?";
            return Optional.of(jdbcTemplate.queryForObject(sql, PRIVACY_ENTITY_ROW_MAPPER, customerId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(long customerId, PrivacyEntity privacyEntity) {
        String sql = "UPDATE privacy SET name = :name, gender = :gender, birth_day = :birth_day, contact = :contact WHERE customer_id = :customer_id";

        Map<String, Object> params = new HashMap<>();
        params.put(NAME_COLUMN, privacyEntity.getName());
        params.put(GENDER_COLUMN, privacyEntity.getGender());
        params.put(BIRTH_DAY_COLUMN, privacyEntity.getBirthday());
        params.put(CONTACT_COLUMN, privacyEntity.getContact());
        params.put(CUSTOMER_ID_COLUMN, customerId);

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(long customerId) {
        String sql = "DELETE FROM privacy WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }
}
