package cart.repository.member;

import cart.config.RepositoryTestConfig;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberJdbcRepositoryTest extends RepositoryTestConfig {
    private static final Member MEMBER = new Member("헤나", "test@test.com", "test");

    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemberJdbcRepository(jdbcTemplate);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        // when
        final MemberId saveMemberId = memberRepository.save(MEMBER);

        final Optional<Member> maybeMember = memberRepository.findByMemberId(saveMemberId);

        assertThat(maybeMember).isPresent();
        final Member member = maybeMember.get();

        // then
        assertThat(member)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Member("헤나", "test@test.com", "test"));
    }

    @DisplayName("회원을 회원 번호을 통해 조회한다.")
    @Test
    void findByMemberId() {
        // given
        final MemberId memberId = memberRepository.save(MEMBER);

        // when
        final Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);

        assertThat(maybeMember).isPresent();
        final Member member = maybeMember.get();

        // then
        assertThat(member)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Member("헤나", "test@test.com", "test"));
    }

    @DisplayName("회원을 회원 번호을 통해 조회한다.")
    @Test
    void findByEmail() {
        // given
        final MemberId memberId = memberRepository.save(MEMBER);

        // when
        final Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);

        assertThat(maybeMember).isPresent();
        final Member member = maybeMember.get();

        // then
        assertThat(member)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Member("헤나", "test@test.com", "test"));
    }

    @DisplayName("회원 전체를 조회한다.")
    @Test
    void findAll() {
        // given
        memberRepository.save(MEMBER);

        // when
        final List<Member> findAllMembers = memberRepository.findAll();

        // then
        assertThat(findAllMembers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(MEMBER);
    }

    @DisplayName("회원을 회원 번호를 통해 삭제한다.")
    @Sql(statements = "INSERT INTO members VALUES(1L, '헤나1', 'test1@test.com', 'test1')")
    @Test
    void deleteByMemberId() {
        // when
        final MemberId deleteId = memberRepository.deleteByMemberId(MemberId.from(1L));
        final Optional<Member> maybeMember = memberRepository.findByMemberId(deleteId);

        // expect
        assertThat(maybeMember).isNotPresent();
    }
}
