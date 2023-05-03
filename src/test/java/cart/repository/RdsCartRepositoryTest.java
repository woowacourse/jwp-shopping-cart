package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dao.UserDao;
import cart.domain.product.Product;
import cart.domain.user.Email;
import cart.domain.user.User;
import cart.entiy.ProductEntity;
import cart.entiy.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class RdsCartRepositoryTest {

    private RdsCartRepository rdsCartRepository;
    private CartDao cartDao;
    private ProductDao productDao;
    private UserDao userDao;

    @Autowired
    void setUp(final JdbcTemplate jdbcTemplate) {
        cartDao = new CartDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        userDao = new UserDao(jdbcTemplate);
        rdsCartRepository = new RdsCartRepository(cartDao, productDao);
    }

    @Nested
    @DisplayName("product와 user가 저장되어 있을 때")
    class DescribeSave {

        private long productId;

        @BeforeEach
        void setUp() {
            final ProductEntity productEntity = productDao.insert(new ProductEntity("odo", "url", 1));
            productId = productEntity.getId().getValue();
            userDao.insert(new UserEntity("a@a.com", "password1"));
        }

        @Nested
        @DisplayName("cart를 저장하면")
        class ContextSave {
            
            @BeforeEach
            void setUp() {
                rdsCartRepository.save(
                        new User("a@a.com", "password1"),
                        new Product(productId, "odo", "url", 1)
                );
            }

            @Test
            @DisplayName("조회할 수 있다")
            void save() {
                final List<Product> products = rdsCartRepository.findByEmail(new Email("a@a.com"));
                assertThat(products).hasSize(1);
            }
        }
    }
}
