package cart.dao;

import cart.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class MemberDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @Test
    @Sql("/truncate.sql")
    void save() {
        //when
        Member member = new Member("name", "password");
        memberDao.save(member);

        //then
        List<Member> members = memberDao.findAll();
        assertThat(members.get(0))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(member);
    }
}
