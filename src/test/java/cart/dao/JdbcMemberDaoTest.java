package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.member.Member;
import java.util.Optional;
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
    @DisplayName("Member 삽입")
    void insert() {
        Long id = jdbcMemberDao.insert(new Member("h@h.com", "password1", "홍길동"));
        assertThat(id).isPositive();
    }

    @Test
    @DisplayName("Member 조회")
    void findAll() {
        jdbcMemberDao.insert(new Member("m@h.com", "password1", "무민"));
        jdbcMemberDao.insert(new Member("t@h.com", "password2", "누누"));
        jdbcMemberDao.insert(new Member("n@h.com", "password3", "테오"));

        assertThat(jdbcMemberDao.findAll()).extracting("name")
                .contains("무민", "누누", "테오");
    }

    @Test
    @DisplayName("이메일과 비밀번호로 Member 조회")
    void findByEmailAndPassword() {
        String email = "test@test.com";
        String password = "testPassword";
        jdbcMemberDao.insert(new Member(email, password, "홍길동"));

        Optional<Member> member = jdbcMemberDao.findByEmailAndPassword(email, password);
        assertAll(
                () -> assertThat(member).isPresent(),
                () -> assertThat(member.get()).extracting("name").isEqualTo("홍길동")
        );
    }

    @Test
    @DisplayName("이메일과 비밀번호가 올바르지 않을시 empty 반환")
    void findByEmailAndPassword_empty() {
        // when
        Optional<Member> member = jdbcMemberDao.findByEmailAndPassword("test@test.com", "testPassword");

        // then
        assertThat(member).isEmpty();
    }
}
