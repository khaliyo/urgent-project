package com.daren.gis.web.bootup.wicket;

import com.daren.core.api.IConst;
import com.daren.core.web.api.module.ISystemItemModule;
import com.daren.gis.webapp.wicket.page.GisTemplatePage;
import org.apache.wicket.markup.html.WebPage;

/**
 * @类描述：系统菜单Gis子菜单
 * @创建人：xukexin
 * @创建时间：2014/9/23 8:19
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class GisSysItemModule implements ISystemItemModule{
    @Override
    public String getProjectName() {
        return IConst.GIS_WICKET_APPLICATION_NAME;
    }

    @Override
    public String getUrl() {
        return "../../gis";
    }

    @Override
    public WebPage getWebPage(){
        return  new GisTemplatePage();
    }

    @Override
    public String getName() {
        return "GIS系统";
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getTag() {
        return IConst.PROJECT_WICKET_APPLICATION_NAME;
    }

    @Override
    public int getIndex() {
        return 0;
    }

}
