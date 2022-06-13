package woowacourse.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.dto.MemberNameUpdateRequest;
import woowacourse.member.dto.MemberPasswordUpdateRequest;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.dto.MemberResponse;
import woowacourse.member.exception.DuplicateMemberEmailException;
import woowacourse.member.exception.NoMemberException;
import woowacourse.member.infrastructure.PasswordEncoder;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Long save(final MemberRegisterRequest memberRegisterRequest) {
        validateDuplicateEmail(memberRegisterRequest.getEmail());
        Member member = Member.createMemberWithPasswordEncode(memberRegisterRequest.getEmail(),
                memberRegisterRequest.getPassword(),
                memberRegisterRequest.getName(),
                passwordEncoder);
        return memberDao.save(member);
    }

    public void validateDuplicateEmail(final String email) {
        if (memberDao.isEmailExist(email)) {
            throw new DuplicateMemberEmailException();
        }
    }

    public Member login(final TokenRequest tokenRequest) {
        Member member = memberDao.findByEmail(tokenRequest.getEmail())
                .orElseThrow(NoMemberException::new);
        member.validateWrongPassword(tokenRequest.getPassword(), passwordEncoder);
        return member;
    }

    public MemberResponse getMemberInformation(final Long id) {
        Member member = findMemberById(id);
        return MemberResponse.from(member);
    }

    @Transactional
    public void updateName(final Long id, final MemberNameUpdateRequest memberNameUpdateRequest) {
        Member member = findMemberById(id);
        member.updateName(memberNameUpdateRequest.getName());
        memberDao.updateName(member);
    }

    @Transactional
    public void updatePassword(final Long id, final MemberPasswordUpdateRequest memberPasswordUpdateRequest) {
        Member member = findMemberById(id);
        member.updatePassword(memberPasswordUpdateRequest.getOldPassword(),
                memberPasswordUpdateRequest.getNewPassword(),
                passwordEncoder);
        memberDao.updatePassword(member);
    }

    @Transactional
    public void deleteById(final Long id) {
        memberDao.deleteById(id);
    }

    private Member findMemberById(final Long id) {
        return memberDao.findById(id)
                .orElseThrow(NoMemberException::new);
    }
}
