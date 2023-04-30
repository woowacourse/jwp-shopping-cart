package cart.service;

import cart.domain.Product;
import cart.domain.ProductRepository;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class JwpCartService {

    private final ProductRepository productRepository;

    public JwpCartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll() {
        List<ProductDto> productDtos = productRepository.findAll();
        productDtos.stream()
                .map(productDto -> Product.createWithId(
                        productDto.getId(),
                        productDto.getName(),
                        productDto.getImgUrl(),
                        productDto.getPrice()))
                .collect(toList());

        return productDtos.stream()
                .map(ProductResponseDto::fromProductDto)
                .collect(toList());
    }

    @Transactional
    public ProductResponseDto add(ProductRequestDto productRequestDto) {
        Product product = Product.createWithoutId(
                productRequestDto.getName(),
                productRequestDto.getImgUrl(),
                productRequestDto.getPrice()
        );

        ProductDto productDto = new ProductDto(product);
        productRepository.save(productDto);
        return ProductResponseDto.fromProductDto(productDto);
    }

    @Transactional
    public ProductResponseDto updateById(ProductRequestDto productRequestDto, Long id) {
        Product product = Product.createWithoutId(
                productRequestDto.getName(),
                productRequestDto.getImgUrl(),
                productRequestDto.getPrice()
        );
        ProductDto productDto = new ProductDto(product);
        productRepository.updateById(productDto, id);
        return ProductResponseDto.fromProductDto(productDto);
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
