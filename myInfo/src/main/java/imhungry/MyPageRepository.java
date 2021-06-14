package imhungry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyPageRepository extends CrudRepository<MyPage, Long> {

    List<MyPage> findByRequestId(Long requestId);
//    List<MyPage> findByRequestId(Long requestId);
//    List<MyPage> findByRequestId(Long requestId);

}