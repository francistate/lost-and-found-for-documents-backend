package tech.ftchekure.documentslostandfound.entities.items;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.val;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Data
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreated;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateLastUpdated;

    @Version
    private Integer version;

    @MapKeyJoinColumn(name = "item_attribute_id")
    @ElementCollection(fetch = FetchType.EAGER)
    @JsonSerialize(converter = AttributeValuesConverter.class)
    private Map<ItemAttribute, String> attributeValues;

    @ManyToOne
    @JoinColumn(name = "item_type_id", referencedColumnName = "id", nullable = false)
    private ItemType itemType;

    private String description;


    public static Item createItem(Map<ItemAttribute, String> attributeValues, ItemType itemType, String description) {

        val item = new Item();
        item.setAttributeValues(attributeValues);
        item.setItemType(itemType);
        item.setDescription(description);

        return item;

    }

}
