package cart.controller;

import cart.dao.member.MemberDao;
import cart.dto.ResultResponse;
import cart.dto.SuccessCode;
import cart.dto.member.MemberRequest;
import cart.entity.MemberEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberDao memberDao;

    public MemberController(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @PostMapping
    public ResultResponse add(@Valid @RequestBody MemberRequest memberRequest) {
        MemberEntity item = new MemberEntity(memberRequest.getEmail(),
                memberRequest.getName(),
                memberRequest.getPhoneNumber(),
                memberRequest.getPassword());

        MemberEntity saveItem = memberDao.save(item);

        return new ResultResponse(SuccessCode.CREATE_MEMBER, saveItem);
    }

    @PutMapping
    public ResultResponse edit(@Valid @RequestBody MemberRequest memberRequest) {
        MemberEntity item = new MemberEntity(memberRequest.getEmail(),
                memberRequest.getName(),
                memberRequest.getPhoneNumber(),
                memberRequest.getPassword());

        memberDao.update(item);
        return new ResultResponse(SuccessCode.UPDATE_MEMBER, item);
    }

    @DeleteMapping("/{email}")
    public ResultResponse delete(@PathVariable String email) {
        memberDao.delete(email);
        return new ResultResponse(SuccessCode.DELETE_MEMBER, email);
    }
}
