package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/scheme.sql")
class H2MemberDaoTest {

    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        memberDao = new H2MemberDao(jdbcTemplate);
    }

    @Test
    void findAll() {
        jdbcTemplate.update("INSERT INTO member (id, email, password) values (30, 'email5@email', 'password5')");
        jdbcTemplate.update("INSERT INTO member (id, email, password) values (40, 'email54@email', 'password54')");

        final List<MemberEntity> members = memberDao.findAll();
        assertThat(members).hasSize(2);
    }
}
