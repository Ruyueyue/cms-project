package org.ruyue.cms.model;


import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @program: cms-core
 * @description: 用户基本属性
 * @author: Ruyue Bian
 * @create: 2019-05-16 15:08
 */
//Table注释指定了Entity所要映射带数据库表，其中@Table.name()用来指定映射表的表名。
@Entity
@Table(name = "t_user")
public class User {
    private int id;
    private String username;   //用户登录名称
    private String password;
    private String nickname;   //用户的中文名称
    private String email;
    private String phone;
    private int status;        //用户的状态:0表示停用,1表示启用
    private Date createDate;   //创建时间

    public User(int id, String username, String password, String nickname, String email, String phone, int status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public User(){

    }

    @Id    //表示是一个主键
    @GeneratedValue    //标注主键的生成策略,为一个实体生成一个唯一标识的主键
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull(message = "用户名不能为空")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "用户密码不能为空")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Email(message = "邮件格式不正确")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "create_date")    //定义了被标注字段在数据库表中所对应字段的名称；
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
