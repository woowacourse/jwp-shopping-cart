package domain;

import java.util.List;

import dto.ProductRequestDto;

public interface ProductRepository {
    Long save(ProductRequestDto productRequestDto);
    ProductRequestDto findById(Long id);
    List<ProductRequestDto> findAll();
    void updateById(ProductRequestDto productRequestDto, Long id);
    void deleteById(Long id);
}
