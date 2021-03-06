package com.daren.core.web.wicket.custome;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBean;
import com.daren.core.web.api.provider.IAboutDialogProvider;
import com.daren.core.web.wicket.PermissionConstant;
import com.daren.core.web.wicket.security.ChangePassword;
import com.daren.core.web.wicket.security.PasswordInfo;
import com.daren.core.web.wicket.security.SignInPage;
import com.daren.core.web.wicket.security.SignOutPage;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import javax.inject.Inject;


/**
 * Created by dell on 14-3-23.
 */
public class CustomeHeaderPanel extends Panel {

    UserBean userBean;
    @Inject
    @Reference(id = "aboutDialogProvider", serviceInterface = IAboutDialogProvider.class)
    private IAboutDialogProvider aboutDialogProvider;
    @Inject
    @Reference(id = "userBeanService", serviceInterface = IUserBeanService.class)
    private IUserBeanService userBeanService;

    public CustomeHeaderPanel(String id) {
        super(id);

        //添加用户姓名到页面
        //todo 获得当前用户需要封装到单独的service中
        String userName = "";
        Session session = SecurityUtils.getSubject().getSession();
        userBean = (UserBean) session.getAttribute(PermissionConstant.SYS_CURRENT_USER);
        if (userBean != null) {
            userName = userBean.getName();
        } else {
            SecurityUtils.getSubject().logout();
            setResponsePage(SignInPage.class);
        }
        Label userNameLabel = new Label("userName", userName);
        add(userNameLabel);

        // Dialog //
        final ChangePassword dialog = new ChangePassword("dialog", "修改密码") {

            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit(AjaxRequestTarget target) {
                PasswordInfo passwordInfo = this.getModelObject();
                if (userBean != null) {
                    UserBean bean = (UserBean) userBeanService.getEntity(userBean.getId());
                    bean.setPassword(passwordInfo.getNewPassword());
                    userBeanService.saveEntity(bean);
                    target.appendJavaScript("alert('密码修改成功！')");
                }

            }

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {

            }
        };
        add(dialog);

        add(getChangePwdButton(dialog));

        /*add(new AjaxLink("about") {

            @Override
            public void onClick(AjaxRequestTarget target) {

            }
        });*/

        add(new Link("signout") {
            @Override
            public void onClick() {
                setResponsePage(SignOutPage.class);
            }
        });

    }

    private AjaxLink getChangePwdButton(final ChangePassword dialog) {
        return new AjaxLink("changePassword") {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {

                dialog.setTitle(target, "修改密码");
                dialog.setModelObject(new PasswordInfo()); //Provides a new model object to the dialog

                dialog.open(target); //Important: onOpen() event has been overridden in UserDialog to re-attach the inner form, in order to reflect the updated model

            }
        };
    }
}
