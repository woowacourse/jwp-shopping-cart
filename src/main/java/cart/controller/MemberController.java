package cart.controller;

import cart.controller.dto.MemberSaveRequest;
import cart.service.MemberService;
import cart.service.dto.MemberSaveDto;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(@Valid @RequestBody final MemberSaveRequest memberSaveRequest) {
        this.memberService.save(MemberSaveDto.from(memberSaveRequest));
        return ResponseEntity.ok().build();
    }
}
