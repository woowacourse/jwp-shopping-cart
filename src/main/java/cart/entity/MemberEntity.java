package cart.entity;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.lang.reflect.Member;

>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
=======
>>>>>>> d9c17a80 (chore: 불필요한 import 제거)
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
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
