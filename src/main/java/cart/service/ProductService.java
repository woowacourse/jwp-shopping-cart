package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
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

    public void add(ProductDto productDto) {
        productDao.save(productDto.toEntity());
    }

    public void modifyById(Long id, ProductDto productDto) {
        int numberOfUpdateRow = productDao.updateById(id, productDto.toEntity());

        if (numberOfUpdateRow == 0) {
            throw new IllegalStateException("변경하려는 상품이 존재하지 않습니다.");
        }
    }

    public void removeById(Long id) {
        int numberOfDeleteRow = productDao.deleteById(id);

        if (numberOfDeleteRow == 0) {
            throw new IllegalStateException("삭제하려는 상품이 존재하지 않습니다.");
        }
    }

}
