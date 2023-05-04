package cart.service;

import cart.dto.request.ProductCreateDto;
import cart.dto.response.ProductDto;
import cart.entity.ProductEntity;
import cart.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public int create(final ProductCreateDto productCreateDto) {
        return productDao.create(dtoToEntity(productCreateDto));
    }

    public List<ProductDto> findAll() {
        final ArrayList<ProductDto> productDtos = new ArrayList<>();
        for (ProductEntity productEntity : productDao.findAll()) {
            productDtos.add(new ProductDto(
                            productEntity.getId(),
                            productEntity.getName(),
                            productEntity.getImage(),
                            productEntity.getPrice()
                    )
            );
        }
        return productDtos;
    }

    public void update(final ProductCreateDto productCreateDto, final int id) {
        productDao.update(productCreateDto, id);
    }

    public void delete(final int id) {
        productDao.delete(id);
    }

    private ProductEntity dtoToEntity(final ProductCreateDto productCreateDto) {
        return new ProductEntity(productCreateDto.getName(),
                productCreateDto.getImage(),
                productCreateDto.getPrice());
    }
}
