package cart.service;

import cart.dao.CustomerDao;
import cart.dao.entity.CustomerEntity;
import cart.domain.Email;
import cart.domain.Password;
import cart.service.dto.SignUpRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public long save(final SignUpRequest signUpRequest) {
        Email email = signUpRequest.getEmail();
        Password password = signUpRequest.getPassword();
        validateEmailExistence(email);
        return customerDao.insert(toCustomerEntity(email, password));
    }

    private void validateEmailExistence(final Email email) {
        customerDao.findByEmail(email.getEmail())
                .ifPresent(customer -> {
                    throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
                });
    }

    private CustomerEntity toCustomerEntity(final Email email, final Password password) {
        return new CustomerEntity.Builder()
                .email(email.getEmail())
                .password(password.getPassword())
                .build();
    }

    public CustomerEntity findByEmail(final String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 입니다."));
    }

}
