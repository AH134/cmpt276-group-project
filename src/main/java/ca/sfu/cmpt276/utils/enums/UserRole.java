package ca.sfu.cmpt276.utils.enums;

public enum UserRole {
    ADMIN(0),
    GROWER(1),
    SPONSOR(2),;

    private final int roleId;

    UserRole(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

}
