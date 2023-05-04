package cart.dao;

import cart.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void beforeEach() {
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @DisplayName("resources 내의 sql 파일로 인해서 데이터베이스에 초기화된 Member 를 조회")
    void selectAll() {
        List<Member> members = memberDao.selectAll();

        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("resources 내의 sql 파일로 인해서 데이터베이스에 초기화된 Member 데이터 중 이메일을 통해서 조회")
    void findByEmail() {
        Member member = memberDao.findByEmail("kpeel5839@a.com");

        assertThat(member.getEmail()).isEqualTo("kpeel5839@a.com");
        assertThat(member.getPassword()).isEqualTo("password1!");
    }

}
