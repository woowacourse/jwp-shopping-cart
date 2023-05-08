package cart.dto;

import cart.domain.member.dto.MemberCreateDto;
import java.time.LocalDateTime;

public class MemberCreateResponse {

    private Long id;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemberCreateResponse() {
    }

    public MemberCreateResponse(final Long id, final String email, final LocalDateTime createdAt,
        final LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MemberCreateResponse of(final MemberCreateDto memberCreateDto) {
        return new MemberCreateResponse(memberCreateDto.getId(), memberCreateDto.getEmail(),
            memberCreateDto.getCreatedAt(), memberCreateDto.getUpdatedAt());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
