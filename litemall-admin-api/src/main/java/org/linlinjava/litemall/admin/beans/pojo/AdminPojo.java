package org.linlinjava.litemall.admin.beans.pojo;

import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallRole;

import java.util.List;

public class AdminPojo {
    private LitemallAdmin litemallAdmin;
    private List<LitemallRole> litemallRoles;

    public LitemallAdmin getLitemallAdmin() {
        return litemallAdmin;
    }

    public void setLitemallAdmin(LitemallAdmin litemallAdmin) {
        this.litemallAdmin = litemallAdmin;
    }

    public List<LitemallRole> getLitemallRoles() {
        return litemallRoles;
    }

    public void setLitemallRoles(List<LitemallRole> litemallRoles) {
        this.litemallRoles = litemallRoles;
    }

    @Override
    public String toString() {
        return "AdminPojo{" +
                "litemallAdmin=" + litemallAdmin +
                ", litemallRoles=" + litemallRoles +
                '}';
    }
}
