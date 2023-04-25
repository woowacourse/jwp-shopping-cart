package repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import domain.ProductRepository;
import dto.ProductRequestDto;

@Repository
public class DBProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(ProductRequestDto productRequestDto) {
        String sql = "INSERT INTO product (name, imgURL, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setString(1, productRequestDto.getName());
            preparedStatement.setString(2, productRequestDto.getImgURL());
            preparedStatement.setInt(3, productRequestDto.getPrice());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public ProductRequestDto findById(Long id) {
        String sql = "SELECT name, imgURL, price FROM product WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
            (resultSet, rowNum) -> new ProductRequestDto(
                resultSet.getString("name"),
                resultSet.getString("imgURL"),
                resultSet.getInt("price")),
            id);
    }

    @Override
    public List<ProductRequestDto> findAll() {
        String sql = "SELECT name, imgURL, price FROM product";

        return jdbcTemplate.query(sql,
            (resultSet, rowNum) -> new ProductRequestDto(
                resultSet.getString("name"),
                resultSet.getString("imgURL"),
                resultSet.getInt("price")));
    }

    @Override
    public void updateById(ProductRequestDto productRequestDto, Long id) {
        String sql = "UPDATE product SET name = ?, imgURL = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, productRequestDto.getName(), productRequestDto.getImgURL(), productRequestDto.getPrice(), id);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
