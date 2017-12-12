package newsportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Entity
@Data
@NoArgsConstructor
public class ImageFile extends AbstractPersistable<Long>{

    @Lob
    private byte[] content;
}
