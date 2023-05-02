package cart.service;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.dao.dto.MemberDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberResponse findOne(Long id) {
        MemberDto memberDto = memberDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 존재하지 않습니다."));

        return new MemberResponse(memberDto.getId(), memberDto.getEmail(), memberDto.getPassword(), memberDto.getName());
    }

    public MemberResponse findByEmailAndPassword(String email, String password) {
        MemberDto memberDto = memberDao.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 존재하지 않습니다."));

        return new MemberResponse(memberDto.getId(), memberDto.getEmail(), memberDto.getPassword(), memberDto.getName());
    }

    public List<MemberResponse> findAll() {
        return memberDao.findAll()
                .stream()
                .map(memberDto -> new MemberResponse(memberDto.getId(), memberDto.getEmail(), memberDto.getPassword(), memberDto.getName()))
                .collect(Collectors.toList());
    }

}
