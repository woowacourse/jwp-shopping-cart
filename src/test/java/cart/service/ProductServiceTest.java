package cart.service;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.controller.dto.response.ProductResponse;
import cart.convertor.ProductEntityConvertor;
import cart.database.dao.ProductDao;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductEntityConvertor productEntityConvertor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productDao, productEntityConvertor);
    }

    @DisplayName("Product create 테스트")
    @Test
    void create() {
        //given
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("Product");
        request.setPrice(10);

        //when
        productService.create(request);

        //then
        verify(productEntityConvertor).dtoToEntity(request);
        verify(productDao).create(any());
    }


    @DisplayName("Product findAll 테스트")
    @Test
    void findAll() {
        List<ProductEntity> expectedDaoReturn = Arrays.asList(
                new ProductEntity(1L, "Product1", "test", 10),
                new ProductEntity(2L, "Product2", "test", 20)
        );
        when(productDao.findAll()).thenReturn(expectedDaoReturn);

        List<ProductResponse> expectedResponse = Arrays.asList(
                new ProductResponse(1L, "Product1", "test", 10),
                new ProductResponse(2L, "Product2", "test", 20)
        );

        //when
        List<ProductResponse> actualResponse = productService.findAll();

        //then
        for (int i = 0; i < expectedResponse.size(); i++) {
            assertThat(expectedResponse.get(i).getId()).isEqualTo(actualResponse.get(i).getId());
            assertThat(expectedResponse.get(i).getName()).isEqualTo(actualResponse.get(i).getName());
            assertThat(expectedResponse.get(i).getImageUrl()).isEqualTo(actualResponse.get(i).getImageUrl());
            assertThat(expectedResponse.get(i).getPrice()).isEqualTo(actualResponse.get(i).getPrice());
        }
    }

    @DisplayName("Product update 테스트")
    @Test
    void update() {
        //given
        Long id = 1L;
        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setName("UpdatedProduct");
        request.setPrice(30);

        //when
        productService.update(id, request);

        //then
        verify(productEntityConvertor).dtoToEntity(request);
        verify(productDao).updateById(eq(id), any());
    }

    @DisplayName("Product delete 테스트")
    @Test
    void delete() {
        //given
        Long id = 1L;

        //when
        productService.delete(id);

        //then
        verify(productDao).deleteById(id);
    }
}
