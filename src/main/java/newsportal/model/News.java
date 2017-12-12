package newsportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
public class News implements Serializable{
    private LocalDateTime publishTime;

    private Integer viewCount;

    @OneToMany(mappedBy = "newsItem")
    private List<View> views;

    @NotEmpty
    @Size(min = 4, max = 100)
    private String title;

    @NotEmpty
    @Size(min = 4, max = 1000)
    private String lead;

    @NotEmpty
    @Size(min = 4, max = 10000)
    private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private ImageFile image;

    @NotEmpty
    @ManyToMany(mappedBy = "newsItems")
    private List<Writer> writers;

    @NotEmpty
    @ManyToMany(mappedBy = "newsItems")
    private List<Category> categories;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormattedPublishTime() {
        return publishTime.getDayOfMonth() + "." + publishTime.getMonthValue() + "." + publishTime.getYear();
    }
}
