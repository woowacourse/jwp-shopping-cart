package cart.controller;

import cart.dao.MemberDao;
import cart.dto.ResultResponse;
import cart.dto.SuccessCode;
import cart.dto.member.MemberUpdateRequest;
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

    @PutMapping
    public ResultResponse edit(@Valid @RequestBody MemberUpdateRequest itemUpdateRequest) {
        MemberEntity item = new MemberEntity(itemUpdateRequest.getEmail(),
                itemUpdateRequest.getName(),
                itemUpdateRequest.getPhoneNumber(),
                itemUpdateRequest.getPassword());

        memberDao.update(item);
        return new ResultResponse(SuccessCode.UPDATE_ITEM, item);
    }

    @DeleteMapping("/{email}")
    public ResultResponse delete(@PathVariable String email) {
        memberDao.delete(email);
        return new ResultResponse(SuccessCode.DELETE_ITEM, email);
    }
}
