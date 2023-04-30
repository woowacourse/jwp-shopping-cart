package cart.dao;

import cart.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("모든 멤버를 조회한다")
    void findAll() {
        final List<MemberEntity> result = memberDao.findAll();

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(result.get(0).getEmail()).isEqualTo("a@a.com"),
                () -> assertThat(result.get(0).getPassword()).isEqualTo("password1"),
                () -> assertThat(result.get(1).getEmail()).isEqualTo("b@b.com"),
                () -> assertThat(result.get(1).getPassword()).isEqualTo("password2")
        );
    }

    @Nested
    @DisplayName("이메일, 비밀번호를 통한 멤버 조회 시")
    class FindByEmailAndPassword {

        @Test
        @DisplayName("일치하는 멤버가 존재한다면 멤버를 조회한다.")
        void findByEmailAndPassword() {
            final String email = "a@a.com";
            final String password = "password1";

            final Optional<MemberEntity> result = memberDao.findByEmailAndPassword(email, password);

            assertThat(result).isNotEmpty();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "a@a.com,password2",
                "a@b.com,password1",
        })
        @DisplayName("일치하는 멤버가 존재하지 않으면 멤버를 조회하지 못한다.")
        void findByEmailAndPasswordWithNull(final String email, final String password) {
            final Optional<MemberEntity> result = memberDao.findByEmailAndPassword(email, password);

            assertThat(result).isEmpty();
        }
    }
}
