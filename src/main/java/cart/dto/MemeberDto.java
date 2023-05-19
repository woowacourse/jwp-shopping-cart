package cart.dto;

public class MemeberDto {
    private String email;
    private String passowrd;

    public MemeberDto(String email, String passowrd) {
        this.email = email;
        this.passowrd = passowrd;
    }

    public String getEmail() {
        return email;
    }

    public String getPassowrd() {
        return passowrd;
    }
}
