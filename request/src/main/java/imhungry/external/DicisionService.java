package imhungry.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

// @FeignClient(name="dicision", url="http://dicision:8080")
//@FeignClient(name="dicision", url="http://localhost:8081")
@FeignClient(name="dicision", url="${api.url.dicision}")
public interface DicisionService {

    @RequestMapping(method= RequestMethod.POST, path="/dicisions/menuSelect")
    public void menuSelect(@RequestParam("requestId") Long requestId,
                           @RequestParam("menuType") String menuType);

}