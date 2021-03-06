package com.daren.regulation.webapp.wicket.page;


import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.regulation.api.biz.IUploadDocumentService;
import com.daren.regulation.entities.DocmentBean;
import com.daren.regulation.entities.RegulationBean;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.time.Duration;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @类描述：法律法规-文档列表
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class DocumentListPage extends IrisAbstractDialog<RegulationBean> {
    @Inject
    private IUploadDocumentService uploadDocumentService;

    public DocumentListPage(String id, String title, IModel<RegulationBean> model) {
        super(id, title, model);
        RegulationBean regulationBean = (RegulationBean) model.getObject();
        long entityId = regulationBean.getId();
        List<DocmentBean> list = uploadDocumentService.getDocmentBeanListByAttach(entityId);
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<DocmentBean> lv = new PageableListView<DocmentBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<DocmentBean> item) {
                final DocmentBean docmentBean = item.getModelObject();
                item.add(new Label("name", docmentBean.getName()));
                item.add(new Label("description", docmentBean.getDescription()));

                //下载文档
                DownloadLink alinkdownDocument = new DownloadLink("downDocument", new AbstractReadOnlyModel<File>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public File getObject() {
                        File tempFile = null;
                        try {
                            tempFile = new File(docmentBean.getName());
                            FileInputStream fileInputStream = new FileInputStream(docmentBean.getFilePath());
                            DataInputStream data = new DataInputStream(fileInputStream);
                            Files.writeTo(tempFile, data);
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return tempFile;
                    }
                }).setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);
                item.add(alinkdownDocument.setOutputMarkupId(true));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", lv) {
        };
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);
    }
}
