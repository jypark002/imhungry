package imhungry;

public class Requested extends AbstractEvent {

    private Long id;
    private String status;
    private String memuType;

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
    public String getMemuType() {
        return memuType;
    }

    public void setMemuType(String memuType) {
        this.memuType = memuType;
    }
}