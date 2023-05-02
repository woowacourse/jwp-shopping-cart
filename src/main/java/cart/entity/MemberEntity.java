package cart.entity;

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
}
