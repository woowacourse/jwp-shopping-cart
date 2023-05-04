package cart.service;

import cart.domain.member.Member;
import cart.dto.member.MemberDto;
import cart.dto.member.MemberRequestDto;
import cart.entity.MemberEntity;
import cart.exception.DuplicateEmailException;
import cart.exception.InvalidMemberException;
import cart.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MemberDto join(MemberRequestDto requestDto) {
        validateDuplicateEmail(requestDto.getEmail());
        Member member = new Member(requestDto.getEmail(), requestDto.getPassword());
        MemberEntity entity = new MemberEntity(null, member.getEmail(), member.getPassword());
        MemberEntity savedEntity = repository.save(entity);
        return MemberDto.fromEntity(savedEntity);
    }

    private void validateDuplicateEmail(String email) {
        try {
            repository.findByEmail(email);
        } catch (DuplicateEmailException e) {
            throw new InvalidMemberException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public MemberDto findById(Long id) {
        Optional<MemberEntity> nullableEntity = repository.findById(id);
        if (nullableEntity.isEmpty()) {
            throw new IllegalArgumentException("id와 일치하는 회원이 없습니다.");
        }
        MemberEntity foundEntity = nullableEntity.get();
        return MemberDto.fromEntity(foundEntity);
    }

    public MemberDto findByEmail(String email) {
        Optional<MemberEntity> nullableEntity = repository.findByEmail(email);
        if (nullableEntity.isEmpty()) {
            throw new IllegalArgumentException("email과 일치하는 회원이 없습니다.");
        }
        MemberEntity foundEntity = nullableEntity.get();
        return MemberDto.fromEntity(foundEntity);
    }

    @Transactional(readOnly = true)
    public List<MemberDto> findAll() {
        List<MemberEntity> entities = repository.findAll();
        return entities.stream()
                .map(MemberDto::fromEntity)
                .collect(toList());
    }

    @Transactional
    public MemberDto updateById(MemberRequestDto requestDto, Long id) {
        Member member = new Member(requestDto.getEmail(), requestDto.getPassword());
        MemberEntity entity = new MemberEntity(id, member.getEmail(), member.getPassword());
        repository.update(entity);
        return MemberDto.fromEntity(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
