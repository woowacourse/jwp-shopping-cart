package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@JdbcTest
class JdbcMemberDaoTest {

    private final RowMapper<Member> memberRowMapper = (resultSet, rowNum) ->
            new Member(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("name")
            );

    private JdbcMemberDao jdbcMemberDao;

    private JdbcMemberDaoTest(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcMemberDao = new JdbcMemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("Member 조회 테스트")
    void findAllTest() {
        assertThat(jdbcMemberDao.findAll()).extracting("name")
                .containsExactly("이오", "애쉬");
    }
}
