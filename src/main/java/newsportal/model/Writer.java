package newsportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Writer extends AbstractPersistable<Long>{

    @NotEmpty
    @Size(min = 3, max = 20)
    @Column(unique = true)
    private String name;

    @NotEmpty
    @Size(min = 5, max = 100)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<News> newsItems;
}
