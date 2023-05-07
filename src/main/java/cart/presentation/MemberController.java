package cart.presentation;

import cart.business.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService productService) {
        this.memberService = productService;
    }

    @DeleteMapping(path = "/settings/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(memberService.delete(id));
    }
}
