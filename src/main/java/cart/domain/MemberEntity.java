package cart.domain;

public class MemberEntity {

    private final String email;
    private final String password;


    public MemberEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {

        private String email;
        private String password;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public MemberEntity build() {
            return new MemberEntity(email, password);
        }

    }

}