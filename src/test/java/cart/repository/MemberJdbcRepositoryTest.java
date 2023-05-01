package cart.repository;

import cart.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Import(MemberJdbcRepository.class)
class MemberJdbcRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        // given
        final Member member = new Member("헤나", "test@test.com", "test");

        // when
        final long saveMemberId = memberRepository.save(member);

        // expect
        assertThat(saveMemberId).isEqualTo(1L);
    }

    @DisplayName("회원을 회원 번호을 통해 조회한다.")
    @Sql(statements = "INSERT INTO members VALUES(1L, '헤나', 'test@test.com', 'test')")
    @Test
    void findByMemberId() {
        // when
        final Optional<Member> maybeMember = memberRepository.findByMemberId(1L);

        // expect
        assertThat(maybeMember).isPresent();
        final Member member = maybeMember.get();

        assertAll(
                () -> assertThat(member.getId()).isEqualTo(1L),
                () -> assertThat(member.getName()).isEqualTo("헤나"),
                () -> assertThat(member.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(member.getPassword()).isEqualTo("test")
        );
    }

    @DisplayName("회원 전체를 조회한다.")
    @Sql(statements = {
            "INSERT INTO members VALUES(1L, '헤나1', 'test1@test.com', 'test1')",
            "INSERT INTO members VALUES(2L, '헤나2', 'test2@test.com', 'test2')",
            "INSERT INTO members VALUES(3L, '헤나3', 'test3@test.com', 'test3')"
    })
    @Test
    void findAllMembers() {
        // when
        final List<Member> findAllMembers = memberRepository.findAll();

        // expect
        assertThat(findAllMembers).hasSize(3);
    }

    @DisplayName("회원을 회원 번호를 통해 삭제한다.")
    @Sql(statements = "INSERT INTO members VALUES(1L, '헤나1', 'test1@test.com', 'test1')")
    @Test
    void deleteByMemberId() {
        // when
        final long deleteId = memberRepository.deleteByMemberId(1L);
        final Optional<Member> maybeMember = memberRepository.findByMemberId(deleteId);

        // expect
        assertThat(maybeMember).isNotPresent();
    }
}
