package cart.entity;

public class MemberEntity {

    private int id;
    private String email;
    private String password;

    public MemberEntity(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public MemberEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
