package cart.repository.member;

import cart.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberDbRepositoryTest {

    private MemberDbRepository memberDbRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    @BeforeEach
    void init() {
        memberDbRepository = new MemberDbRepository(jdbcTemplate);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("member");
        jdbcTemplate.execute("INSERT INTO member(id, email, password) VALUES (1, 'test1@test.com', '!!abc123'), (2, 'test2@test.com', '!!abc123')");
    }

    @Test
    @DisplayName("모든 사용자를 찾는다.")
    void returns_all_members() {
        // when
        List<Member> members = memberDbRepository.findAll();

        // then
        assertAll(
                () -> assertThat(members.size()).isEqualTo(2),
                () -> assertThat(members.get(0).getEmail()).isEqualTo("test1@test.com")
        );
    }

    @Test
    @DisplayName("이메일 기준으로 사용자를 찾는다.")
    void returns_member_by_email() {
        // given
        String email = "test1@test.com";

        // when
        Optional<Member> member = memberDbRepository.findByEmail(email);

        // then
        assertThat(member.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("사용자를 저장한다.")
    void save_member() {
        // given
        Member member = Member.from("test3@test.com", "!!abc123");

        // when
        memberDbRepository.save(member);

        // then
        List<Member> members = memberDbRepository.findAll();
        assertAll(
                () -> assertThat(members.size()).isEqualTo(3),
                () -> assertThat(members.get(2).getEmail()).isEqualTo("test3@test.com")
        );
    }
}
