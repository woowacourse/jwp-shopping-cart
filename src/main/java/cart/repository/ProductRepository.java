package cart.repository;

import cart.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    private static final Map<Long, Product> products = new LinkedHashMap<>();
    private static Long id;
    
    static {
        products.put(1L, new Product(1L, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 1_000_000_000));
        products.put(2L, new Product(2L, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 1_000_000_000));
        id = initId();
    }
    
    private static long initId() {
        return getCurrentMaxId() + 1L;
    }
    
    private static Long getCurrentMaxId() {
        return products.keySet().stream()
                .max(Long::compareTo)
                .orElse(1L);
    }
    
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
