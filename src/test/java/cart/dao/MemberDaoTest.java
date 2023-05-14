package cart.dao;

import cart.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
class MemberDaoTest {

    @Autowired
    DataSource dataSource;

    MemberDao memberDao;

    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setting() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("현재 등록되어 있는 모든 유저의 정보를 가져온다")
    @Test
    void finaAll() {
        //given,when
        List<MemberEntity> customers = memberDao.findAll();

        //then
        assertThat(customers).hasSize(2);
    }

    @DisplayName("해당 이메일, 비밀 번호를 가진 유저를 찾는다.")
    @Test
    void findBy_EmailAndPassword() {
        //given
        final String email = "pooh@naver.com";
        final String password = "123";

        //when
        final MemberEntity findMEmber = memberDao.findBy(email, password).get();

        //then
        assertThat(findMEmber).isNotNull();
    }

    @DisplayName("해당 이메일, 비밀 번호를 가진 유저가 없을 경우 Optional null 을 반환한다.")
    @Test
    void findBy_notExitingMember() {
        //given
        final String email = "notExiting";
        final String password = "none";

        //when
        final Optional<MemberEntity> findMember = memberDao.findBy(email, password);

        //then
        assertTrue(findMember.isEmpty());
    }
}
