package cart.dao.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@JdbcTest
@Import(JdbcMemberDao.class)
class JdbcMemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new JdbcMemberDao(jdbcTemplate);
    }

    private void insertMember() {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", "test@wooteco.com");
        parameters.put("password", "test");
        simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Test
    @DisplayName("저장된 회원 목록을 모두 조회할 수 있다.")
    void findAll() {
        // given
        final int initialSize = memberDao.findAll().size();
        // when
        insertMember();
        final int result = memberDao.findAll().size();
        // then
        assertThat(result).isEqualTo(initialSize + 1);
    }
}
