package com.daren.core.web.wicket;

import com.daren.core.web.wicket.custome.CustomeHeaderPanel;
import com.daren.core.web.wicket.custome.CustomeMenuPanel;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 14:29
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 14:29
 * 修改备注:  [说明本次修改内容]
 */
@ShiroSecurityConstraint(constraint = ShiroConstraint.LoggedIn)
public class CustomeHomePage extends WebPage {
    public CustomeHomePage() {
        add(new CustomeHeaderPanel("headerPanel"));
        add(new CustomeMenuPanel("menuPanel"));
        add(new FooterPage("footer"));
//        add(new Include("footer", "cus/Template/FooterPage.html"));
    }
}
