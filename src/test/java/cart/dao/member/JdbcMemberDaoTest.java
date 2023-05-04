package cart.dao.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.PreparedStatement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@Import(JdbcMemberDao.class)
class JdbcMemberDaoTest {

    @Autowired
    private JdbcMemberDao jdbcMemberDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("저장된 회원 목록을 모두 조회할 수 있다.")
    void findAll() {
        // given
        final int initialSize = jdbcMemberDao.findAll().size();
        // when
        String sql = "INSERT INTO member(email, password) VALUES(?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[]{"id"});
            ps.setString(1, "test@wooteco.com");
            ps.setString(2, "test");
            return ps;
        });
        final int result = jdbcMemberDao.findAll().size();
        // then
        assertThat(result).isEqualTo(initialSize + 1);
    }
}
