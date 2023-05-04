package cart.dao;

import cart.dao.entity.MemberEntity;
import cart.dao.entity.ProductEntity;
import org.springframework.jdbc.core.RowMapper;

public class ObjectMapper {

    public static RowMapper<ProductEntity> getProductRowMapper() {
        return (resultSet, rowNum) -> new ProductEntity.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getInt("price"))
                .image(resultSet.getString("image"))
                .build();
    }

    public static RowMapper<MemberEntity> getMemberRowMapper() {
        return (resultSet, rowNum) -> new MemberEntity.Builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .build();
    }
}
