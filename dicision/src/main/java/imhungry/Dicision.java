package imhungry;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Dicision_table")
public class Dicision {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String menuType;
    private Long requestId;
    private String status;
    private Integer selectCount;

    @PostPersist
    public void onPostPersist(){

        System.out.println("########## PostPersist Start.");
        System.out.println("########## REQUESTID=" + this.getRequestId());
        System.out.println("########## STATUS=" + this.getStatus());
        System.out.println("########## MENUTYPE=" + this.getMenuType());
        System.out.println("########## MENUID=" + this.getId());

        if ("REQUESTED".equals(this.getStatus())) {

            MenuSelected menuSelected = new MenuSelected();
            menuSelected.setStatus("SELECTED");
            BeanUtils.copyProperties(this, menuSelected);
            menuSelected.publishAfterCommit();

            try {
                Thread.currentThread().sleep((long) (400 + Math.random() * 220));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if ("CANCELED".equals(this.getStatus())) {

            MenuCanceled menuCanceled = new MenuCanceled();
            menuCanceled.setRequestId(this.getRequestId());
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSelectCount() {
        return selectCount;
    }
    public void setSelectCount(Integer selectCount) {
        this.selectCount = selectCount;
    }

}
