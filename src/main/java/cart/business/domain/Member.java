package cart.business.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Member {

    private final Integer id;
    private String email;
    private String password;
    private Cart cart;
    public static int sequence = 1;

    public Member(String email, String password) {
        this.id = sequence++;
        this.cart = new Cart(id, new Products(new ArrayList<>()));
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member user = (Member) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
