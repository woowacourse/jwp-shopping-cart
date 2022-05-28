package woowacourse.auth.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.domain.Member;
import woowacourse.auth.dto.MemberRequest;
import woowacourse.auth.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<Void> signUp(@RequestBody @Valid MemberRequest memberRequest) {
		Member member = memberService.signUp(memberRequest);
		URI uri = createUri(member.getId());
		return ResponseEntity.created(uri).build();
	}

	private URI createUri(Long id) {
		return ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/" + id)
			.build().toUri();
	}
}
