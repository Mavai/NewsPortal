package newsportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Category extends AbstractPersistable<Long>{
    private String name;

    @ManyToMany
    private List<News> newsPosts;
}
