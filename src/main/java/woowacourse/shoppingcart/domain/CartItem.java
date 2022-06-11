package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CartItem {

    private final Customer customer;
    private final Product product;

    public CartItem(Customer customer, Product product) {
        this.customer = customer;
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }
}
