package jyrestaurant.external;

public class Dicision {

    private Long id;
    private String menuType;
    private Long requestId;
    private String status;
    private Integer selectCount;

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
