package woowacourse.auth.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.user.Email;
import woowacourse.auth.domain.user.Id;
import woowacourse.auth.entity.UserEntity;

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
    public int save(UserEntity userEntity) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", userEntity.getEmail());
        params.put("password", userEntity.getPassword());
        params.put("profile_image_url", userEntity.getProfileImageUrl());
        params.put("terms", userEntity.isTerms());

        return jdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public UserEntity findById(int id) {
        String sql = "SELECT id, email, password, profile_image_url, terms FROM CUSTOMER WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        new UserEntity(
                                rs.getInt("id"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("profile_image_url"),
                                rs.getBoolean("terms")
                        ),
                id
        );
    }

    @Override
    public UserEntity findByEmail(Email email) {
        return null;
    }


    @Override
    public void update(UserEntity userEntity) {

    }

    @Override
    public void delete(Id id) {

    }
}
