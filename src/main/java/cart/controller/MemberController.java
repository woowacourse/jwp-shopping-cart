package cart.controller;

import cart.dto.member.MemberDto;
import cart.dto.member.MemberRequestDto;
import cart.dto.member.MemberResponseDto;
import cart.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MemberResponseDto> joinMember(@RequestBody @Valid MemberRequestDto requestDto) {
        MemberDto dto = memberService.join(requestDto);
        MemberResponseDto response = MemberResponseDto.fromDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/members/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable("id") Long id,
                                                          @RequestBody @Valid MemberRequestDto requestDto) {
        MemberDto dto = memberService.updateById(requestDto, id);
        MemberResponseDto response = MemberResponseDto.fromDto(dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
