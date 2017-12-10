package newsportal.repository;

import newsportal.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Long>{

    Writer findByName(String name);
}
