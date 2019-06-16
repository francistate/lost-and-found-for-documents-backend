package tech.ftchekure.documentslostandfound.daos.items;

import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class ItemAttributeValuesRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ItemAttributeValuesRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Collection<ItemAttributeValue> search(String attributeValue) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("itemAttributeValue", "%" + attributeValue + "%");

        val query = "select * from docs_lost_and_found_test.item_attribute_values where attribute_values like :itemAttributeValue";

        return namedParameterJdbcTemplate.query(query, namedParameters, new ItemAttributeValueRowMapper());

    }

    class ItemAttributeValueRowMapper implements RowMapper<ItemAttributeValue> {

        @Override
        public ItemAttributeValue mapRow(ResultSet resultSet, int i) throws SQLException {

            val itemAttributeValue = new ItemAttributeValue();
            itemAttributeValue.setItemAttributeId(resultSet.getLong("item_attribute_id"));
            itemAttributeValue.setItemAttributeValue(resultSet.getString("attribute_values"));
            itemAttributeValue.setItemId(resultSet.getLong("item_id"));
            return itemAttributeValue;

        }
    }

}
