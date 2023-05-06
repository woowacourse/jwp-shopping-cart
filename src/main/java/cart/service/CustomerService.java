package cart.service;

import cart.auth.UnauthorizedException;
import cart.dao.CustomerDao;
import cart.dao.entity.CustomerEntity;
import cart.domain.Email;
import cart.domain.Password;
import cart.service.dto.CustomerInfoDto;
import cart.service.dto.SignUpDto;
import cart.service.exception.InvalidEmailException;
import cart.service.exception.InvalidEmailException.Language;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public long save(final SignUpDto signUpDto) {
        Email email = signUpDto.getEmailDomain();
        Password password = signUpDto.getPasswordDomain();
        validateEmailExistence(email);
        return customerDao.insert(CustomerEntity.from(email, password));
    }

    private void validateEmailExistence(final Email email) {
        customerDao.findByEmail(email.getEmail())
                .ifPresent(customer -> {
                    throw new InvalidEmailException(Language.KO);
                });
    }

    public CustomerInfoDto findByEmail(final String email) {
        return customerDao.findByEmail(email)
                .map(CustomerInfoDto::fromEntity)
                .orElseThrow(() -> new InvalidEmailException(Language.KO));
    }

    public long findIdByEmail(final String email) {
        return customerDao.findByEmail(email)
                .map(CustomerEntity::getId)
                .orElseThrow(() -> new UnauthorizedException(UnauthorizedException.Language.KO));
    }

    public List<CustomerInfoDto> findAll() {
        return customerDao.findAll()
                .stream()
                .map(CustomerInfoDto::fromEntity)
                .collect(Collectors.toList());
    }

    public boolean isAbleToLogin(final String email, final String password) {
        return customerDao.isEmailAndPasswordExist(email, password);
    }

}
