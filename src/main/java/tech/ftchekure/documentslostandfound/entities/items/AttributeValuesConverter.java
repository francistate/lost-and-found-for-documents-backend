package tech.ftchekure.documentslostandfound.entities.items;

import com.fasterxml.jackson.databind.util.StdConverter;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

public class AttributeValuesConverter extends StdConverter<Map<ItemAttribute, String>, Map<String, String>> {

    @Override
    public Map<String, String> convert(Map<ItemAttribute, String> value) {
        val resultMap = new HashMap<String, String>();
        value.forEach((key, value1) -> resultMap.put(key.getName(), value1));
        return resultMap;
    }
}
