package org.ruyue.cms.model;

import javax.persistence.*;

/**
 * @program: cms-core
 * @description: 用户角色对象
 * @author: Ruyue Bian
 * @create: 2019-05-16 16:11
 */
@Entity
@Table(name = "t_user_role")
public class UserRole {
    private int id;
    private User user;
    private Role role;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*与@Column用法相同，区别是@JoinColumn作用的属性必须是实体类*/
    @ManyToOne
    @JoinColumn(name = "u_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "r_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
