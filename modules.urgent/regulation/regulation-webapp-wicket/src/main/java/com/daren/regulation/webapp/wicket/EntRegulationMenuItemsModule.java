package com.daren.regulation.webapp.wicket;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.regulation.webapp.wicket.page.RegulationListPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：企业法律法规子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EntRegulationMenuItemsModule implements IMenuItemsModule {


    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isPermissionAvaliable() {
        return false;
    }

    @Override
    public String getPermissionName() {
        return null;
    }

    @Override
    public int getIndex() {
        return 1;
    }

    @Override
    public String getName() {
        return "法律法规管理";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "ent.regulation.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Panel getPanel(String id, WebMarkupContainer wmc) {
        return new RegulationListPage(id, wmc);
    }
}