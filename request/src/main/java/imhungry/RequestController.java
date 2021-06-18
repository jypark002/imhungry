package imhungry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class RequestController {

//    @Autowired
//    RequestRepository requestRepository;
//
//    @RequestMapping(value = "/requests/request",
//            method = RequestMethod.POST,
//            produces = "application/json;charset=UTF-8")
//    public void request(HttpServletRequest request, HttpServletResponse response) {
//
//        String status = request.getParameter("status");
//        String menuType = request.getParameter("menuType");
//
//        Request requestInfo = new Request();
//        System.out.println("########## Request /requests/request " + status + ":" + menuType + ":" + requestInfo.getId());
//        requestInfo.setStatus(status);
//        requestInfo.setMenuType(menuType);
//        requestInfo.setRequestId(requestInfo.getId());
//    }
}
