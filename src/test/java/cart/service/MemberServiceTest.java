package cart.service;

import cart.dto.MemberRequestDto;
import cart.dto.MemberResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("사용자를 추가한다.")
    void addMember() {
        MemberRequestDto memberRequestDto = new MemberRequestDto("eastsea@eastsea", "eastsea");

        memberService.addMember(memberRequestDto);
        MemberResponseDto memberResponseDto = memberService.findMembers().get(0);

        assertThat(memberResponseDto.getEmail()).isEqualTo(memberRequestDto.getEmail());
    }

    @Test
    @DisplayName("모든 사용자를 찾는다.")
    void findMembers() {
        MemberRequestDto memberRequestDto1 = new MemberRequestDto("eastsea@eastsea", "eastsea");
        MemberRequestDto memberRequestDto2 = new MemberRequestDto("westsea@westsea", "westsea");
        memberService.addMember(memberRequestDto1);
        memberService.addMember(memberRequestDto2);

        assertThat(memberService.findMembers().size()).isEqualTo(2);
    }
}
