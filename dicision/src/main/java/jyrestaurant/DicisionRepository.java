package jyrestaurant;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="dicisions", path="dicisions")
public interface DicisionRepository extends PagingAndSortingRepository<Dicision, Long>{


}
