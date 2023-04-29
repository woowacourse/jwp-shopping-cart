package cart.service;

import static java.util.stream.Collectors.toList;

import cart.domain.Member;
import cart.dto.member.MemberDto;
import cart.exception.DuplicateEmailException;
import cart.repository.MemberDao;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
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
}
