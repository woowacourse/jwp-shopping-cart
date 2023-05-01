package cart.entity;

public class MemberEntity {

    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String password;

    public MemberEntity(final String email, final String name, final String phoneNumber, final String password) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }
}
