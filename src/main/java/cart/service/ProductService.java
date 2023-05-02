package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponseDto> findAll() {
        return productDao.selectAll()
                .stream()
                .map(this::toProductResponseDto)
                .collect(Collectors.toList());
    }

    private ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(product);
    }

    public void add(ProductRequestDto productRequestDto) {
        productDao.save(productRequestDto.toEntity());
    }

    public void modifyById(int id, ProductRequestDto productRequestDto) {
        productDao.updateById(id, productRequestDto.toEntity());
    }

    public void removeById(int id) {
        productDao.deleteById(id);
    }

}
