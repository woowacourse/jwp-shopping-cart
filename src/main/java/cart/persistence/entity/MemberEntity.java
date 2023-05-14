package cart.persistence.entity;

import java.util.Objects;

public class MemberEntity {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String phoneNumber;

    private MemberEntity(Long id, String email, String password, String name, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static MemberEntity create(Long id, String email, String password, String name, String phoneNumber) {
        return new MemberEntity(id, email, password, name, phoneNumber);
    }

    public static MemberEntity createToSave(String email, String password, String name, String phoneNumber) {
        return new MemberEntity(null, email, password, name, phoneNumber);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberEntity member = (MemberEntity) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
