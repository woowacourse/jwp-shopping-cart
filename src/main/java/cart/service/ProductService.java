package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductAddRequestDto;
import cart.dto.ProductModifyRequestDto;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

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

    public void add(ProductAddRequestDto productAddRequestDto) {
        Product product = productAddRequestDto.toEntity();
        productDao.save(product);
    }

    public void modifyById(int id, ProductModifyRequestDto productModifyRequestDto) {
        findProductById(id);
        Product product = productModifyRequestDto.toEntity();
        productDao.updateById(id, product);
    }

    public void removeById(int id) {
        findProductById(id);
        productDao.deleteById(id);
    }

    private void findProductById(int id) {
        productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 id 입니다."));
    }

}
