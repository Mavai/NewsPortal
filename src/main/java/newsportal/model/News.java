package newsportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class News extends AbstractPersistable<Long>{
    private String title;
    private String lead;
    private String content;
    private LocalDateTime publishTime;

    @OneToOne
    private ImageFile image;

    @ManyToMany(mappedBy = "newsPosts")
    private List<User> writers;

    @ManyToMany(mappedBy = "newsPosts")
    private List<Category> categories;
}
