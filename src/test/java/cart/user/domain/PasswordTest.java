package cart.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.settings.domain.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordTest {
    
    @Test
    @DisplayName("비밀번호 생성 테스트")
    void create() {
        //given
        final String password = "#?Hello1234";
        
        //when
        final Password result = new Password(password);
        
        //then
        assertEquals(password, result.getValue());
    }
    
    @Test
    @DisplayName("비밀번호 생성 실패 테스트 - 8자 이하")
    void createFail() {
        //given
        final String password = "#?Hell1";
        
        //when
        final IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new Password(password));
        
        //then
        assertEquals(Password.PASSWORD_UNDER_MIN_LENGTH_ERROR, exception.getMessage());
    }
    
    @Test
    @DisplayName("비밀번호 생성 실패 테스트 - 20자 이상")
    void createFail2() {
        //given
        final String password = "#?Hello1234Hello12345";
        
        //when
        final IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new Password(password));
        
        //then
        assertEquals(Password.PASSWORD_OVER_MAX_LENGTH_ERROR, exception.getMessage());
    }
    
    @Test
    @DisplayName("비밀번호 생성 실패 테스트 - 숫자 없음")
    void createFail3() {
        //given
        final String password = "#?HelloHello";
        
        //when
        final IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new Password(password));
        
        //then
        assertEquals(Password.PASSWORD_NOT_HAVING_NUMBER_ERROR, exception.getMessage());
    }
    
    @Test
    @DisplayName("비밀번호 생성 실패 테스트 - 영문자 없음")
    void createFail4() {
        //given
        final String password = "#?12341234";
        
        //when
        final IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new Password(password));
        
        //then
        assertEquals(Password.PASSWORD_NOT_HAVING_ALPHABET_ERROR, exception.getMessage());
    }
    
    @Test
    @DisplayName("비밀번호 생성 실패 테스트 - 특수문자 없음")
    void createFail5() {
        //given
        final String password = "Hello1234";
        
        //when
        final IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new Password(password));
        
        //then
        assertEquals(Password.PASSWORD_NOT_HAVING_SPECIAL_CHARACTER_ERROR, exception.getMessage());
    }
}