package cart.domain;

import java.util.List;

import cart.dto.ProductDto;

public interface ProductRepository {
    Long save(ProductDto productDto);

    ProductDto findById(Long id);

    List<ProductDto> findAll();

    void updateById(ProductDto productRequestDto, Long id);

    void deleteById(Long id);
}
