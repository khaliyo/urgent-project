package com.daren.rescue.webapp.wicket.page;

import com.daren.core.web.component.map.WindowMapPage;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.daren.rescue.api.biz.IRescueBeanService;
import com.daren.rescue.entities.RescueBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

/**
 * @类描述：救援队管理-添加
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class RescueAddPage extends Panel {
    final WebMarkupContainer dialogWrapper;
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    WindowMapPage dialog;
    private boolean isAdd;
    //注入服务
    @Inject
    @Reference(id = "rescueBeanService", serviceInterface = IRescueBeanService.class)
    private IRescueBeanService rescueBeanService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示
    private RescueBean rescueBean=new  RescueBean();

    public RescueAddPage(String id, String type, IModel<RescueBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null) {
            isAdd = true;
            initForm(Model.of(rescueBean));
        } else {
            isAdd = false;
            initForm(model);
        }

        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("dialog"));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<RescueBean> model) {
        final Form<RescueBean> dictForm = new Form("dictForm", new CompoundPropertyModel(model));
        feedbackPanel = new JQueryFeedbackPanel("feedback");
        dictForm.add(feedbackPanel.setOutputMarkupId(true));
        TextField name = new TextField("name");

        dictForm.add(name.setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("head").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("headPhone").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        TextField telephone = new TextField("telephone");
        telephone.setConvertEmptyInputStringToNull(false);
        dictForm.add(telephone.setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("totalNumber").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("address").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new HiddenField("jd").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new HiddenField("wd").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("equipment").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("expertise").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("remarks").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new AjaxButton("save", dictForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    RescueBean dictBean = (RescueBean) form.getModelObject();
                    dictBean.setEnterpriseId(1l);
                        rescueBeanService.saveEntity(dictBean);
                    if (isAdd) {
                        dictForm.setModelObject(new RescueBean());
                    }
                    feedbackPanel.info(type + dictBean.getName() + "成功！");
                    target.add(form);
                } catch (Exception e) {
                    feedbackPanel.error(type + "失败！");
                    e.printStackTrace();
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });

        dictForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
        dictForm.setOutputMarkupId(true);
//        dictForm.add(new JSR303FormValidator());
        add(dictForm);
        dictForm.add(initGisButton());
    }

    private void createDialog(AjaxRequestTarget target, final String title) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowMapPage("dialog", title) {
            @Override
            public void updateTarget(AjaxRequestTarget target) {

            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    private AjaxLink initGisButton() {
        AjaxLink alink = new AjaxLink("gisSrc") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "标注地址");
            }
        };
        return alink;
    }
}
