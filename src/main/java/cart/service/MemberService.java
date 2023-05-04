package cart.service;

import cart.domain.Member;
import cart.domain.MemberPassword;
import cart.domain.MemberRole;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.MemberEntity;
import cart.service.dto.MemberRequest;
import cart.service.dto.MemberResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public long save(final MemberRequest memberRequest) {
        if (memberDao.findByEmail(memberRequest.getEmail()).isPresent()) {
            throw new GlobalException(ErrorCode.MEMBER_DUPLICATE_EMAIL);
        }
        final MemberEntity memberEntity = convertToEntity(memberRequest);
        return memberDao.insert(memberEntity);
    }

    public MemberResponse getById(final Long id) {
        return memberDao.findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberResponse getByEmailAndPassword(final String email, String password) {
        return memberDao.findByEmailAndPassword(email, password)
            .map(this::convertToDto)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public List<MemberResponse> getMembers() {
        final List<MemberEntity> userEntities = memberDao.findAll();
        return userEntities.stream()
            .map(this::convertToDto)
            .collect(Collectors.toUnmodifiableList());
    }

    private MemberEntity convertToEntity(final MemberRequest memberRequest) {
        final Member member = Member.create(memberRequest.getEmail(), memberRequest.getPassword(),
            memberRequest.getNickname(), memberRequest.getTelephone(),
            MemberRole.from(memberRequest.getRole()));
        return new MemberEntity(member.getEmail(), member.getRole().name(),
            member.getPassword(), member.getNickname(), member.getTelephone());
    }

    private MemberResponse convertToDto(final MemberEntity memberEntity) {
        final MemberPassword password = MemberPassword.create(memberEntity.getPassword());
        final String decodedPassword = password.decodePassword();
        return new MemberResponse(memberEntity.getId(), memberEntity.getRole(), memberEntity.getEmail(),
            decodedPassword, memberEntity.getNickname(), memberEntity.getTelephone());
    }
}
