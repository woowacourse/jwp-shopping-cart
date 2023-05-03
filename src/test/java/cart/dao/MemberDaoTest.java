package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.member.Member;
import cart.domain.member.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setup() {
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    void 사용자_목록_반환() {
        final List<MemberEntity> members = memberDao.findAll();

        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void 사용자_존재_여부_확인() {
        final boolean isExist = memberDao.isMember(new Member("user1@email.com", "password1"));

        assertThat(isExist).isTrue();
    }
}
