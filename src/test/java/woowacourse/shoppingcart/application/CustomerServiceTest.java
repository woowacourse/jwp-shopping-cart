package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.ChangeCustomerRequest;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@JdbcTest
@Transactional
@Sql("/data-test.sql")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomerServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private CustomerService customerService;
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        customerDao = new CustomerDao(jdbcTemplate, namedParameterJdbcTemplate);
        customerService = new CustomerService(customerDao);
    }

    @DisplayName("유저 생성 정보를 입력 받아, 회원가입을 한다.")
    @Test
    void createCustomer() {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
            "beomWhale@naver.com", "범고래", "Password12345!");

        // when
        Long savedId = customerService.createCustomer(customerCreateRequest);

        // then
        assertThat(savedId).isNotNull();
    }

    @DisplayName("닉네임이 중복될 경우, 예외가 발생한다.")
    @Test
    void validateDuplicationNickname() {
        // given
        customerDao.save(new Customer("awesomeo@naver.com", "범고래", "Password12345!"));
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
            "beomWhale@naver.com", "범고래", "Password12345!");

        // when && then
        assertThatThrownBy(
            () -> customerService.createCustomer(customerCreateRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @DisplayName("이메일을 입력 받아 정보를 조회한다.")
    @Test
    void findCustomerByEmail() {
        // given
        String email = "beomWhale@naver.com";
        String password = "Password1234!";
        String nickname = "beomWhale";
        Customer customer = new Customer(email, nickname, password);
        Long saveId = customerDao.save(customer);

        // when
        CustomerResponse expected = new CustomerResponse(saveId, email, nickname);
        CustomerResponse customerResponse = customerService.findCustomerByEmail(email);

        // then
        assertThat(customerResponse).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("새로운 패스워드를 입력받아 새로운 패스워드로 변경한다.")
    @Test
    void changePassword() {
        // given
        String prevPassword = "Password123!";
        String email = "beomWhale@naver.com";
        Customer customer = new Customer(email, "beomWhale", prevPassword);
        customerDao.save(customer);

        // when
        String newPassword = "Password1234!";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(prevPassword,
            newPassword);
        customerService.changePassword(email, changePasswordRequest);
        Customer findCustomer = customerDao.findIdByEmail(email).get();

        // then
        assertThat(findCustomer.isPasswordMatched(newPassword)).isTrue();
    }

    @DisplayName("새로운 닉네임 입력받아 새로운 닉네임으로 변경한다.")
    @Test
    void changeNickname() {
        // given
        String email = "beomWhale@naver.com";
        String password = "Password123!";
        String prevNickname = "beomWhale";
        Customer customer = new Customer(email, prevNickname, password);
        customerDao.save(customer);

        // when
        String changedNickname = "changed";
        ChangeCustomerRequest changeCustomerRequest = new ChangeCustomerRequest(changedNickname);
        customerService.changeNickname(email, changeCustomerRequest);
        Customer findCustomer = customerDao.findIdByEmail(email).get();

        // then
        assertThat(findCustomer.getNickname()).isEqualTo(changedNickname);
    }

    @DisplayName("새로운 닉네임으로 변경시 기존에 존재하는 닉네임과 중복될 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenDuplicateNickname() {
        // given
        String nickname = "beomWhale";
        customerDao.save(new Customer("beomWhale@naver.com", nickname, "Password123!"));

        String email = "awesome@gmail.com";
        Customer customer = new Customer(email, "awesome", "Password123!");
        customerDao.save(customer);

        assertThatThrownBy(
            () -> customerService.changeNickname(email, new ChangeCustomerRequest(nickname)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @DisplayName("회원가입 시 기존에 존재하는 닉네임과 중복될 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenDuplicateEmail() {
        // given
        String email = "beomWhale@naver.com";
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(email, "beomWhale",
            "Password123!");
        CustomerCreateRequest duplicationCustomerCreateRequest = new CustomerCreateRequest(email,
            "awesome",
            "Password123!");
        customerService.createCustomer(customerCreateRequest);

        // when && then
        assertThatThrownBy(() -> customerService.createCustomer(duplicationCustomerCreateRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 존재하는 이메일입니다.");
    }
}
