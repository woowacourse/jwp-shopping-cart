package cart.catalog.service;

import cart.catalog.dao.CatalogDAO;
import cart.catalog.domain.Product;
import cart.catalog.dto.ProductRequestDTO;
import cart.catalog.dto.ProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final CatalogDAO catalogDao;

    public CatalogService(final CatalogDAO catalogDao) {
        this.catalogDao = catalogDao;
    }

    public List<ProductResponseDTO> display() {
        final List<Product> products = this.catalogDao.findAll();
        return products.stream()
                .map(ProductResponseDTO::create)
                .collect(Collectors.toList());
    }

    public void create(final ProductRequestDTO productRequestDTO) {
        final Product product = Product.create(productRequestDTO);
        this.catalogDao.insert(product);
    }

    public ProductResponseDTO update(final long id, final ProductRequestDTO productRequestDTO) {
        final Product originalProduct = this.catalogDao.findByID(id);
        final Product updatedProduct = originalProduct.update(productRequestDTO);
        this.catalogDao.update(updatedProduct);
        return ProductResponseDTO.create(updatedProduct);
    }

    public void delete(final long id) {
        this.catalogDao.deleteByID(id);
    }
}
