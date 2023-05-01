package cart.service.member;

import cart.dto.member.MembersResponseDto;
import cart.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("INSERT INTO member(id, email, password) VALUES (1, 'test1@test.com', '!!abc123'), (2, 'test2@test.com', '!!abc123')");
    }

    @Test
    @DisplayName("모든 사용자를 찾는다.")
    void find_all_members() {
        // when
        MembersResponseDto result = memberService.findAll();

        // then
        assertAll(
                () -> assertThat(result.getMembers().size()).isEqualTo(2),
                () -> assertThat(result.getMembers().get(0).getEmail()).isEqualTo("test1@test.com")
        );
    }
}
