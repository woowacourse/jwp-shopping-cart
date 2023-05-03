package cart.dao.entity;

public class MemberEntity {

    private final int id;
    private final String email;
    private final String password;

    private MemberEntity(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private int id;
        private String email;
        private String password;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public MemberEntity build() {
            return new MemberEntity(id, email, password);
        }
    }
}
