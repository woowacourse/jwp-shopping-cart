package cart.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {
    
    @Test
    @DisplayName("이메일 생성 테스트")
    void create() {
        //given
        final String email = "hello@echo.com";
        
        //when
        final Email result = new Email(email);
        
        //then
        assertEquals(email, result.getValue());
    }
    
    @Test
    @DisplayName("이메일 생성 실패 테스트")
    void createFail() {
        //given
        final String email = "helloecho.com";
        
        //when
        final IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> new Email(email));
        
        //then
        assertEquals(Email.INVALID_EMAIL_FORMAT_ERROR, exception.getMessage());
    }
}