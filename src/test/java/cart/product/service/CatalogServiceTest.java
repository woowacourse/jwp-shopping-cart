package cart.product.service;

import cart.catalog.dao.CatalogDAO;
import cart.catalog.dto.ProductResponseDTO;
import cart.catalog.service.CatalogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CatalogServiceTest {

    @Test
    @DisplayName("display 테스트")
    void display() {
        final CatalogDAO fakeProductDao = new FakeCatalogDAO();
        final CatalogService catalogService = new CatalogService(fakeProductDao);

        final List<ProductResponseDTO> responseDtos = catalogService.display();

        assertEquals(responseDtos.get(0).getName(), "망고");
        assertEquals(responseDtos.get(0).getImage(), "http://mango");
        assertEquals(responseDtos.get(0).getPrice(), 1000);
        assertEquals(responseDtos.get(1).getName(), "에코");
        assertEquals(responseDtos.get(1).getImage(), "http://echo");
        assertEquals(responseDtos.get(1).getPrice(), 2000);
    }
}