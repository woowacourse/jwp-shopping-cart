package cart.service;

import static java.util.stream.Collectors.toList;

import cart.domain.Member;
import cart.dto.member.MemberDto;
import cart.exception.AuthenticationException;
import cart.exception.DuplicateEmailException;
import cart.repository.MemberDao;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void signupMember(String email, String password) {
        validateEmail(email);
        memberDao.save(new Member(email, password));
    }

    private void validateEmail(String email) {
        if (memberDao.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    public List<MemberDto> findAllMembers() {
        return memberDao.findAll()
                .stream()
                .map(MemberDto::fromEntity)
                .collect(toList());
    }

    public Long findIdByEmailAndPassword(String email, String password) {
        Optional<Long> memberId = memberDao.findByEmailAndPassword(email, password);
        return memberId.orElseThrow(() -> new AuthenticationException("해당 이메일이 존재하지 않거나 비밀번호가 틀렸습니다."));
    }
}
