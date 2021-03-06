package com.daren.fireworks.webapp.wicket.page;

import com.daren.core.util.DateUtil;
import com.daren.fireworks.api.biz.IFireworksService;
import com.daren.fireworks.entities.FireworksBean;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.Date;

/**
 * @类描述：显示业务数据的页面类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksBizDataPanel extends Panel {
    @Inject
    IFireworksService fireworksService;
    FireworksBean bean;
    public FireworksBizDataPanel(String id, String bizId) {
        super(id);
        bean= (FireworksBean) fireworksService.getEntity(new Long(bizId));
        this.setDefaultModel(new CompoundPropertyModel(bean));
        addBizData();
        
    }

    private void addBizData() {
        //add data to form
        add(new Label("name",new PropertyModel<String>(bean, "name")));
        add(new Label("head",new PropertyModel<String>(bean, "head")));
        add(new Label("phone",new PropertyModel<String>(bean, "phone")));
        add(new Label("address",new PropertyModel<String>(bean, "address")));
        add(new Label("economicsType",new PropertyModel<String>(bean, "economicsType")));
        add(new Label("storageAddress",new PropertyModel<String>(bean, "storageAddress")));
        add(new Label("scope",new PropertyModel<String>(bean, "scope")));
        add(new Label("code",new PropertyModel<String>(bean, "code")));
        add(new Label("card",new PropertyModel<String>(bean, "card")));
        Date validityDate=new PropertyModel<Date>(bean, "validityDate").getObject();
        add(new Label("validityDate",DateUtil.convertDateToString(validityDate, DateUtil.shortSdf)));
        Date unitsDate=new PropertyModel<Date>(bean, "unitsDate").getObject();
        add(new Label("unitsDate", DateUtil.convertDateToString(unitsDate, DateUtil.shortSdf)));
        add(new Label("linkHandle",new PropertyModel<String>(bean, "linkHandle")));
    }
}
