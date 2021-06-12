
package jyrestaurant.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="dicision", url="http://dicision:8080")
public interface DicisionService {

    @RequestMapping(method= RequestMethod.GET, path="/dicisions")
    public void menuSelect(@RequestBody Dicision dicision);

}