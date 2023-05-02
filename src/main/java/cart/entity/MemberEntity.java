package cart.entity;

import java.util.Objects;

public class MemberEntity {

  private final Long id;
  private final String email;
  private final String password;

  public MemberEntity(Long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    Objects.requireNonNull(id, "id가 null입니다.");
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
