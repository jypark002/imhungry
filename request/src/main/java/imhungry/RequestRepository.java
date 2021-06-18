package imhungry;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel="requests", path="requests")
public interface RequestRepository extends PagingAndSortingRepository<Request, Long>{

//    List<Request> findAllByIdExists();
}
