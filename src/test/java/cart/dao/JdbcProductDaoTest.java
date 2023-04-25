package cart.dao;

import cart.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JdbcProductDaoTest {

    @InjectMocks
    private JdbcProductDao productDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @DisplayName("모든 상품 데이터를 제대로 반환하는지 확인한다")
    @Test
    void selectAllTest() {
        final List<ProductEntity> data = List.of(
                ProductEntity.of(1L, "chicken", "image", 10000),
                ProductEntity.of(2L, "pizza", "image2", 20000)
        );
        when(productDao.selectAll()).thenReturn(data);

        final List<ProductEntity> productEntities = productDao.selectAll();

        assertAll(
                () -> assertThat(productEntities.size()).isEqualTo(data.size()),
                () -> assertThat(productEntities.get(0).getId()).isEqualTo(1L),
                () -> assertThat(productEntities.get(0).getName()).isEqualTo("chicken"),
                () -> assertThat(productEntities.get(0).getImage()).isEqualTo("image"),
                () -> assertThat(productEntities.get(0).getPrice()).isEqualTo(10000),
                () -> assertThat(productEntities.get(1).getId()).isEqualTo(2L),
                () -> assertThat(productEntities.get(1).getName()).isEqualTo("pizza"),
                () -> assertThat(productEntities.get(1).getImage()).isEqualTo("image2"),
                () -> assertThat(productEntities.get(1).getPrice()).isEqualTo(20000)
        );
    }
}
