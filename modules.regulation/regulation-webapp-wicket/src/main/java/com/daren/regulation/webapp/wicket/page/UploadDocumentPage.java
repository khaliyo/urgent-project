package com.daren.regulation.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.regulation.api.biz.IUploadDocumentService;
import com.daren.regulation.entities.DocmentBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.file.File;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @类描述：法律法规-上传文档
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UploadDocumentPage extends BasePanel {
    @Inject
    private IUploadDocumentService uploadDocumentService;

    public UploadDocumentPage(final String id, final WebMarkupContainer wmc, final long entityId) {
        super(id, wmc);
        final DocmentBean docmentBean = new DocmentBean();
        final FileUploadField fileUploadField = new FileUploadField("filePath");
        Form form = new Form("form", new CompoundPropertyModel(docmentBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(fileUploadField);
        form.add(new TextField("description"));

        //保存按钮
        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DocmentBean docmentBean1 = (DocmentBean) form.getDefaultModelObject();
                try {
                    List<FileUpload> fileUploadList = fileUploadField.getFileUploads();
                    if (null != fileUploadList && fileUploadList.size() > 0) {
                        for (FileUpload fileUpload : fileUploadList) {
                            String path = "F:\\saveFilePath\\" + fileUpload.getMD5();
                            File file = new File(path);
                            fileUpload.writeTo(file);
                            docmentBean1.setFilePath(path);
                            docmentBean1.setName(fileUpload.getClientFileName());
                            docmentBean1.setSize(fileUpload.getSize());
                            docmentBean1.setType(fileUpload.getContentType());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                docmentBean1.setAttach(entityId);
                uploadDocumentService.saveEntity(docmentBean1);
                super.onSubmit(target, form);
               /* wmc.removeAll();
                wmc.addOrReplace(new RegulationPage(id, wmc));
                target.add(wmc);*/
            }
        };
        add(ajaxSubmitLinkCreate);
        //返回按钮
        AjaxLink ajaxLinkReturn = new AjaxLink("return") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
               /* wmc.removeAll();
                wmc.addOrReplace(new RegulationPage(id, wmc));
                ajaxRequestTarget.add(wmc);*/
            }
        };
        this.add(ajaxLinkReturn);
    }
}