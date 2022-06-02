package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthorizationException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.DuplicateNameException;
import woowacourse.shoppingcart.support.Encryptor;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {
    private final CustomerDao customerDao;
    private final Encryptor encryptor;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(final CustomerDao customerDao, final Encryptor encryptor, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.encryptor = encryptor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void addCustomer(final CustomerRequest customerRequest) {
        if (customerDao.existsByName(customerRequest.getName())) {
            throw new DuplicateNameException();
        }
        String encryptedPassword = encryptor.encrypt(customerRequest.getPassword());
        customerDao.save(customerRequest.getName(), encryptedPassword);
    }

    public void deleteCustomer(final HttpServletRequest request) {
        String customerName = getNameFromToken(request);
        customerDao.deleteByName(customerName);
    }

    public CustomerResponse findCustomer(final HttpServletRequest request) {
        String customerName = getNameFromToken(request);
        final Customer customer = customerDao.findCustomerByName(customerName);
        return new CustomerResponse(customer.getName());
    }

    public void editCustomer(final HttpServletRequest request, final CustomerRequest editRequest) {
        String customerName = getNameFromToken(request);
        String encryptedPassword = encryptor.encrypt(editRequest.getPassword());
        customerDao.updateByName(customerName, encryptedPassword);
    }

    public void validateNameAndPassword(final String name, final String password) {
        String encryptedPassword = encryptor.encrypt(password);
        if (customerDao.existsIdByNameAndPassword(name, encryptedPassword)) {
            return;
        }
        throw new AuthorizationException("로그인에 실패했습니다.");
    }

    private String getNameFromToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        return jwtTokenProvider.getPayload(token);
    }
}
