package org.ruyue.basic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @program: basic-hibernate
 * @description: 简单测试，在test中不会被打包
 * @author: Ruyue Bian
 * @create: 2019-05-14 01:15
 */
@Entity
@Table(name = "t_user")
public class User {
    private int id;
    private String username;

    public User(){
    }
    /*带参数的构造方法*/
    public User(int id, String username) {
        super();
        this.id = id;
        this.username = username;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
