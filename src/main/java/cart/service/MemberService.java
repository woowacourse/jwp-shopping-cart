package cart.service;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.dto.response.MemberResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao userDao;

    @Autowired
    public MemberService(final MemberDao userDao) {
        this.userDao = userDao;
    }

    public List<MemberResponse> findAll() {
        final List<MemberEntity> memberEntities = userDao.selectAll();
        return memberEntities.stream()
                .map(entity -> new MemberResponse(
                        entity.getId(),
                        entity.getEmail(),
                        entity.getPassword()
                        )
                ).collect(Collectors.toUnmodifiableList());
    }
}
