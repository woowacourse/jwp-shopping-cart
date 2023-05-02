package cart.dto;

import cart.domain.member.entity.Member;
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

    public static MemberCreateResponse of(final Member member) {
        return new MemberCreateResponse(member.getId(), member.getEmail(), member.getCreatedAt(),
            member.getUpdatedAt());
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
