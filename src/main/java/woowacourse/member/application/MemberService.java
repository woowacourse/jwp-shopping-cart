package woowacourse.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.dto.MemberDeleteRequest;
import woowacourse.member.dto.MemberNameUpdateRequest;
import woowacourse.member.dto.MemberPasswordUpdateRequest;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.dto.MemberResponse;
import woowacourse.member.exception.DuplicateMemberEmailException;
import woowacourse.member.exception.NoMemberException;
import woowacourse.member.exception.WrongPasswordException;
import woowacourse.member.infrastructure.PasswordEncoder;

@Transactional
@Service
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Long save(final MemberRegisterRequest memberRegisterRequest) {
        validateDuplicateEmail(memberRegisterRequest.getEmail());
        Member member = memberRegisterRequest.toEntity();
        member.encodePassword(passwordEncoder);
        return memberDao.save(member);
    }

    private void validateDuplicateEmail(final String email) {
        if (memberDao.isEmailExist(email)) {
            throw new DuplicateMemberEmailException();
        }
    }

    @Transactional(readOnly = true)
    public Member login(final TokenRequest tokenRequest) {
        Member member = memberDao.findByEmail(tokenRequest.getEmail())
                .orElseThrow(NoMemberException::new);

        String encode = passwordEncoder.encode(tokenRequest.getPassword());
        if (!member.authenticate(encode)) {
            throw new WrongPasswordException();
        }
        return member;
    }

    @Transactional(readOnly = true)
    public MemberResponse getMemberInformation(final Long id) {
        Member member = memberDao.findById(id)
                .orElseThrow(NoMemberException::new);
        return MemberResponse.from(member);
    }

    public void updateName(final Long id, final MemberNameUpdateRequest memberNameUpdateRequest) {
        Member member = memberDao.findById(id)
                .orElseThrow(NoMemberException::new);
        member.updateName(memberNameUpdateRequest.getName());
        memberDao.updateName(member);
    }

    public void updatePassword(final Long id, final MemberPasswordUpdateRequest memberPasswordUpdateRequest) {
        Member member = memberDao.findById(id)
                .orElseThrow(NoMemberException::new);
        member.updatePassword(memberPasswordUpdateRequest.getOldPassword(),
                memberPasswordUpdateRequest.getNewPassword(),
                passwordEncoder);
        memberDao.updatePassword(member);
    }

    public void deleteById(final Long id, final MemberDeleteRequest memberDeleteRequest) {
        Member member = memberDao.findById(id)
                .orElseThrow(NoMemberException::new);
        validateWrongPassword(memberDeleteRequest, member);
        memberDao.deleteById(id);
    }

    private void validateWrongPassword(final MemberDeleteRequest memberDeleteRequest, final Member member) {
        if (!member.authenticate(passwordEncoder.encode(memberDeleteRequest.getPassword()))) {
            throw new WrongPasswordException();
        }
    }
}
