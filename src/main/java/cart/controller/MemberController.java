package cart.controller;

import cart.dto.MemberRequest;
import cart.dto.MemberResponse;
import cart.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll(ModelAndView modelAndView) {
        return ResponseEntity.ok(memberService.findAll());
    }

    @PostMapping
    public long save(@RequestBody MemberRequest memberRequest) {
        return memberService.save(memberRequest);
    }
}
