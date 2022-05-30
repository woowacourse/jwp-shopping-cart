package woowacourse.auth.entity;

public class UserEntity {
    private final Integer id;
    private final String email;
    private final String password;
    private final String profileImageUrl;
    private final boolean terms;

    public UserEntity(String email, String password, String profileImageUrl, boolean terms) {
        this(null, email, password, profileImageUrl, terms);
    }

    public UserEntity(Integer id, String email, String password, String profileImageUrl, boolean terms) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.terms = terms;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public boolean isTerms() {
        return terms;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", terms=" + terms +
                '}';
    }
}
