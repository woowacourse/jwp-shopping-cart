package cart.controller;

import cart.dto.InsertRequestDto;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CartApiController {

    private final SimpleJdbcInsert insertProducts;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartApiController(JdbcTemplate jdbcTemplate) {
        this.insertProducts = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("product")
    public void insertProduct(@RequestBody InsertRequestDto insertRequestDto) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", insertRequestDto.getName());
        parameters.put("price", insertRequestDto.getPrice());
        parameters.put("image", insertRequestDto.getImage());
        insertProducts.execute(parameters);
    }
}
