package imhungry;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Request_table")
public class Request {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private String menuType;
    private Long requestId;
    private Long menuId;
    private Long orderId;

    @PrePersist
    public void onPrePersist() {
        System.out.println("########## PrePersist Start.");
        System.out.println("########## ID=" + this.getId());
        System.out.println("########## STATUS=" + this.getStatus());
        System.out.println("########## MENUTYPE=" + this.getMenuType());
        System.out.println("########## REQUESTID=" + this.getRequestId());
        System.out.println("########## MENUID=" + this.getMenuId());
        System.out.println("########## ORDERID=" + this.getOrderId());
//        if (this.getId().equals(null)) this.setId(1L);

    }

    @PostPersist
    public void onPostPersist(){
        System.out.println("########## PostPersist Start.");
        System.out.println("########## ID=" + this.getId());
        System.out.println("########## STATUS=" + this.getStatus());
        System.out.println("########## MENUTYPE=" + this.getMenuType());
        System.out.println("########## REQUESTID=" + this.getRequestId());
        System.out.println("########## MENUID=" + this.getMenuId());
        System.out.println("########## ORDERID=" + this.getOrderId());

        if ("REQUESTED".equals(this.getStatus())) {
            imhungry.external.Dicision dicision = new imhungry.external.Dicision();
            dicision.setId(this.getId());
            dicision.setStatus(this.getStatus());
            dicision.setMenuType(this.getMenuType());
            dicision.setRequestId(this.getId());

            RequestApplication.applicationContext.getBean(imhungry.external.DicisionService.class)
                    .menuSelect(this.getId(), this.getMenuType());

            Requested requested = new Requested();
            requested.setId(this.getId());
            requested.setStatus(this.getStatus());
            requested.setMenuType(this.getMenuType());
            requested.setRequestId(this.getId());
//            BeanUtils.copyProperties(this, requested);
            requested.publishAfterCommit();
        }
//        else if ("CANCELED".equals(this.getStatus())) {
//            RequestCanceled requestCanceled = new RequestCanceled();
//            BeanUtils.copyProperties(this, requestCanceled);
//            requestCanceled.publishAfterCommit();
//        }
        System.out.println("########## PostPersist End.");
    }

    @PreUpdate
    public void onPreUpdate() {
        System.out.println("########## PreUpdate Start.");
        System.out.println("########## STATUS=" + this.getStatus());
        System.out.println("########## MENUTYPE=" + this.getMenuType());
        System.out.println("########## REQUESTID=" + this.getId());
        System.out.println("########## MENUID=" + this.getMenuId());
        System.out.println("########## ORDERID=" + this.getOrderId());

        if ("CANCELED".equals(this.getStatus())) {
//            Request request =
            RequestCanceled requestCanceled = new RequestCanceled();
            BeanUtils.copyProperties(this, requestCanceled);
            requestCanceled.publishAfterCommit();
        }
        System.out.println("########## PostUpdate End.");
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

    public String getMenuType() {
        return menuType;
    }
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Long getRequestId() {
        return requestId;
    }
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getMenuId() {
        return menuId;
    }
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
