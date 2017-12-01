package newsportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Data(staticConstructor="of")
public class News implements Serializable{
    private String title;
    private String lead;
    private String content;
    private LocalDateTime publishTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private ImageFile image;

    @ManyToMany(mappedBy = "newsItems")
    private List<User> writers;

    @ManyToMany(mappedBy = "newsItems")
    private List<Category> categories;

    public void setTitle(String title) {
        this.title = title;
    }
}
