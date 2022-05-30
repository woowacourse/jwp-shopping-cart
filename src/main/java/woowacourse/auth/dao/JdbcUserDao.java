package woowacourse.auth.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.user.Email;
import woowacourse.auth.domain.user.Id;
import woowacourse.auth.domain.user.User;

@Repository
public class JdbcUserDao implements UserDao {
    private static final String TABLE_NAME = "CUSTOMER";
    private static final String ID_COLUMN = "id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcUserDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public Id save(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", user.getEmail().getValue());
        params.put("password", user.getPassword().getValue());
        params.put("profile_image_url", user.getProfileImageUrl().getValue());
        params.put("terms", user.isTerms());

        int id = jdbcInsert.executeAndReturnKey(params).intValue();
        return new Id(id);
    }

    @Override
    public User findById(Id id) {
        return null;
    }

    @Override
    public User findByEmail(Email email) {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(Id id) {

    }
}
