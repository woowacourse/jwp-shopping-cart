package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final MemberDao memberDao;

  public MemberService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  public List<MemberResponse> findAll() {
    return memberDao.findAll().stream()
        .map(member -> new MemberResponse(member.getEmail(), member.getPassword()))
        .collect(Collectors.toList());
  }
}
