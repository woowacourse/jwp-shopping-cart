package cart.controller;

import cart.dto.MemberDto;
import cart.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    void memberSave(@RequestBody MemberDto memberDto) {
        memberService.createMember(memberDto);
    }

    @GetMapping("/members")
    List<MemberDto> memberList() {
       return memberService.findAll();
    }
}
