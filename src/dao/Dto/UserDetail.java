package dao.Dto;

public class UserDetail {
    private String userName;
    private String id;
    private String role;

    public UserDetail(String userName, String id,String role) {
        this.userName = userName;
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}