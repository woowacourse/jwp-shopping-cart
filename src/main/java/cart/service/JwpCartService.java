package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.entity.Product;
import cart.entity.ProductRepository;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;

@Service
@Transactional(readOnly = true)
public class JwpCartService {

    private final ProductRepository productRepository;

    public JwpCartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll()
            .stream()
            .map(productDto -> Product.of(
                productDto.getId(),
                productDto.getName(),
                productDto.getImgUrl(),
                productDto.getPrice()))
            .map(ProductResponseDto::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void add(ProductRequestDto productRequestDto) {
        Product product = Product.of(
            null,
            productRequestDto.getName(),
            productRequestDto.getImgUrl(),
            productRequestDto.getPrice()
        );
        productRepository.save(product);
    }

    @Transactional
    public void updateById(ProductRequestDto productRequestDto, Long id) {
        Product product = Product.of(
            null,
            productRequestDto.getName(),
            productRequestDto.getImgUrl(),
            productRequestDto.getPrice()
        );
        productRepository.updateById(product, id);
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
