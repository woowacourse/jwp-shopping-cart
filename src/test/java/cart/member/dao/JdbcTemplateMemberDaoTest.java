package cart.member.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.member.entity.MemberEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@JdbcTest
class JdbcTemplateMemberDaoTest {

    @Autowired
    private DataSource dataSource;

    private SimpleJdbcInsert memberInsert;
    private JdbcTemplate jdbcTemplate;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.memberDao = new JdbcTemplateMemberDao(dataSource);
        this.memberInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("members")
                .usingGeneratedKeyColumns("id");
    }

    private int insert(MemberEntity member) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", member.getEmail());
        parameters.put("password", member.getPassword());
        return memberInsert.executeAndReturnKey(parameters).intValue();
    }

    @Test
    void selectAllTest() {
        insert(new MemberEntity(null, "yuja@naver.com", "1234"));
        insert(new MemberEntity(null, "mint@naver.com", "5678"));

        final List<MemberEntity> members = memberDao.selectAll();
        final MemberEntity member1 = members.get(0);
        final MemberEntity member2 = members.get(1);

        assertThat(members).hasSize(2);
        assertThat(member2.getId() - member1.getId()).isEqualTo(1);
    }

    @Test
    void selectAllTest_noData() {
        final List<MemberEntity> members = memberDao.selectAll();

        assertThat(members).isEmpty();
    }

    @Test
    void findByEmailTest() {
        String email = "yuja@naver.com";
        final String password = "1234";
        final int memberEntity = insert(new MemberEntity(null, email, password));

        final Optional<MemberEntity> findMember = memberDao.findByEmail(email);
        final MemberEntity member = findMember.get();

        assertThat(findMember).isPresent();
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getId()).isNotNull();
    }

    @Test
    void findByEmailTest_nonExistEmail_fail() {
        String email = "nonExist@mail.com";

        assertThatThrownBy(() -> memberDao.findByEmail(email))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
