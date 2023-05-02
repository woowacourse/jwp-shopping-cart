package cart.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.AuthInfo;
import cart.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import java.util.Optional;

@JdbcTest
@Sql({"/schema.sql", "/data.sql"})
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    void 모든_회원_조회_성공() {
        final var members = memberDao.findAll();

        assertAll(
                () -> assertThat(members.size()).isEqualTo(2),
                () -> assertThat(members.get(0)).isInstanceOf(MemberEntity.class)
        );
    }

    @Test
    void AuthInfo로_존재하지_않는_회원을_찾는_경우_Optional_empty가_반환된다() {
        final AuthInfo emptyAuthInfo = new AuthInfo("emptyEmail@email.com", "emptyPassword");

        final Optional<MemberEntity> result = memberDao.findByAuthInfo(emptyAuthInfo);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void AuthInfo로_존재하지_않는_회원을_찾는_경우_Optional에_감싸져서_반환된다() {
        final AuthInfo userAInfo = new AuthInfo("userA@woowahan.com", "passwordA");

        final Optional<MemberEntity> result = memberDao.findByAuthInfo(userAInfo);
        assertThat(result.isPresent()).isTrue();

        final MemberEntity member = result.get();
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(1),
                () -> assertThat(member.getClass()).isEqualTo(MemberEntity.class)
        );
    }
}
