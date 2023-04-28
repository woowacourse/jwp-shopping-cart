package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
import cart.entity.Product.Builder;
import java.util.List;
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

    public void add(ProductRequestDto productRequestDto) {
        Product product = toEntity(productRequestDto);
        productDao.save(product);
    }

    public void modifyById(int id, ProductRequestDto productRequestDto) {
        Product product = toEntity(productRequestDto);
        productDao.updateById(id, product);
    }

    private Product toEntity(ProductRequestDto productRequestDto) {
        return new Builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .imageUrl(productRequestDto.getImageUrl())
                .build();
    }

    public void removeById(int id) {
        productDao.deleteById(id);
    }

}
