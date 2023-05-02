package cart.service;

import cart.controller.dto.MemberDto;
import cart.domain.Member;
import cart.domain.MemberPassword;
import cart.domain.MemberRole;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.MemberEntity;
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

    public long save(final MemberDto memberDto) {
        if (memberDao.findByEmail(memberDto.getEmail()).isPresent()) {
            throw new GlobalException(ErrorCode.MEMBER_DUPLICATE_EMAIL);
        }
        final MemberEntity memberEntity = convertToEntity(memberDto);
        return memberDao.insert(memberEntity);
    }

    public MemberDto getById(final Long id) {
        return memberDao.findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberDto getByEmail(final String email) {
        return memberDao.findByEmail(email)
            .map(this::convertToDto)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public List<MemberDto> getMembers() {
        final List<MemberEntity> userEntities = memberDao.findAll();
        return userEntities.stream()
            .map(this::convertToDto)
            .collect(Collectors.toUnmodifiableList());
    }

    private MemberEntity convertToEntity(final MemberDto memberDto) {
        final Member member = Member.create(memberDto.getEmail(), memberDto.getPassword(),
            memberDto.getNickname(), memberDto.getTelephone(),
            MemberRole.from(memberDto.getRole()));
        return new MemberEntity(member.getEmail(), member.getRole().name(),
            member.getPassword(), member.getNickname(), member.getTelephone());
    }

    private MemberDto convertToDto(final MemberEntity memberEntity) {
        final MemberPassword password = MemberPassword.create(memberEntity.getPassword());
        final String decodedPassword = password.decodePassword();
        return new MemberDto(memberEntity.getId(), memberEntity.getRole(), memberEntity.getEmail(),
            decodedPassword, memberEntity.getNickname(), memberEntity.getTelephone());
    }
}
