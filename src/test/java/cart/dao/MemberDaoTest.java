package cart.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import cart.entity.MemberEntity;
import cart.entity.MemberEntity.MemberEntityBuilder;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/test.sql")
class MemberDaoTest {

    public static final String EMAIL = "test@email.com";
    public static final String PASSWORD = "12345678";
    public static final MemberEntityBuilder MEMBER_ENTITY = MemberEntity.builder()
            .id(1L)
            .email(EMAIL)
            .password(PASSWORD);

    @Autowired
    JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("전체 사용자를 반환한다.")
    void findAll_success() {
        // when
        List<MemberEntity> all = memberDao.findAll();

        // then
        assertAll(
                () -> assertThat(all).hasSize(1),
                () -> assertThat(all.get(0))
                        .usingRecursiveComparison()
                        .comparingOnlyFields("email")
                        .comparingOnlyFields("password")
                        .isEqualTo(MEMBER_ENTITY)
        );
    }

    @Test
    @DisplayName("email, password 정보로 사용자를 조회한다.")
    void findByCredentials_success() {
        // when
        MemberEntity member = memberDao.findByCredentials(EMAIL, PASSWORD);

        // then
        assertThat(member)
                .usingRecursiveComparison()
                .comparingOnlyFields("id")
                .comparingOnlyFields("email")
                .comparingOnlyFields("password")
                .isEqualTo(MEMBER_ENTITY);
    }
}
