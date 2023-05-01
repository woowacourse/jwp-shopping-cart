package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Member;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;


@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberDaoTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(dataSource);
        jdbcTemplate.update("delete from member");
    }

    @DisplayName("저장된 모든 유저를 가져온다.")
    @Test
    void find_all() {
        //given
        saveUser();
        saveUser();
        //when
        List<Member> actual = memberDao.findAll();
        //then
        assertThat(actual.size()).isEqualTo(2);
    }


    private void saveUser() {
        String sql = "insert into member(email, password) values (?,?)";
        jdbcTemplate.update(sql, "email", "password");
    }
}
