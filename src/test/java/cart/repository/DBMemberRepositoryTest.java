package cart.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cart.entity.Member;
import cart.entity.MemberRepository;

@JdbcTest
class DBMemberRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private MemberRepository memberRepository;
    private Long id1;
    private Long id2;

    @BeforeEach
    void setUp() {
        memberRepository = new DBMemberRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE cart IF EXISTS");
        jdbcTemplate.execute("DROP TABLE member IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE member ("
            + "id          BIGINT          NOT NULL    AUTO_INCREMENT,"
            + "email       VARCHAR(255)    NOT NULL,"
            + "password    VARCHAR(255)    NOT NULL,"
            + "    PRIMARY KEY (id))");

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("member")
            .usingGeneratedKeyColumns("id");

        Member member1 = Member.of(null, "email1@email.com", "password1");
        Member member2 = Member.of(null, "email2@email.com", "password2");

        Map<String, Object> map1 = Map.of(
            "email", member1.getEmail(),
            "password", member1.getPassword()
        );
        Map<String, Object> map2 = Map.of(
            "email", member2.getEmail(),
            "password", member2.getPassword()
        );

        this.id1 = simpleJdbcInsert.executeAndReturnKey(map1).longValue();
        this.id2 = simpleJdbcInsert.executeAndReturnKey(map2).longValue();
    }

    @Test
    @DisplayName("사용자 정보를 DB에 저장한다.")
    void save() {
        Member member = Member.of(null, "email3@email.com", "password3");
        Long id3 = memberRepository.save(member);

        String sql = "SELECT * FROM member";
        assertThat(jdbcTemplate.query(sql, (rs, rn) -> rs).size()).isEqualTo(3);

    }

    @Test
    @DisplayName("Email과 Password로 사용자 정보를 조회한다.")
    void findByEmailAndPassword() {
        Member member = memberRepository.findByEmailAndPassword("email1@email.com", "password1");
        assertAll(
            () -> assertThat(member.getEmail()).isEqualTo("email1@email.com"),
            () -> assertThat(member.getPassword()).isEqualTo("password1")
        );

    }

    @Test
    @DisplayName("모든 사용자 정보를 조회한다.")
    void findAll() {
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("ID에 해당하는 사용자 정보를 수정한다.")
    void updateById() {
        Member member1 = Member.of(id2, "email4@email.com", "password4");
        memberRepository.updateById(member1, id2);

        String sql = "SELECT id, email, password FROM member WHERE id = ?";
        Member member2 = jdbcTemplate.queryForObject(sql,
            (rs, rn) -> Member.of(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")),
            id2);

        assertAll(
            () -> assertThat(member2.getEmail()).isEqualTo(member1.getEmail()),
            () -> assertThat(member2.getPassword()).isEqualTo(member1.getPassword())
        );
    }

    @Test
    @DisplayName("ID에 해당하는 사용자 정보를 삭제한다.")
    void deleteById() {
        memberRepository.deleteById(id2);

        String sql = "SELECT * FROM member";
        assertThat(jdbcTemplate.query(sql, (rs, rn) -> rs).size()).isEqualTo(1);
    }
}
