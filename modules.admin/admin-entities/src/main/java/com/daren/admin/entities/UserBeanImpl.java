package com.daren.admin.entities;

import com.google.common.collect.Lists;
import com.daren.core.impl.persistence.PersistentEntity;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * @类描述：用户实体实现类
 * @创建人：sunlf
 * @创建时间：2014-04-26 上午11:10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "sys_user")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class UserBeanImpl extends PersistentEntity {
    @NotNull(message = "登录名不能为空")
    @Max(5)
    private String loginName;// 登录名
    @Size(min = 4, max = 10)
    @NotNull(message = "姓名不能为空")
    private String name;  // 姓名
    @NotNull(message = "密码不能为空")
    @Min(6)
    private String password; // 密码
    @Email
    private String email; // 邮箱
    private String phone;    // 电话
    private String mobile;    // 手机
    private String loginIp;    // 最后登陆IP
    private Date loginDate;    // 最后登陆日期

    private List<RoleBeanImpl> roleList = Lists.newArrayList(); // 拥有角色列表

    public UserBeanImpl() {

    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getLoginName() {
        return loginName;
    }


    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getLoginIp() {
        return loginIp;
    }


    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }


    public Date getLoginDate() {
        return loginDate;
    }


    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OrderBy("id")
    public List<RoleBeanImpl> getRoleList() {
        return roleList;
    }


    public void setRoleList(List<RoleBeanImpl> roleList) {
        this.roleList = roleList;
    }
}
