package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"})
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @DisplayName("회원이고 아무 것도 장바구니에 담지 않았을 경우, 상품을 조회한다.")
    @Test
    void findProductsWhoCustomer() {
        saveCustomer();
        List<ProductResponse> productResponses = productService.findProducts();

        assertThat(productResponses.size()).isEqualTo(12);
    }

    void saveCustomer() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        customerService.save(customer);
    }

}
