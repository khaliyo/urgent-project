package com.daren.enterprise.core.biz;

import com.daren.admin.api.biz.IAboutBeanService;
import com.daren.admin.api.dao.IAboutBeanDao;
import com.daren.admin.entities.AboutBean;
import com.daren.core.api.ErrorCodeValue;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.enterprise.api.biz.IOrganizationBeanService;
import com.daren.enterprise.api.dao.IOrganizationBeanDao;
import com.daren.enterprise.core.model.OrgnizationListModel;
import com.daren.enterprise.core.util.OrgnizationConst;
import com.daren.enterprise.entities.OrganizationBean;
import com.sun.org.apache.bcel.internal.generic.ICONST;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监管机构业务逻辑实现类
 * Created by xukexin on 2014/8/26.
 */
public class OrganizationBeanServiceImpl extends GenericBizServiceImpl implements IOrganizationBeanService {

    private IOrganizationBeanDao organizationBeanDao;
    private IAboutBeanDao aboutBeanDao;

    public void setOrganizationBeanDao(IOrganizationBeanDao organizationBeanDao) {
        this.organizationBeanDao = organizationBeanDao;
        super.init(organizationBeanDao, OrganizationBean.class.getName());
    }

    public void setAboutBeanDao(IAboutBeanDao aboutBeanDao) {
        this.aboutBeanDao = aboutBeanDao;
    }

    @Override
    public List<OrganizationBean> queryOrganization(OrganizationBean organizationBean) {
        return organizationBeanDao.find("select a from OrganizationBean a where a.mc LIKE ?1", "%" + organizationBean.getMc() + "%");
    }

    @Override
    public Map<String, String> getAllBeansToHashMap() {
        Map<String, String> hashMap = new HashMap<>();
        List<OrganizationBean> list = organizationBeanDao.getAll(OrganizationBean.class.getName());
        if (null != list && list.size() > 0) {
            for (OrganizationBean bean : list) {
                hashMap.put(bean.getJgdm() + "", bean.getMc());
            }
        }
        return hashMap;
    }

    /**
     * 按名称模糊查询监管机构（带分页）
     * @param term
     * @param page
     * @param page_size
     * @return
     */
    @Override
    public List<OrganizationBean> findByName(String term, int page, int page_size) {
        AboutBean aboutBean = aboutBeanDao.getAll(AboutBean.class.getName()).get(0);
        /*AboutBean aboutBean = new AboutBean();
        try{
            aboutBean = aboutBeanDao.get(AboutBean.class.getName(),1L);
        }catch(Exception e){
            e.printStackTrace();
        }*/
        return organizationBeanDao.findbyPage(
                "select t from OrganizationBean t where t.xzqh_dm like '"+aboutBean.getXzqh_dm()+"%' and t.mc like '%"+term+"%'",page,page_size);
    }
    /**
     * 通过机构代码获得机构
     * @param id
     * @return
     */
    @Override
    public OrganizationBean getByJgdm(String id) {
      return   organizationBeanDao.findUnique("select a from OrganizationBean a where a.jgdm=?1", id);
    }

    /**
     * 根据机构代码获取下级部分的集合
     * @param jgdm
     * @param page
     * @param page_size
     * @return
     */
    @POST
    @Path("/getorglist")
    @Consumes("application/json;charset=utf-8")
    @Override
    public List<OrganizationBean> getOrgListByJgdm(String jgdm, Integer page, Integer page_size) {
        if(jgdm!=null){
            jgdm = OrgnizationConst.JILIN_JGDM;
        }
        if(page!=null){
            page = 0;
        }else{
            page = page-1;
        }
        if(page_size!=null){
            page_size = 15;
        }
        return organizationBeanDao.findbyPage("select t from OrganizationBean t " +
                "where t.zfbj='0' and t.sjjgdm = '"+jgdm+"'",page,page_size);
    }

    @GET
    @Path("/getProvOrgListByJgdm/{page}/{page_size}")
    @Produces("application/json;charset=utf-8")
    public Map getProvOrgListByJgdm(@PathParam("page") Integer page, @PathParam("page_size") Integer page_size) {
        Map map = new HashMap();
        int result = -1;
        try{
            int start = 0;
            if(page==null){
                start=0;
            }else{
                start = (page-1)*page_size;
            }
            if(page_size==null){
                page_size = 15;
            }
            List<OrganizationBean> list = new ArrayList<OrganizationBean>();
            //jgdm = OrgnizationConst.JILIN_JGDM;
            list = organizationBeanDao.findByNativeSql("select u.jgdm,u.mc as name from urg_ent_organization u " +
                    "where u.jgdm='"+OrgnizationConst.JILIN_JGDM+"' and u.zfbj=0 limit "+start+","+page_size, OrgnizationListModel.class);
            result = 1;
            map.put("response",list);
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;

    }
    /**
     * 获取第一层节点上的机构
     * @return
     */
    @GET
    @Path("/getRootOrgnaizationList/{page}/{page_size}")
    @Produces("application/json;charset=utf-8")
    @Override
    public Map getRootOrgnaizationList(@PathParam("page")Integer page, @PathParam("page_size")Integer page_size){
        Map map = new HashMap();
        int result = -1;
        try{
            int start = 0;
            if(page==null){
                start=0;
            }else{
                start = (page-1)*page_size;
            }
            if(page_size==null){
                page_size = 15;
            }
            List list = organizationBeanDao.findByNativeSql("select u.jgdm,u.mc as name from urg_ent_organization u " +
                    "where u.xzqh_dm='220300' and u.jgbmbj!='1' and u.zfbj=0 limit "+start+","+page_size, OrgnizationListModel.class);

            if(list!=null && !list.isEmpty()){
                map.put("response",list);
            }
            result = 1;
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }
    /**
     * 获取下级部门和人员
     * @param jgdm
     * @return
     */
    @GET
    @Path("/getOranizationAndUserList/{jgdm}")
    @Produces("application/json;charset=utf-8")
    @Override
    public Map getOranizationAndUserList(@PathParam("jgdm")String jgdm) {
        Map map = new HashMap();
        int result = -1;
        try {
            List list = new ArrayList();
            List orglist = organizationBeanDao.findByNativeSql("select u.jgdm,u.mc as name ,u.sjjgdm from urg_ent_organization u " +
                    "where u.sjjgdm='"+jgdm+"' and u.zfbj=0 ", OrgnizationListModel.class);
            if(orglist!=null && !orglist.isEmpty()){
                list.addAll(orglist);
            }
            List userList = organizationBeanDao.findByNativeSql("select CONVERT(s.id,CHAR) as jgdm,s.name,su.user_logo, 2 as flag,'"+jgdm+"' as sjjgdm " +
                    "from sys_user s left join sys_user_rel su on su.user_id=s.id " +
                    "where s.jgdm='"+jgdm+"'", OrgnizationListModel.class);
            if(userList!=null && !userList.isEmpty()){
                list.addAll(userList);
            }
            if(list!=null && !list.isEmpty()){
                map.put("response",list);
            }
            result = 1;
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }
    /**
     * 根据区划代码获取组织机构
     * @return
     */
    @Override
    public List<OrganizationBean> getOrganizationListByQhdm() {
        AboutBean aboutBean = aboutBeanDao.get(AboutBean.class.getName(),1L);
        if(aboutBean==null){
            aboutBean.setXzqh_dm("-1");
        }
        return organizationBeanDao.findByNativeSql("select u.* from urg_ent_organization u " +
                "where u.xzqh_dm like '"+aboutBean.getXzqh_dm()+"%'", OrganizationBean.class);
    }

}
