package imhungry;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private Long requestId;
    private Long menuId;
    private Long orderId;

    @PostPersist
    public void onPostPersist(){
        // System.out.println("########## PostPersist Start.");
        // System.out.println("########## REQUESTID=" + this.getRequestId());
        // System.out.println("########## STATUS=" + this.getStatus());
        // System.out.println("########## MENUID=" + this.getMenuId());
        // System.out.println("########## ORDERID=" + this.getId());

        if ("SELECTED".equals(this.getStatus())) {
            this.setStatus("ORDERED");
            this.setOrderId(this.getId());
            Ordered ordered = new Ordered();
//            ordered.setStatus("ORDERED");
            BeanUtils.copyProperties(this, ordered);
            ordered.publishAfterCommit();
        }
//        else if ("CANCELED".equals(this.getStatus())) {
//            OrderCanceled orderCanceled = new OrderCanceled();
//            BeanUtils.copyProperties(this, orderCanceled);
//            orderCanceled.publishAfterCommit();
//        }
    }

    @PreUpdate
    public void onPreUpdate() {
        System.out.println("########## PreUpdate Start.");
        System.out.println("########## REQUESTID=" + this.getRequestId());
        System.out.println("########## STATUS=" + this.getStatus());
        System.out.println("########## MENUID=" + this.getMenuId());
        System.out.println("########## ORDERID=" + this.getOrderId());

        if ("CANCELED".equals(this.getStatus())) {
            OrderCanceled orderCanceled = new OrderCanceled();
            BeanUtils.copyProperties(this, orderCanceled);
            orderCanceled.publishAfterCommit();
        }
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
