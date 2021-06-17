package imhungry;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel="dicisions", path="dicisions")
public interface DicisionRepository extends PagingAndSortingRepository<Dicision, Long>{

    Dicision findByRequestId(Long requestId);

}
