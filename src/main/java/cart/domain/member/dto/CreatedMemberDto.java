package cart.domain.member.dto;

import cart.domain.member.entity.Member;
import java.time.LocalDateTime;

public class CreatedMemberDto {

    private final Long id;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CreatedMemberDto(final Long id, final String email, final LocalDateTime createdAt,
        final LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CreatedMemberDto of(final Member member) {
        return new CreatedMemberDto(member.getId(), member.getEmail(), member.getCreatedAt(),
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
