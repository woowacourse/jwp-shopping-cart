package cart.service;

import cart.controller.dto.MemberDto;
import cart.domain.Member;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.MemberEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public long save(final MemberDto memberDto) {
        final MemberEntity memberEntity = convertToEntity(memberDto);
        return memberDao.insert(memberEntity);
    }

    public MemberDto getById(final Long id) {
        return memberDao.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
    }

    public List<MemberDto> getMembers() {
        final List<MemberEntity> userEntities = memberDao.findAll();
        return userEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toUnmodifiableList());
    }

    private MemberEntity convertToEntity(final MemberDto memberDto) {
        final Member member = Member.create(memberDto.getEmail(), memberDto.getPassword(),
                memberDto.getNickname(), memberDto.getTelephone());
        final String encodedPassword = encodePassword(member.getPassword());
        return new MemberEntity(member.getEmail(), encodedPassword, member.getNickname(), member.getTelephone());
    }

    private MemberDto convertToDto(final MemberEntity memberEntity) {
        final String decodedPassword = decodePassword(memberEntity.getPassword());
        return new MemberDto(memberEntity.getId(), memberEntity.getEmail(), decodedPassword,
                memberEntity.getNickname(), memberEntity.getTelephone());
    }

    private String encodePassword(final String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private String decodePassword(final String encodedPassword) {
        return new String(Base64.getDecoder().decode(encodedPassword.getBytes()), StandardCharsets.UTF_8);
    }
}
