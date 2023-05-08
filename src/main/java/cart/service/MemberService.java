package cart.service;

import cart.dto.MemberDto;
import cart.entity.Member;
import cart.exception.customexceptions.DataNotFoundException;
import cart.exception.customexceptions.NotUniqueValueException;
import cart.exception.customexceptions.NotValidDataException;
import cart.exception.customexceptions.PasswordNotMatchException;
import cart.repository.dao.memberDao.MemberDao;
import cart.utils.CaesarCipher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
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

        return memberDao.save(member)
                .orElseThrow(() -> new NotValidDataException("적절하지 않은 정보 입니다."));
    }

    @Transactional(readOnly = true)
    public MemberDto loginMember(final String email, final String password) {
        final Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
        if (!member.matchPassword(CaesarCipher.encrypt(password))) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }
        return new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getName(),
                CaesarCipher.decrypt(member.getPassword()));
    }

    @Transactional(readOnly = true)
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
