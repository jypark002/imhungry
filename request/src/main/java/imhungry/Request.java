package imhungry;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Request_table")
public class Request {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private Long menuId;
    private String menuType;
    private Long orderId;

    @PrePersist
    public void onPrePersist() {

    }

    @PostPersist
    public void onPostPersist(){
        System.out.println("########## PostPersist Start.");
        System.out.println("########## ID=" + this.getId());
        System.out.println("########## STATUS=" + this.getStatus());
        System.out.println("########## MENUTYPE=" + this.getMenuType());
        System.out.println("########## MENUID=" + this.getMenuId());
        System.out.println("########## ORDERID=" + this.getOrderId());

        if ("REQUESTED".equals(this.getStatus())) {
            Requested requested = new Requested();
            BeanUtils.copyProperties(this, requested);
            requested.publishAfterCommit();

            imhungry.external.Dicision dicision = new imhungry.external.Dicision();
            // mappings goes here
            RequestApplication.applicationContext.getBean(imhungry.external.DicisionService.class)
                    .menuSelect(dicision);
        }
        else if ("CANCELED".equals(this.getStatus())) {
            RequestCanceled requestCanceled = new RequestCanceled();
            BeanUtils.copyProperties(this, requestCanceled);
            requestCanceled.publishAfterCommit();
        }
        System.out.println("########## PostPersist End.");
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMenuId() {
        return menuId;
    }
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuType() {
        return menuType;
    }
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
