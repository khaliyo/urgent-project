package com.daren.fireworks.webapp.wicket.impl;

import com.daren.core.web.api.workflow.IFormHandler;
import com.daren.fireworks.webapp.wicket.Const;
import com.daren.fireworks.webapp.wicket.page.FireworksAuditFormPage;
import com.daren.fireworks.webapp.wicket.page.FireworksEndFormPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：烟花爆竹经营(批发)许可证
 * @创建人： Administrator
 * @创建时间：2014/9/13
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksEndFormHandler implements IFormHandler{
    @Override
    public Panel getPanel(String id, IModel model) {
        return new FireworksEndFormPage(id,model);
    }

    @Override
    public String getFormKey() {
        return "end.form";
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
