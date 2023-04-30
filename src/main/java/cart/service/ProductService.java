package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductAddRequestDto;
import cart.dto.ProductModifyRequestDto;
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

    public void add(ProductAddRequestDto productAddRequestDto) {
        Product product = toEntity(productAddRequestDto);
        productDao.save(product);
    }

    public void modifyById(int id, ProductModifyRequestDto productModifyRequestDto) {
        Product product = toEntity(productModifyRequestDto);
        productDao.updateById(id, product);
    }

    private Product toEntity(ProductAddRequestDto productAddRequestDto) {
        return new Builder()
                .name(productAddRequestDto.getName())
                .price(productAddRequestDto.getPrice())
                .imageUrl(productAddRequestDto.getImageUrl())
                .build();
    }

    private Product toEntity(ProductModifyRequestDto productModifyRequestDto) {
        return new Builder()
                .name(productModifyRequestDto.getName())
                .price(productModifyRequestDto.getPrice())
                .imageUrl(productModifyRequestDto.getImageUrl())
                .build();
    }

    public void removeById(int id) {
        productDao.deleteById(id);
    }

}
