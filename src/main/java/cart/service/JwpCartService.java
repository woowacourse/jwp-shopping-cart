package cart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.ProductRepository;
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
        return productRepository.findAll();
    }

    public void add(ProductRequestDto productRequestDto) {
        productRepository.save(productRequestDto);
    }

    public void updateById(ProductRequestDto productRequestDto, Long id) {
        productRepository.updateById(productRequestDto, id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
