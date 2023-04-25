package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.Product;
import cart.domain.ProductRepository;
import cart.dto.ProductDto;
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
        List<Product> products = productRepository.findAll()
            .stream()
            .map(productDto -> Product.createWithId(
                productDto.getId(),
                productDto.getName(),
                productDto.getImgUrl(),
                productDto.getPrice()))
            .collect(Collectors.toList());

        return products.stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    public void add(ProductRequestDto productRequestDto) {
        Product product = Product.createWithoutId(
            productRequestDto.getName(),
            productRequestDto.getImgUrl(),
            productRequestDto.getPrice()
        );
        productRepository.save(new ProductDto(product));
    }

    public void updateById(ProductRequestDto productRequestDto, Long id) {
        Product product = Product.createWithoutId(
            productRequestDto.getName(),
            productRequestDto.getImgUrl(),
            productRequestDto.getPrice()
        );
        productRepository.updateById(new ProductDto(product), id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
