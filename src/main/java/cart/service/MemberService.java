package cart.service;

import cart.dto.MemberDto;
import cart.entity.Member;
import cart.exception.customExceptions.DataNotFoundException;
import cart.exception.customExceptions.NotUniqueValueException;
import cart.exception.customExceptions.PasswordNotMatchException;
import cart.repository.dao.memberDao.MemberDao;
import cart.utils.CaesarCipher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long registerMember(final MemberDto memberDto) {
        final Member member = new Member(
                memberDto.getEmail(),
                memberDto.getName(),
                CaesarCipher.encrypt(memberDto.getPassword()));

        final Optional<Long> id = memberDao.save(member);
        if (id.isEmpty()) {
            throw new NotUniqueValueException("중복되는 email입니다. 다른 이메일을 입력해주세요.");
        }
        return id.get();
    }

    public MemberDto loginMember(final String email, final String password) {
        Optional<Member> findMember = memberDao.findByEmail(email);
        if (findMember.isEmpty()) {
            throw new DataNotFoundException("해당 사용자가 존재하지 않습니다.");
        }

        Member member = findMember.get();
        String encryptedPassword = member.getPassword();
        if (!encryptedPassword.equals(CaesarCipher.encrypt(password))) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        return new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPassword());
    }

    public List<MemberDto> findAllMember() {
        final List<Member> members = memberDao.findAll();
        return members.stream()
                .map(member -> {
                    final String email = member.getEmail();
                    final String name = member.getName();
                    final String password = member.getPassword();
                    return new MemberDto(email, name, password);
                })
                .collect(Collectors.toList());
    }
}
