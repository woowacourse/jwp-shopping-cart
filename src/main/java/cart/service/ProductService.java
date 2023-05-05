package cart.service;

import cart.domain.Product;
import cart.dto.request.ProductCreateDto;
import cart.dto.response.ProductDto;
import cart.excpetion.ProductionServiceException;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(final ProductCreateDto productCreateDto) {
        final Product product = dtoToDomain(productCreateDto);
        productRepository.create(product);
    }

    public List<ProductDto> findAll() {
        final ArrayList<ProductDto> productDtos = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            productDtos.add(new ProductDto(
                            product.getId(),
                            product.getName(),
                            product.getImage(),
                            product.getPrice()
                    )
            );
        }
        return productDtos;
    }

    public void update(final ProductCreateDto productCreateDto, final int id) {
        final Product requestProduct = dtoToDomain(productCreateDto);
        final Optional<Product> exitingProduct = productRepository.findBy(id);
        if (exitingProduct.isPresent()) {
            productRepository.update(id, requestProduct);
            return;
        }
        throw new ProductionServiceException("존재 하지 않는 상품에 대한 업데이트입니다");
    }

    public void delete(final int id) {
        final Optional<Product> exitingProduct = productRepository.findBy(id);
        if (exitingProduct.isPresent()) {
            productRepository.delete(id);
            return;
        }
        throw new ProductionServiceException("존재 하지 않는 상품에 대한 삭제 입니다");
    }

    private Product dtoToDomain(final ProductCreateDto productCreateDto) {
        try {
            return productCreateDto.toProduct();
        } catch (IllegalArgumentException e) {
            throw new ProductionServiceException(e.getMessage());
        }
    }
}
