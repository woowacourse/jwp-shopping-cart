package cart.repository;

import cart.dto.UserAuthenticationDto;
import cart.exception.AuthorizationException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;

public class UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Integer findIdByEmailAndPassword(final UserAuthenticationDto userAuthenticationDto) {
        final String sql = "select id from users where email = :email and password = :password";
        final SqlParameterSource params = new BeanPropertySqlParameterSource(userAuthenticationDto);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        } catch (DataAccessException e) {
            throw new AuthorizationException("잘못된 인증 정보입니다.");
        }
    }
}
