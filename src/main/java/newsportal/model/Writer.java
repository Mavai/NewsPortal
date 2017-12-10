package newsportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Writer extends AbstractPersistable<Long>{

    @Column(unique = true)
    private String name;
    
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<News> newsItems;
}
