package com.css.app.qxjgl.business.entity;

public class ZFDicUsersModel {

    //
    private String id;

    //
    private String userid;

    //
    private String username;

    //
    private String deptid;

    //
    private String deptname;

    //
    private String rolecode;

    //
    private String rolename;

    //
    private String orgId;

    //
    private String orgName;

    //操作类型 2部门 3局
    private Integer type;
    private String ids;
    private Integer page;
    private Integer pageSize;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getDeptid() {
        return deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public String getRolecode() {
        return rolecode;
    }

    public String getRolename() {
        return rolename;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }
}