package imhungry;

public class Requested extends AbstractEvent {

    private Long id;
    private String status;
    private String menuType;
    private Long requestId;

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
    public void setRequestId(Long id) {
        this.requestId = requestId;
    }
}