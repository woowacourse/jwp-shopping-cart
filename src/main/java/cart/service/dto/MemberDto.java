package cart.service.dto;

import cart.domain.member.Member;

public class MemberDto {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String phoneNumber;

    private MemberDto(Long id, String email, String password, String name, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static MemberDto from(Member member) {
        String name = member.getName().orElse(null);
        String phoneNumber = member.getPhoneNumber().orElse(null);
        return new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                name,
                phoneNumber
        );
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
