package imhungry;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name="Dicision_table")
public class Dicision {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private String menuType;
    private Long requestId;
    private Long menuId;

    @PrePersist
    public void onPrePersist() {
        System.out.println("########## PrePersist Start.");
        System.out.println("########## STATUS=" + this.getStatus());
        System.out.println("########## MENUTYPE=" + this.getMenuType());
        System.out.println("########## REQUESTID=" + this.getRequestId());
        System.out.println("########## MENUID=" + this.getMenuId());

        try {
            Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PostPersist
    public void onPostPersist() {
        System.out.println("########## PostPersist Start.");
        System.out.println("########## STATUS=" + this.getStatus());
        System.out.println("########## MENUTYPE=" + this.getMenuType());
        System.out.println("########## REQUESTID=" + this.getRequestId());
        System.out.println("########## MENUID=" + this.getMenuId());

        if ("SELECTED".equals(this.getStatus())) {
            MenuSelected menuSelected = new MenuSelected();
            BeanUtils.copyProperties(this, menuSelected);
            menuSelected.publishAfterCommit();
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        System.out.println("########## PreUpdate Start.");
        System.out.println("########## STATUS=" + this.getStatus());
        System.out.println("########## MENUTYPE=" + this.getMenuType());
        System.out.println("########## REQUESTID=" + this.getRequestId());
        System.out.println("########## MENUID=" + this.getMenuId());

        if ("CANCELED".equals(this.getStatus())) {
            MenuCanceled menuCanceled = new MenuCanceled();
            BeanUtils.copyProperties(this, menuCanceled);
            menuCanceled.publishAfterCommit();
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
}
