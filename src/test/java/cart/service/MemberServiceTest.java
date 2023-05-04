package cart.service;

import static cart.MemberFixture.TEST_MEMBER1;
import static cart.MemberFixture.TEST_MEMBER2;
import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Member;
import cart.repository.JdbcMemberRepository;
import cart.service.dto.MemberInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({MemberService.class, JdbcMemberRepository.class})
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("모든 멤버를 조회하는 기능 테스트")
    public void findAllMemberTest() {
        final List<Member> allMember = memberService.findAllMember();

        assertThat(allMember)
                .extracting(Member::getId)
                .containsExactly(TEST_MEMBER1.getId(), TEST_MEMBER2.getId());
    }

    @Nested
    @DisplayName("MemberInfo로 멤버가 실제 존재하는지 확인하는 기능 테스트")
    class isExist {

        @Test
        @DisplayName("존재하는 member의 Info를 넘기면 true를 반환한다.")
        public void found() {
            final MemberInfo memberInfo = new MemberInfo(TEST_MEMBER1.getEmail(), TEST_MEMBER1.getPassword());

            assertThat(memberService.isExist(memberInfo))
                    .isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 member의 Info를 넘기면 false를 반환한다.")
        public void notFound() {
            final MemberInfo memberInfo = new MemberInfo("backFox@wooteco.com", "backfox");

            assertThat(memberService.isExist(memberInfo))
                    .isFalse();
        }
    }
}
