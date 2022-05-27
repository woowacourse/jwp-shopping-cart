package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Member;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> memberRowMapper = (rs, rn) -> new Member(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("nickname")
    );


    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Long save(final Member member) {
        final String sql = "INSERT INTO CUSTOMER(email, password, nickname) values(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getEmail());
            preparedStatement.setString(2, member.getPassword());
            preparedStatement.setString(3, member.getNickname());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<Member> findById(final Long createdMemberId) {
        final String sql = "SELECT id, email, password, nickname FROM CUSTOMER WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, memberRowMapper, createdMemberId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long findIdByNickName(final String nickname) {
        try {
            final String query = "SELECT id FROM customer WHERE nickname = ?";
            return jdbcTemplate.queryForObject(query, Long.class, nickname.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }
}

