package com.daren.core.web.wicket.custome;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.core.web.api.module.IMenuModule;
import com.daren.core.web.api.module.IModule;
import com.daren.core.web.wicket.MenuModuleManager;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.List;

/**
 * Created by dell on 14-3-23.
 */
public class CustomeMenuItemsPanel extends Panel {

    public CustomeMenuItemsPanel(String id, final IMenuModule menus, final int index) {
        super(id);
        Label labelName = new Label("name", menus.getName());
        add(labelName);

        //获取目录下面的子菜单
        List<IModule> submenus = MenuModuleManager.getInstall().findMenusByTag(menus.getTargetTag());
        ListView<IModule> listView = new ListView<IModule>("menuList", submenus) {
            @Override
            protected void populateItem(final ListItem<IModule> item) {
                Link submenusLink = new Link("menus") {


                    @Override
                    public void onClick() {
                        setResponsePage(((IMenuItemsModule) item.getModelObject()).getPageClass());
                    }
                };
                submenusLink.setOutputMarkupId(true);
                submenusLink.add(new Label("linkName", item.getModelObject().getName()).setRenderBodyOnly(true).setEscapeModelStrings(false));
                item.add(submenusLink);
            }
        };
        add(listView);
    }

}


