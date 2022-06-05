package woowacourse.shoppingcart.service;

import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.service.dto.CustomerCreateServiceRequest;
import woowacourse.shoppingcart.service.dto.CustomerDeleteServiceRequest;
import woowacourse.shoppingcart.service.dto.CustomerUpdatePasswordServiceRequest;
import woowacourse.shoppingcart.service.dto.CustomerUpdateProfileServiceRequest;

public interface CustomerService {
    Customer create(CustomerCreateServiceRequest customerRequest);

    Customer getById(Long id);

    Customer getByEmail(String email);

    Customer getByName(String name);

    Customer updateProfile(Long id, CustomerUpdateProfileServiceRequest customerUpdateProfileRequest);

    Customer updatePassword(Long id, CustomerUpdatePasswordServiceRequest customerUpdatePasswordRequest);

    long delete(long id, CustomerDeleteServiceRequest customerDeleteRequest);
}
