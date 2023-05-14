package cart;

import cart.cart_product.dao.CartProductDao;
import cart.member.dao.MemberDao;
import cart.product.dao.ProductDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Sql("/reset-cart_product-data.sql")
public abstract class ApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    CartProductDao cartProductDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    MemberDao memberDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}
