package com.daren.expert.webapp.wicket;

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
public class MainMenuModule implements IMenuModule {
    @Override
    public String getTargetTag() {
        return "resources.module.bundles";
    }

    @Override
    public String getName() {
        return "资源管理";
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
        return 1;
    }
}
