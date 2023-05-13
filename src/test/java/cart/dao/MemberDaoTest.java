package cart.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Member;
import cart.entity.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class MemberDaoTest {

    public static final String EMAIL = "test@email.com";
    public static final String PASSWORD = "12345678";
    private final Member MEMBER = Member.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();

    private long memberId;

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
        // given
        memberId = memberDao.save(MEMBER).getId();

        // when
        List<MemberEntity> all = memberDao.findAll();

        // then
        assertAll(
                () -> assertThat(all).hasSize(1),
                () -> assertThat(all.get(0))
                        .usingRecursiveComparison()
                        .comparingOnlyFields("email")
                        .comparingOnlyFields("password")
                        .isEqualTo(MemberEntity.builder()
                                .id(memberId)
                                .email(EMAIL)
                                .password(PASSWORD)
                                .build()));
    }

    @Test
    @DisplayName("email, password 정보로 사용자를 조회한다.")
    void findByCredentials_success() {
        // given
        memberId = memberDao.save(MEMBER).getId();

        // when
        MemberEntity member = memberDao.findByCredentials(EMAIL, PASSWORD);

        // then
        assertThat(member)
                .usingRecursiveComparison()
                .comparingOnlyFields("id")
                .comparingOnlyFields("email")
                .comparingOnlyFields("password")
                .isEqualTo(MemberEntity.builder()
                        .id(memberId)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build());
    }
}
