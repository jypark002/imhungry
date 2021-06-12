package jyrestaurant;

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
        MenuSelected menuSelected = new MenuSelected();
        BeanUtils.copyProperties(this, menuSelected);
        menuSelected.publishAfterCommit();


        MenuCanceled menuCanceled = new MenuCanceled();
        BeanUtils.copyProperties(this, menuCanceled);
        menuCanceled.publishAfterCommit();


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
