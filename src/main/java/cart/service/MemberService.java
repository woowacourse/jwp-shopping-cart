package cart.service;

import cart.domain.cart.CartRepository;
import cart.domain.member.Member;
import cart.domain.member.MemberRepository;
import cart.service.dto.MemberAuthDto;
import cart.service.dto.MemberSaveDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    public MemberService(MemberRepository memberRepository, CartRepository cartRepository) {
        this.memberRepository = memberRepository;
        this.cartRepository = cartRepository;
    }

    public void save(MemberSaveDto memberSaveDto) {
        Member member = Member.createToSave(
                memberSaveDto.getEmail(),
                memberSaveDto.getPassword(),
                memberSaveDto.getName(),
                memberSaveDto.getPhoneNumber()
        );
        Long userId = this.memberRepository.save(member);
        this.cartRepository.create(userId);
    }

    public List<Member> findAll() {
        return this.memberRepository.findAll();
    }

    public Member signUp(MemberAuthDto memberAuthDto) {
        Member member = this.memberRepository.findByEmail(memberAuthDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 유저가 존재하지 않습니다." + System.lineSeparator() +
                        "존재하지 않는 이메일: " + memberAuthDto.getEmail()));
        if (member.checkPassword(memberAuthDto.getPassword())) {
            return member;
        }
        throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
    }
}
