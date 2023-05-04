package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@JdbcTest
class AuthMemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert memberJdbcInsert;

    private AuthMemberDao authMemberDao;

    @BeforeEach
    void setUp() {
        authMemberDao = new AuthMemberDao(jdbcTemplate);
        memberJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingColumns("email", "password")
                .usingGeneratedKeyColumns("id");
    }

    @Test
    void 이메일을_입력받아_동일한_이메일을_가진_사용자를_조회한다() {
        // given
        final Member member = new Member("pizza1@pizza.com", "password");
        final BeanPropertySqlParameterSource parameter = new BeanPropertySqlParameterSource(member);
        memberJdbcInsert.executeAndReturnKey(parameter).longValue();

        // when
        final Member findMember = authMemberDao.findByEmail("pizza1@pizza.com").get();

        // then
        assertAll(
                () -> assertThat(findMember.getEmail()).isEqualTo("pizza1@pizza.com"),
                () -> assertThat(findMember.getPassword()).isEqualTo("password")
        );
    }

    @Test
    void 동일한_이메일을_가진_사용자가_없다면_Optional_Empty를_반환한다() {
        // expect
        assertThat(authMemberDao.findByEmail("pizza1@pizza.com")).isNotPresent();
    }
}
