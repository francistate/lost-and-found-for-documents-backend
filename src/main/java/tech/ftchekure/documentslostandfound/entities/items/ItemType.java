package tech.ftchekure.documentslostandfound.entities.items;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "item_types")
@Data
public class ItemType {

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

    @Column(unique = true)
    private String name;

    @JoinTable(name = "item_type_item_attributes", joinColumns = @JoinColumn(name = "item_type_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_attribute_id", referencedColumnName = "id"))
    @OneToMany(fetch = FetchType.EAGER)
    private Collection<ItemAttribute> itemAttributes;

}
