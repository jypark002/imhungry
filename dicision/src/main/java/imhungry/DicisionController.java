package imhungry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

@RestController
public class DicisionController {

    @Autowired
    DicisionRepository dicisionRepository;

    @RequestMapping(value = "/dicisions/menuSelect",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public boolean menuSelect(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("########### Request /dicisions/menuSelect");

        Long requestId = Long.valueOf(request.getParameter("requestId"));
        String menuType = request.getParameter("menuType");
        if (menuType.isEmpty()) return false;

        Dicision dicision = new Dicision();
        dicision.setStatus("SELECTED");
        dicision.setMenuType(request.getParameter("menuType"));
        dicision.setRequestId(requestId);
        dicision.setMenuId(new Random().nextLong());
        dicisionRepository.save(dicision);

        System.out.println("########### Response /dicisions/menuSelect [true]");
        return true;
    }

}
