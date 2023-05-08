package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductAddRequestDto;
import cart.dto.ProductDto;
import cart.dto.ProductModifyRequestDto;
import cart.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int save(ProductAddRequestDto productAddRequestDto) {
        return productDao.save(new ProductEntity(productAddRequestDto.getName(), productAddRequestDto.getImgUrl(), productAddRequestDto.getPrice()));
    }

    public List<ProductDto> findAll() {
        return productDao.findAll()
                .stream()
                .map(it -> new ProductDto(it.getId(), it.getName(), it.getImgUrl(), it.getPrice()))
                .collect(Collectors.toList());
    }

    public void update(ProductModifyRequestDto productModifyRequestDto, int id) {
        productDao.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 ID가 없습니다."));
        productDao.update(new ProductEntity(
                id,
                productModifyRequestDto.getName(),
                productModifyRequestDto.getImgUrl(),
                productModifyRequestDto.getPrice()));
    }

    public void delete(int id) {
        productDao.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 ID가 없습니다."));
        productDao.delete(id);
    }
}
