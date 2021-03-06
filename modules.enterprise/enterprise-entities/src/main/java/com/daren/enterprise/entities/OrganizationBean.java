package com.daren.enterprise.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @类描述：监管部门实体类
 * @创建人：sunlf
 * @创建时间：2014-05-14 下午1:49
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "urg_ent_Organization")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class OrganizationBean extends PersistentEntity {
    @NotNull(message = "'机关代码'是必填项")
    private String jgdm;//业务主键 机关代码
    private String sjjgdm;//上级机关代码
    @NotNull(message = "'机关名称'是必填项")
    private String mc;//机关名称
    private String mcj;//机关简称
    @NotNull(message = "'作废标记'是必选项")
    private String zfbj;//作废标记，0 有效，1 作废
    @NotNull(message = "'机关部门标记'是必选项")
    private String jgbmbj;//机关部门标记 ，‘1’为部门，‘0’为机关';
    @NotNull(message = "'机构类型标记'是必选项")
    private String jglxbj;//机构类型标记，1 综合，2 行业，5 消防 6 质检 7公安
    private String createtime;//创建时间
    private String xzqh_dm;//区划代码

    public OrganizationBean(){
        this.jgdm = "";
        this.sjjgdm = "";
        this.mc = "";
        this.mcj = "";
        this.zfbj = "";
        this.jgbmbj = "";
        this.jglxbj = "";
        this.createtime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.xzqh_dm = "";
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }
    @XmlTransient
    public String getSjjgdm() {
        return sjjgdm;
    }

    public void setSjjgdm(String sjjgdm) {
        this.sjjgdm = sjjgdm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
    @XmlTransient
    public String getMcj() {
        return mcj;
    }

    public void setMcj(String mcj) {
        this.mcj = mcj;
    }
    @XmlTransient
    public String getZfbj() {
        return zfbj;
    }

    public void setZfbj(String zfbj) {
        this.zfbj = zfbj;
    }
    @XmlTransient
    public String getJgbmbj() {
        return jgbmbj;
    }

    public void setJgbmbj(String jgbmbj) {
        this.jgbmbj = jgbmbj;
    }
    @XmlTransient
    public String getJglxbj() {
        return jglxbj;
    }

    public void setJglxbj(String jglxbj) {
        this.jglxbj = jglxbj;
    }
    @XmlTransient
    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    @XmlTransient
    public String getXzqh_dm() {
        return xzqh_dm;
    }

    public void setXzqh_dm(String xzqh_dm) {
        this.xzqh_dm = xzqh_dm;
    }
}
