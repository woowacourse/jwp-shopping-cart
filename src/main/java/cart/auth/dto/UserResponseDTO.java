package cart.auth.dto;

import cart.auth.domain.User;

public class UserResponseDTO {
    
    private final String email;
    private final String password;
    private final long id;
    
    public UserResponseDTO(final long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    
    public static UserResponseDTO from(final User user) {
        final long id = user.getId();
        final String email = user.getEmail().getValue();
        final String password = user.getPassword().getValue();
        return new UserResponseDTO(id, email, password);
    }
    
    public long getId() {
        return this.id;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPassword() {
        return this.password;
    }
}
