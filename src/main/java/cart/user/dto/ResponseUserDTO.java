package cart.user.dto;

import cart.user.domain.User;

public class ResponseUserDTO {
    
    private final String email;
    private final String password;
    
    public ResponseUserDTO(final String email, final String password) {
        this.email = email;
        this.password = password;
    }
    
    public static ResponseUserDTO from(final User user) {
        final String email = user.getEmail().getValue();
        final String password = user.getPassword().getValue();
        return new ResponseUserDTO(email, password);
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPassword() {
        return this.password;
    }
}
