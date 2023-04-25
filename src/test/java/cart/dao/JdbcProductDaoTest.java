package cart.dao;

import cart.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JdbcProductDaoTest {

    @InjectMocks
    private JdbcProductDao productDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void test() {
        when(productDao.selectAll()).thenReturn(List.of(ProductEntity.of(1L, "chicken", "image", 10000)));

        assertAll(
                () -> assertThat(productDao.selectAll().get(0).getId()).isEqualTo(1L),
                () -> assertThat(productDao.selectAll().get(0).getName()).isEqualTo("chicken"),
                () -> assertThat(productDao.selectAll().get(0).getImage()).isEqualTo("image"),
                () -> assertThat(productDao.selectAll().get(0).getPrice()).isEqualTo(10000)
        );
    }
}
