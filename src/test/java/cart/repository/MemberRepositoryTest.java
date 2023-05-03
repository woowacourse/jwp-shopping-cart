package cart.repository;

import cart.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(){
        jdbcTemplate.execute("TRUNCATE TABLE members RESTART IDENTITY");
    }

    @DisplayName("회원 저장 테스트")
    @Test
    void insert(){
        // given
        final Member member = new Member("kiara", "email@email", "pw");

        // when
        long id = memberRepository.insert(member);

        // then
        Assertions.assertThat(1L).isEqualTo(id);
    }

    @DisplayName("전체 회원 조회 테스트")
    @Test
    void findAll(){
        // given
        final Member member = new Member("kiara", "email@email", "pw");

        // when
        memberRepository.insert(member);
        List<Member> allMembers = memberRepository.findAll();

        // then
        Assertions.assertThat(1).isEqualTo(allMembers.size());
    }
}
