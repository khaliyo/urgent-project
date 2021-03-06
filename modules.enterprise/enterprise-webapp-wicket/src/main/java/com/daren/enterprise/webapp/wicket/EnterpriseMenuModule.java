package com.daren.enterprise.webapp.wicket;

import com.daren.core.api.IConst;
import com.daren.core.web.api.module.IMenuModule;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/9 19:53
 * 修改人:    sunlf
 * 修改时间:  2014/7/9 19:53
 * 修改备注:  [说明本次修改内容]
 */
public class EnterpriseMenuModule implements IMenuModule {
    @Override
    public boolean isPermissionAvaliable() {
        return true;
    }

    @Override
    public String getPermissionName() {
        return Const.PERMISSION_INFO;
    }

    @Override
    public String getProjectName() {
        return IConst.SYSTEM_WICKET_APPLICATION_NAME;
    }

    @Override
    public String getTargetTag() {
        return "enterprise.module.bundles";
    }

    @Override
    public String getName() {
        return Const.MENU_INFO;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getTag() {
        return null;
    }

    @Override
    public int getIndex() {
        return 10;
    }
}
