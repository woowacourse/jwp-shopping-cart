package cart.user.dao;

import cart.user.domain.Email;
import cart.user.domain.Password;
import cart.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcUserDAO.class)
class JdbcUserDAOTest {
    
    @Autowired
    private JdbcUserDAO jdbcUserDAO;
    
    @Test
    @DisplayName("유저 DB 저장 테스트")
    void insert() {
        //given
        final int initialCount = this.jdbcUserDAO.findAll().size();
        final User user = new User(new Email("test@test.com"), new Password("#test1234"));
        
        //when
        this.jdbcUserDAO.insert(user);
        
        //then
        final int finalCount = this.jdbcUserDAO.findAll().size();
        Assertions.assertEquals(initialCount + 1, finalCount);
        final String email = user.getEmail().getValue();
        final User resultUser = this.jdbcUserDAO.findByEmail(email);
        Assertions.assertEquals(email, resultUser.getEmail().getValue());
    }
    
    @Test
    @DisplayName("유저 DB 저장 실패 테스트 - 중복 이메일")
    void insertFail() {
        //given
        final User user = new User(new Email("echo@test.com"), new Password("#test1234"));
        this.jdbcUserDAO.insert(user);
        
        //when
        final Email duplicatedEmail = new Email("echo@test.com");
        final boolean exist = this.jdbcUserDAO.isExist(duplicatedEmail.getValue());
        
        //then
        Assertions.assertTrue(exist);
    }
    
    @Test
    @DisplayName("유저 Password 테스트")
    void findByEmail() {
        //given
        final User user = new User(new Email("echo@test.com"), new Password("#test1234"));
        this.jdbcUserDAO.insert(user);
        
        //when
        final User findUser = new User(new Email("echo@test.com"), new Password("#test1234"));
        final boolean isCorrectPassword = this.jdbcUserDAO.isCorrectPassword(findUser);
        
        final User wrongPasswordUser = new User(new Email("echo@test.com"), new Password("#test12345"));
        final boolean isWrongPassword = this.jdbcUserDAO.isCorrectPassword(wrongPasswordUser);
        
        //then
        Assertions.assertTrue(isCorrectPassword);
        Assertions.assertFalse(isWrongPassword);
    }
}