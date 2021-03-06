package com.daren.production.webapp.wicket.impl;

import com.daren.core.web.api.workflow.IFormHandler;
import com.daren.production.webapp.wicket.Const;
import com.daren.production.webapp.wicket.page.ProductionModifyFormPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：烟花爆竹经营许可证流程
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ProductionModifyFormHandler implements IFormHandler{
    @Override
    public Panel getPanel(String id, IModel model) {
        return new ProductionModifyFormPage(id,model);
    }

    @Override
    public String getFormKey() {
        return "modify.form";
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
