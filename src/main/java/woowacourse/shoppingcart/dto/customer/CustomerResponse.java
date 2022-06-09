package woowacourse.shoppingcart.dto.customer;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {

    private CustomerResponseNested customer;

    private CustomerResponse() {
    }

    public CustomerResponse(final CustomerResponseNested customer) {
        this.customer = customer;
    }

    public static CustomerResponse from(final Customer customer) {
        return new CustomerResponse(CustomerResponseNested.from(customer));
    }

    public CustomerResponseNested getCustomer() {
        return customer;
    }

    public static class CustomerResponseNested {

        private String username;
        private String phoneNumber;
        private String address;

        private CustomerResponseNested() {
        }

        public CustomerResponseNested(final String username, final String phoneNumber, final String address) {
            this.username = username;
            this.phoneNumber = phoneNumber;
            this.address = address;
        }

        public static CustomerResponseNested from(final Customer customer) {
            return new CustomerResponseNested(
                    customer.getUsername(),
                    customer.getPhoneNumber(),
                    customer.getAddress()
            );
        }

        public String getUsername() {
            return username;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getAddress() {
            return address;
        }
    }
}
