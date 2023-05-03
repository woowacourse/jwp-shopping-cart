package cart.service;

import cart.dao.Dao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dto.MemberDto;
import cart.dto.ProductDto;
import cart.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final Dao dao;

    public MemberService(MemberDao dao) {
        this.dao = dao;
    }


    public List<MemberDto> selectAllMember() {
        List<Member> members = dao.selectAll();
        return members.stream()
                .map(MemberDto::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
