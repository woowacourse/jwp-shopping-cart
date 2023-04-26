package cart.dao;

import cart.controller.ProductRequest;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao implements CrudDao<ProductEntity, ProductRequest> {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long add(ProductRequest request) {
        String query = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setInt(2, request.getPrice());
            ps.setString(3, request.getImageUrl());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<ProductEntity> findAll() {
        String query = "SELECT * FROM product";
        return jdbcTemplate.query(query, (resultSet, rowNum) ->
            new ProductEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")));
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        String query = "SELECT * FROM product WHERE id = ?";
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, (resultSet, rowNum) ->
                new ProductEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url")), id));
        } catch (EmptyResultDataAccessException exception){
            return Optional.empty();
        }

    }

    @Override
    public int updateById(Long id, ProductRequest request) {
        String query = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        return jdbcTemplate.update(query, request.getName(), request.getPrice(),
            request.getImageUrl(),
            id);
    }

    @Override
    public int deleteById(Long id) {
        String query = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }
}
