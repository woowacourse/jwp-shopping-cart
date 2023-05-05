package cart.controller;

import cart.dto.MemberRequest;
import cart.dto.MemberResponse;
import cart.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid MemberRequest memberRequest) {
        long id = memberService.save(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll(ModelAndView modelAndView) {
        return ResponseEntity.ok(memberService.findAll());
    }
}
