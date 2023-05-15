package cart.dao.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/schema.sql")
@Sql("/data.sql")
class MemberDaoTest {

    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("findAll() : 모든 member를 가져올 수 있다.")
    void test_findAll() {
        // when
        List<MemberEntity> memberEntities = memberDao.findAll();

        // expected
        assertAll(
                () -> assertThat(memberEntities).hasSize(2),
                () -> assertThat(memberEntities.get(0).getEmail()).isEqualTo("email@email.com")
        );
    }

    @Test
    @DisplayName("findByEmailAndPassword() : 이메일과 비밀번호로 member를 가져올 수 있다.")
    void test_findByEmailAndPassword() {
        // given
        final String email = "email@email.com";
        final String password = "asdf1234";

        // when
        Optional<MemberEntity> memberEntity = memberDao.findByEmailAndPassword(email, password);

        // expected
        assertThat(memberEntity.get()).isNotNull();
    }

    @Test
    @DisplayName("findByEmailAndPassword() : member가 존재하지 않는다면 리턴 값이 null일 수 있다.")
    void test_findByEmailAndPassword_NoSuchElementException() {
        // given
        final String email = "email3@email.com";
        final String password = "asdf1234";

        // when
        Optional<MemberEntity> memberEntity = memberDao.findByEmailAndPassword(email, password);

        // expected
        assertThatThrownBy(memberEntity::get)
                .isInstanceOf(NoSuchElementException.class);
    }
}
