package woowacourse.shoppingcart.application;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.exception.BadRequestException;
import woowacourse.auth.exception.NotFoundException;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.EmailDuplicationResponse;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long createCustomer(final CustomerDto newCustomer) {
        final Customer customer = CustomerDto.toCustomer(newCustomer);
        try {
            return customerDao.createCustomer(customer);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException("이미 가입한 사용자입니다.");
        }
    }

    public CustomerResponse findCustomerById(Long customerId) {
        try {
            return customerDao.findByCustomerId(customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("회원을 조회할 수 없습니다.");
        }
    }

    public void updateCustomer(final Long customerId, final ModifiedCustomerDto modifiedCustomerDto) {
        final Customer modifiedCustomer = ModifiedCustomerDto.toModifiedCustomerDto(modifiedCustomerDto);
        final int affectedRows = customerDao.updateCustomer(customerId, modifiedCustomer);
        if (affectedRows != 1) {
            throw new BadRequestException("업데이트가 되지 않았습니다.");
        }
    }

    public void deleteCustomer(final Long customerId) {
        final int affectedRows = customerDao.deleteCustomer(customerId);
        if (affectedRows != 1) {
            throw new BadRequestException("삭제가 되지 않았습니다.");
        }
    }

    public EmailDuplicationResponse isDuplicatedEmail(String email) {
        return new EmailDuplicationResponse(customerDao.hasEmail(email));
    }
}
