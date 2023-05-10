package cart.member.service;

import cart.member.dto.MemberResponse;
import cart.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/testdata.sql")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void 전체_멤버_조회() {
        List<MemberResponse> memberResponses = memberService.showAllMembers();
        Assertions.assertThat(memberResponses.size()).isSameAs(2);
    }

    @ParameterizedTest
    @CsvSource(value = {"rg970604@naver.com:password", "yimsh66@naver.com:password"}, delimiter = ':')
    void 이메일과_비밀번호로_개별_멤버_조회(String email, String password) {
        Member member = memberService.selectMemberByEmailAndPassword(email, password);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isEqualTo(password);
    }

}