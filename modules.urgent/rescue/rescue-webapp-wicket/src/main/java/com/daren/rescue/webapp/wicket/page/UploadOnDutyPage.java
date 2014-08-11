package com.daren.rescue.webapp.wicket.page;

import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.rescue.api.biz.IOnDutyBeanService;
import com.daren.rescue.entities.OnDutyBean;
import com.daren.rescue.entities.RescueBean;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.file.File;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @类描述：救援队管理-上传值班
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class UploadOnDutyPage extends IrisAbstractDialog<RescueBean> {
    //注入服务
    @Inject
    @Reference(id = "onDutyBeanService", serviceInterface = IOnDutyBeanService.class)
    private IOnDutyBeanService onDutyBeanService;

    private FeedbackPanel feedbackPanel; //信息显示

    public UploadOnDutyPage(String id, String title, IModel<RescueBean> model) {
        super(id, title, model);
        final RescueBean regulationBean = (RescueBean) model.getObject();
        final OnDutyBean onDutyBean = new OnDutyBean();
        final FileUploadField fileUploadField = new FileUploadField("filePath");
        Form form = new Form("form", new CompoundPropertyModel(onDutyBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(fileUploadField);
        feedbackPanel = new FeedbackPanel("feedback");
        form.add(feedbackPanel.setOutputMarkupId(true));

        //保存按钮
        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                try {
                    List<FileUpload> fileUploadList = fileUploadField.getFileUploads();
                    List<OnDutyBean> list = onDutyBeanService.getOnDutyBeanByAttach(regulationBean.getId());
                    for (int i = 0; i < list.size(); i++) {
                        onDutyBeanService.deleteOnDutyBeanById(list.get(i).getId());
                    }
                    if (null != fileUploadList && fileUploadList.size() > 0) {
                        for (FileUpload fileUpload : fileUploadList) {
                            String path = "F:\\saveFilePath\\值班表.xls";
                            File file = new File(path);
                            fileUpload.writeTo(file);
                            InputStream is = new FileInputStream(path);
                            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
                            // 循环工作表Sheet
                            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                                if (hssfSheet == null) {
                                    continue;
                                }
                                // 循环行Row
                                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                                    OnDutyBean onDutyBeanFile = new OnDutyBean();
                                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                                    if (hssfRow == null) {
                                        continue;
                                    }
                                    HSSFCell c1 = hssfRow.getCell((short) 0);
                                    if (c1 == null) {
                                        continue;
                                    }
                                    onDutyBeanFile.setDate(stringToDate(getValue(c1)));//日期

                                    HSSFCell c2 = hssfRow.getCell((short) 1);
                                    if (c2 == null) {
                                        continue;
                                    }
                                    onDutyBeanFile.setPerson(getValue(c2));

                                    HSSFCell c3 = hssfRow.getCell((short) 2);
                                    if (c3 == null) {
                                        continue;
                                    }
                                    onDutyBeanFile.setTel(getValue(c3));

                                    HSSFCell c4 = hssfRow.getCell((short) 3);
                                    if (c4 == null) {
                                        continue;
                                    }
                                    onDutyBeanFile.setExpertise(getValue(c4));

                                    HSSFCell c5 = hssfRow.getCell((short) 4);
                                    if (c5 == null) {
                                        continue;
                                    }
                                    onDutyBeanFile.setRemarks(getValue(c5));
                                    onDutyBeanFile.setAttach(regulationBean.getId());
                                    onDutyBeanService.saveEntity(onDutyBeanFile);
                                    super.onSubmit(target, form);
                                }
                            }
                            is.close();
                            File fileDelete = new File(path);
                            fileDelete.remove();
                        }
                    }
                    feedbackPanel.info("上传成功！");
                    target.add(feedbackPanel);
                } catch (Exception e) {
                    feedbackPanel.info("上传失败！");
                    target.add(feedbackPanel);
                    e.printStackTrace();
                }
            }
        };
        add(ajaxSubmitLinkCreate);
    }

    /**
     * 得到Excel表中的值
     *
     * @return Excel中每一个格子中的值
     */
    private String getValue(HSSFCell hssfCell) {
        String strCell = "";
        switch (hssfCell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = hssfCell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                DecimalFormat df = new DecimalFormat("#");//转换成整型
                strCell = String.valueOf(df.format(hssfCell.getNumericCellValue()));
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(hssfCell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (hssfCell == null) {
            return "";
        }
        return strCell;
    }

    public static Date stringToDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat df = DateFormat.getInstance();
            Calendar calendar = df.getCalendar();
            calendar.setTime(format.parse(date));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}