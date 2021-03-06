package com.daren.enterprise.webapp.wicket.page;

import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.api.biz.IOrganizationBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.enterprise.entities.OrganizationBean;
import com.daren.enterprise.webapp.component.form.OrgnizationSelect2Choice;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;


/**
 * @类描述：企业信息维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterpriseAddPage extends BasePanel {

    Form<EnterpriseBean> enterpriseBeanForm = new Form("enterpriseForm", new CompoundPropertyModel(new EnterpriseBean()));
    EnterpriseBean enterpriseBean = new EnterpriseBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    OrgnizationSelect2Choice orgnizationSelect2Choice;
    private OrganizationBean organizationBean = new OrganizationBean();
    @Inject
    private IEnterpriseBeanService enterpriseBeanService;
    @Inject
    private IOrganizationBeanService organizationBeanService;

    public EnterpriseAddPage(final String id, final WebMarkupContainer wmc, EnterpriseBean bean) {
        super(id, wmc);
        if (null != bean) {
            enterpriseBean = bean;
            Long qyid=new Long(enterpriseBean.getJgjgdm());
            organizationBean= (OrganizationBean) organizationBeanService.getEntity(qyid);

        }
        initForm(enterpriseBean);
        initFeedBack();
        addForm(id, wmc);
        orgnizationSelect2Choice = new OrgnizationSelect2Choice ("jgjgdm",Model.of(organizationBean));
        orgnizationSelect2Choice.getSettings().setMinimumInputLength(2);
        enterpriseBeanForm.add(orgnizationSelect2Choice);
        addSelectToForm();
    }

    private void addForm(final String id, final WebMarkupContainer wmc) {

        enterpriseBeanForm.setMultiPart(true);
        this.add(enterpriseBeanForm);
        addTextFieldsToForm();
        //日期控件//
        final DatePicker datePicker = new DatePicker("clsj",
                new PropertyModel<Date>(enterpriseBean, "clsj"), "yyyy-MM-dd",
                new Options("dateFormat", Options.asString("yy-mm-dd")));
        enterpriseBeanForm.add(datePicker);

        AjaxButton ajaxButton = new AjaxButton("save", enterpriseBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                EnterpriseBean enterpriseBean = (EnterpriseBean) form.getModelObject();
                if (null != enterpriseBean) {
                    try {
                        organizationBean=orgnizationSelect2Choice.getModelObject();
                        enterpriseBean.setJgjgdm(organizationBean.getJgdm());
                        enterpriseBeanService.saveEntity(enterpriseBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        e.printStackTrace();
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        };
        enterpriseBeanForm.add(ajaxButton);
        enterpriseBeanForm.add(new IrisIndicatingAjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void initForm(EnterpriseBean bean) {
        enterpriseBeanForm.setModelObject(enterpriseBean);
    }

    //通过字典初始化下拉列表
    private void initSelect(String name, String dictConst) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, dictConst);
        enterpriseBeanForm.add(listSites);
    }

    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        enterpriseBeanForm.add(listSites);
    }

    private void addSelectToForm() {
        initSelect("aqjgszqk", IDictConstService.AQJGSZQK);
        initSelect("zybbj", IDictConstService.ZYBBJ);
        initSelect("zdxfdwbj", IDictConstService.ZDXFDW_BJ);
        initSelect("yazlqybj", IDictConstService.YAZLQY_BJ);
        initSelect("xjqybj", IDictConstService.ENTERPRISE_XJQYBJ);
        initSelect("gmqk", IDictConstService.ENTERPRISE_GMQK);
        //initSelect("jgjgdm", organizationBeanService.getAllBeansToHashMap());
        initSelect("jgfl", IDictConstService.ENTERPRISE_JGFL);
        //initSelect("jgjgdm");
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        enterpriseBeanForm.add(textField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("qymc");
        addTextFieldToForm("frdb");
//        addTextFieldToForm("cl_sj");
        addTextFieldToForm("addresszc");
        addTextFieldToForm("addressjy");
        addTextFieldToForm("postcode");
        addTextFieldToForm("qylxfs");
        addTextFieldToForm("mailaddress");
        addTextFieldToForm("zyjyxm");
        addTextFieldToForm("zyyl");
        addTextFieldToForm("zycp");
        //addTextFieldToForm("jgjgdm");
        addTextFieldToForm("zzjgdm");
        addTextFieldToForm("sdlx");
        addTextFieldToForm("jjlxbm");
        addTextFieldToForm("cyrs");
        addTextFieldToForm("zcaqgcsrs");
        addTextFieldToForm("aqrs");
        addTextFieldToForm("jzaqrs");
        addTextFieldToForm("hymldm");
        addTextFieldToForm("hylbbm");
        //addTextFieldToForm("xjqybj");
        addTextFieldToForm("sjqyid");
        addTextFieldToForm("hyzxldm");
        addTextFieldToForm("qygmbm");
        //addTextFieldToForm("gmqk");
        addTextFieldToForm("zczc");
        //addTextFieldToForm("jgfl");
        addTextFieldToForm("sndxssr");
        addTextFieldToForm("aqscfy");
//        addTextFieldToForm("yazlqy_bj");
//        addTextFieldToForm("zdxfdw_bj");
//        addTextFieldToForm("zybbj");
//        addTextFieldToForm("aqjgszqk");
        addTextFieldToForm("jd");
        addTextFieldToForm("wd");
    }

}