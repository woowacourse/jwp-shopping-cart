package cart.controller;

import cart.dto.MemberDto;
import cart.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    void saveMember(@RequestBody @Valid MemberDto memberDto) {
        memberService.createMember(memberDto);
    }

    @GetMapping("/members")
    List<MemberDto> findAllMember() {
       return memberService.findAll();
    }
}
