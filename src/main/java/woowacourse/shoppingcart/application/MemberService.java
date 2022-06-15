package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.shoppingcart.dao.MemberDao;
import woowacourse.shoppingcart.domain.Member;
import woowacourse.shoppingcart.domain.value.Email;
import woowacourse.shoppingcart.domain.value.Nickname;
import woowacourse.shoppingcart.domain.value.Password;
import woowacourse.shoppingcart.dto.request.MemberCreateRequest;
import woowacourse.shoppingcart.dto.request.MemberUpdateRequest;
import woowacourse.shoppingcart.dto.request.PasswordUpdateRequest;
import woowacourse.shoppingcart.dto.response.MemberResponse;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public Long save(MemberCreateRequest memberCreateRequest) {
        validateUniqueEmail(memberCreateRequest);
        Member member = new Member(memberCreateRequest.getEmail(), memberCreateRequest.getPassword(),
                memberCreateRequest.getNickname());
        return memberDao.save(member);
    }

    private void validateUniqueEmail(MemberCreateRequest memberCreateRequest) {
        if (existsEmail(memberCreateRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 주소입니다.");
        }
    }

    public boolean existsEmail(String email) {
        String validatedEmail = new Email(email).getValue();
        return memberDao.existsEmail(validatedEmail);
    }

    public MemberResponse find(Long id) {
        Member member = memberDao.findById(id)
                .orElseThrow(() -> new AuthorizationException("회원 정보를 찾지 못했습니다."));
        return new MemberResponse(member);
    }

    @Transactional
    public void updateMember(Long id, MemberUpdateRequest memberUpdateRequest) {
        validateExists(id);
        String nickname = new Nickname(memberUpdateRequest.getNickname())
                .getValue();
        memberDao.updateNicknameById(id, nickname);
    }

    @Transactional
    public void updatePassword(Long id, PasswordUpdateRequest passwordUpdateRequest) {
        validateExists(id);
        String password = new Password(passwordUpdateRequest.getPassword())
                .getValue();
        memberDao.updatePasswordById(id, password);
    }

    @Transactional
    public void delete(Long id) {
        validateExists(id);
        memberDao.deleteById(id);
    }

    public void validateExists(Long id) {
        if (!memberDao.exists(id)) {
            throw new AuthorizationException("회원 정보를 찾지 못했습니다.");
        }
    }
}
