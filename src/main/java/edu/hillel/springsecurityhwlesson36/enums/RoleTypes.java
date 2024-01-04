package edu.hillel.springsecurityhwlesson36.enums;

public enum RoleTypes {

    ROLE_ADMIN(1L, "ROLE_ADMIN"),
    ROLE_USER(2L, "ROLE_USER");

    private final Long roleId;
    private final String roleType;

    RoleTypes(Long roleId, String roleType) {
        this.roleId = roleId;
        this.roleType = roleType;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getRole() {
        return roleType;
    }
}
