package newsportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User extends AbstractPersistable<Long>{
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<News> newsItems;
}
