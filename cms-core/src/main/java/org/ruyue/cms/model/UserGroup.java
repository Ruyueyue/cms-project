package org.ruyue.cms.model;

import javax.persistence.*;

/**
 * @program: cms-core
 * @description: 用户组对象, 存储用户和组的关联
 * @author: Ruyue Bian
 * @create: 2019-05-16 23:20
 */
@Entity
@Table(name = "t_user_group")
public class UserGroup {
    private int id;
    private User user;
    private Group group;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "u_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "g_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
