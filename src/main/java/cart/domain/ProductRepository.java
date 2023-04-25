package cart.domain;

import java.util.List;

import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;

public interface ProductRepository {
    Long save(ProductRequestDto productRequestDto);
    ProductResponseDto findById(Long id);
    List<ProductResponseDto> findAll();
    void updateById(ProductRequestDto productRequestDto, Long id);
    void deleteById(Long id);
}
