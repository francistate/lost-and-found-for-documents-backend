package tech.ftchekure.documentslostandfound.entities.items;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tech.ftchekure.documentslostandfound.entities.user.Station;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "found_items")
public class FoundItem {

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

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private Station station;

}
