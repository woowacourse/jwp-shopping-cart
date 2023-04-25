package cart.dao;

import cart.dto.ProductRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductDao {

    private final SimpleJdbcInsert insertActor;
    private final JdbcTemplate jdbcTemplate;


    public ProductDao(DataSource dataSource) {
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(ProductRequest productRequest){
        String sql = "insert into Product(name, price, url) values (?,?,?)";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", productRequest.getName());
        parameters.put("price", productRequest.getPrice());
        parameters.put("url", productRequest.getImageUrl());

        insertActor.execute(parameters);

    }
}
