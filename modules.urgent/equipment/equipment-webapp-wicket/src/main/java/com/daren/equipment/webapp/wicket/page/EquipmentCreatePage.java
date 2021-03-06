package com.daren.equipment.webapp.wicket.page;

import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.component.map.WindowMapPage;
import com.daren.core.web.wicket.BasePanel;
import com.daren.equipment.api.biz.IEquipmentBeanService;
import com.daren.equipment.entities.EquipmentBean;
import com.daren.rescue.api.biz.IRescueBeanService;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EquipmentCreatePage extends BasePanel {
    final WebMarkupContainer dialogWrapper;
    WindowMapPage dialog;
    Form<EquipmentBean> equipmentBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new EquipmentBean()));
    EquipmentBean equipmentBean = new EquipmentBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IRescueBeanService rescueBeanService;
    @Inject
    private IEquipmentBeanService equipmentBeanService;

    public EquipmentCreatePage(final String id, final WebMarkupContainer wmc, final EquipmentBean bean) {
        super(id, wmc);
        if (null != bean) {
            equipmentBean = bean;
        }
        initForm(equipmentBean);
        initFeedBack();
        addForm(id, wmc);
        addSelectToForm();

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

    public void addForm(final String id, final WebMarkupContainer wmc) {

        equipmentBeanForm.setMultiPart(true);
        this.add(equipmentBeanForm);

        addTextFieldsToForm();
        equipmentBeanForm.add(initGisButton());
        AjaxButton ajaxSubmitLink = new AjaxButton("save", equipmentBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                EquipmentBean equipmentBean = (EquipmentBean) equipmentBeanForm.getDefaultModelObject();
                if (null != equipmentBean) {
                    try {
                        equipmentBean.setUpdateDate(new Date());
                        equipmentBeanService.saveEntity(equipmentBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        equipmentBeanForm.add(ajaxSubmitLink);
        equipmentBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(EquipmentBean bean) {
        equipmentBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    //通过字典初始化下拉列表
    private void initSelect(String name, String dictConst) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, dictConst);
        equipmentBeanForm.add(listSites);
    }

    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        equipmentBeanForm.add(listSites);
    }

    private void addSelectToForm() {
        initSelect("property", IDictConstService.EQUIPMENT_PROPERTY);
        initSelect("registrationType", IDictConstService.REGISTRATION_TYPE);
        initSelect("equipmentSources", IDictConstService.EQUIPMENT_SOURCES);
        initSelect("equipmentType", IDictConstService.EQUIPMENT_TYPE);
        initSelect("rescueId", new HashMap<String, String>());
//        initSelect("rescueId", rescueBeanService.getAllBeansToHashMap());
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        equipmentBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        equipmentBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
//        addTextFieldToForm("property");
//        addTextFieldToForm("registrationType");
//        addTextFieldToForm("rescueId");
        addTextFieldToForm("unitName");
//        addTextFieldToForm("equipmentSources");
//        addTextFieldToForm("equipmentType");
        addTextFieldToForm("parametersSpecifications");
        addTextFieldToForm("measuringUnit");
        addTextFieldToForm("amount");
        addTextFieldToForm("regularMaintenanceInterval");
        addTextFieldToForm("durableYears");
        addTextFieldToForm("lastMaintenanceDate");
        addTextFieldToForm("manufacturer");
        addTextFieldToForm("manufactureDate");
        addTextFieldToForm("purchaseDate");
        addTextFieldToForm("unitFax");
        addTextFieldToForm("principal");
        addTextFieldToForm("officePhone");
        addTextFieldToForm("homePhone");
        addTextFieldToForm("mobilePhone");
        addTextFieldToForm("describeOrPurposes");
        addTextFieldToForm("warehouse");
        addTextFieldToForm("storagePlace");
        addTextFieldToForm("img");
        addTextFieldToForm("remark");
        addHiddenFieldToForm("jd");
        addHiddenFieldToForm("wd");
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