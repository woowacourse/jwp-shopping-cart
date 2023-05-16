package cart.dao;

import cart.domain.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Transactional
class MemberDaoTest {

    MemberDao memberDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("사용자 db에 등록된 모든 사용자 정보를 찾는다")
    void findAll() {
        assertThat(memberDao.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("존재하지 않는 email로 회원을 조회할 경우 Optional Empty가 반환된다.")
    void findMemberWithNonExistEmail() {
        Optional<MemberEntity> nonExistEmail = memberDao.findBy("NonExistEmail");

        assertTrue(nonExistEmail.isEmpty());
    }

}