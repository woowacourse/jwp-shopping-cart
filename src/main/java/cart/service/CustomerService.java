package cart.service;

import cart.dao.CustomerDao;
import cart.dao.entity.CustomerEntity;
import cart.domain.Email;
import cart.domain.Password;
import cart.service.dto.CustomerResponse;
import cart.service.dto.SignUpRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public long save(final SignUpRequest signUpRequest) {
        Email email = new Email(signUpRequest.getEmail());
        Password password = new Password(signUpRequest.getPassword());
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

    public CustomerResponse findByEmail(final String email) {
        return customerDao.findByEmail(email)
                .map(CustomerResponse::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 입니다."));
    }

    public List<CustomerResponse> findAll() {
        return customerDao.findAll()
                .stream()
                .map(CustomerResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
