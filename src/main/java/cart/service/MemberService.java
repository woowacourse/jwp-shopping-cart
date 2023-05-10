package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberResponse;
import cart.entity.MemberEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberDao memberDao;

    public List<MemberResponse> findAll() {
        final List<MemberEntity> result = memberDao.findAll();

        return result.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }
}
