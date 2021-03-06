package com.daren.rescue.webapp.wicket.page;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.rescue.api.biz.IRescueBeanService;
import com.daren.rescue.entities.RescueBean;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：救援队管理
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RescueListPage extends BasePanel {

    private final static int numPerPage = 10;
    private final static String CONST_LIST = "救援队";
    private final static String CONST_ADD = "添加救援队";
    private final static String CONST_EDIT = "编辑救援队";
    private final TabbedPanel tabPanel;
    private final RepeatingView repeatingView = new RepeatingView("repeatingView");
    DictDataProvider provider = new DictDataProvider();
    IrisAbstractDialog dialog;
    Fragment fragment;
    //注入服务
    @Inject
    @Reference(id = "rescueBeanService", serviceInterface = IRescueBeanService.class)
    private IRescueBeanService rescueBeanService;

    //构造函数
    public RescueListPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        Options options = new Options();
        tabPanel = new TabbedPanel("tabs", this.newTabList(), options);
        this.add(tabPanel);

    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<? extends ITab> newTabList() {
        List<ITab> tabs = new ArrayList<ITab>();
        // tab #1 //
        tabs.add(new AbstractTab(Model.of(CONST_LIST)) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new MainFragment(panelId, "mainPanel");
            }
        });

        return tabs;
    }

    /**
     * 初始化新增按钮
     *
     * @return
     */
    private AjaxButton initAddButton() {
        //新增
        AjaxButton addButton = new AjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createNewTab(target, CONST_ADD, null);
            }

        };
        return addButton;
    }

    /**
     * 创建新的页面，用于新增和修改
     *
     * @param target
     * @param newTabType "修改字典"||"新增字典"
     * @param row        数据
     */
    private void createNewTab(AjaxRequestTarget target, final String newTabType, final RescueBean row) {
        //去掉第二个tab
        if (tabPanel.getModelObject().size() == 2) {
            tabPanel.getModelObject().remove(1);
        }
        tabPanel.add(new AjaxTab(Model.of(newTabType)) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getLazyPanel(String panelId) {
                //通过repeatingView增加新的panel
                repeatingView.removeAll();
                RescueAddPage rescueAddPage = new RescueAddPage(repeatingView.newChildId(), newTabType, Model.of(row)) {
                    //关闭新增tab
                    @Override
                    protected void onDeleteTabs(AjaxRequestTarget target) {
                        if (tabPanel.getModelObject().size() == 2)
                            tabPanel.getModelObject().remove(1);
                        target.add(tabPanel);
                    }
                };
                repeatingView.add(rescueAddPage);
                fragment = new Fragment(panelId, "addPanel", RescueListPage.this);
                fragment.add(repeatingView);
                return fragment;
            }
        });

        tabPanel.setActiveTab(1);
        target.add(tabPanel);
    }

    //列表显示
    public class MainFragment extends Fragment {
        final WebMarkupContainer table;
        private final JQueryFeedbackPanel feedbackPanel;
        private final WebMarkupContainer container, dialogWrapper;

        public MainFragment(String id, String markupId) {
            super(id, markupId, RescueListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));
            //add dialog

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
            container.add(dialogWrapper.setOutputMarkupId(true));

            //add table
            table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));

            //add listview
            final DataView<RescueBean> listView = new DataView<RescueBean>("rows", provider, numPerPage) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<RescueBean> item) {
                    final RescueBean row = item.getModelObject();
                    item.add(new Label("name", row.getName()));
                    item.add(new Label("head", row.getHead()));
                    item.add(new Label("headPhone", row.getHeadPhone()));
                    item.add(new Label("telephone", row.getTelephone()));
                    item.add(new Label("totalNumber", row.getTotalNumber()));
                    item.add(new Label("address", row.getAddress()));
                    //item.add(new Label("jd", row.getJd()));
                    //item.add(new Label("wd", row.getWd()));
                    item.add(new Label("equipment", row.getEquipment()));
                    item.add(new Label("expertise", row.getExpertise()));
                    item.add(new Label("remarks", row.getRemarks()));

                    AjaxLink alinkOnDuty = new AjaxLink("onDuty") {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            createOnDutyDialog(target, row, "值班列表");
                        }
                    };
                    alinkOnDuty.add(new Label("onDutyLabel", "值班"));
                    item.add(alinkOnDuty.setOutputMarkupId(true));

                    AjaxLink alinkScheduling = new AjaxLink("scheduling") {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            createSchedulingDialog(target, row, "排班列表");
                        }
                    };
                    alinkScheduling.add(new Label("schedulingLabel", "排班"));
                    item.add(alinkScheduling.setOutputMarkupId(true));

                    //add delete button
                    item.add(initDeleteButton(row));
                    //add update button
                    item.add(initUpdateButton(row));
                    //add upload button
                    item.add(initUploadOnDutyButton(row));
                    item.add(initUploadSchedulingButton(row));
                }
            };
            table.add(listView);

            //增加分页指示器
            CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);

            //增加form
            Form<RescueBean> dictForm = new Form<>("form", new CompoundPropertyModel<>(new RescueBean()));
            TextField textField = new TextField("name");
            textField.setRequired(false);
            dictForm.add(textField.setOutputMarkupId(true));

            //find button
            dictForm.add(initFindButton(provider, dictForm));
            //add button
            dictForm.add(initAddButton());
            add(dictForm);
        }

        /**
         * 创建dialog
         *
         * @param target
         * @param row
         * @param title
         */
        private void createOnDutyDialog(AjaxRequestTarget target, final RescueBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new OnDutyListPage("dialog", title, new CompoundPropertyModel<>(row));
            target.add(dialogWrapper);
            dialog.open(target);
        }

        private void createSchedulingDialog(AjaxRequestTarget target, final RescueBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new SchedulingListPage("dialog", title, new CompoundPropertyModel<>(row));
            target.add(dialogWrapper);
            dialog.open(target);
        }

        private void createUploadOnDutyDialog(AjaxRequestTarget target, final RescueBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new UploadOnDutyPage("dialog", title, new CompoundPropertyModel<>(row)) {
                @Override
                public void updateTarget(AjaxRequestTarget target) {
                    target.add(table);
                }
            };
            target.add(dialogWrapper);
            dialog.open(target);
        }

        private void createUploadSchedulingDialog(AjaxRequestTarget target, final RescueBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new UploadSchedulingPage("dialog", title, new CompoundPropertyModel<>(row)) {
                @Override
                public void updateTarget(AjaxRequestTarget target) {
                    target.add(table);
                }
            };
            target.add(dialogWrapper);
            dialog.open(target);
        }

        /**
         * 初始化更新按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUpdateButton(final RescueBean row) {
            //修改功能
            AjaxLink alink = new AjaxLink("edit") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createNewTab(target, CONST_EDIT, row);
                }
            };
            return alink;
        }

        /**
         * 初始化删除按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initDeleteButton(final RescueBean row) {
            //删除功能
            AjaxLink alink = new AjaxLink("delete") {
                @Override
                protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                    super.updateAjaxAttributes(attributes);
                    AjaxCallListener listener = new AjaxCallListener();
                    listener.onPrecondition("if(!confirm('确定要删除吗')){return false;}");
                    attributes.getAjaxCallListeners().add(listener);
                }

                @Override
                public void onClick(AjaxRequestTarget target) {
                    try {
                        rescueBeanService.deleteEntity(row.getId());
                        feedbackPanel.info("删除成功！");
                        target.addChildren(getPage(), JQueryFeedbackPanel.class);
                        target.add(container);
                    } catch (Exception e) {
                        feedbackPanel.error("删除失败！");
                        e.printStackTrace();
                    }
                }
            };
            return alink;
        }

        /**
         * 初始化值班上传按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUploadOnDutyButton(final RescueBean row) {
            AjaxLink alink = new AjaxLink("uploadOnDutyLabel") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createUploadOnDutyDialog(target, row, "上传值班表");
                }
            };
            return alink;
        }

        /**
         * 初始化排班上传按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUploadSchedulingButton(final RescueBean row) {
            AjaxLink alink = new AjaxLink("uploadSchedulingLabel") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createUploadSchedulingDialog(target, row, "上传值班表");
                }
            };
            return alink;
        }

        /**
         * 初始化查询按钮
         *
         * @param provider
         * @param form
         * @return
         */
        private AjaxButton initFindButton(final DictDataProvider provider, Form form) {

            return new AjaxButton("find", form) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    RescueBean dictBean = (RescueBean) form.getModelObject();
                    provider.setDictBean(dictBean);
                    target.add(container);
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    target.add(feedbackPanel);
                }
            };
        }
    }

    /**
     * //查询数据提供者
     */
    class DictDataProvider extends ListDataProvider<RescueBean> {
        private RescueBean dictBean = null;

        public void setDictBean(RescueBean dictBean) {
            this.dictBean = dictBean;
        }

        @Override
        protected List<RescueBean> getData() {
            //类型为空时候，返回全部记录
            if (dictBean == null)
                return rescueBeanService.getAllEntity();
            else {
                return rescueBeanService.query(dictBean);
            }
        }
    }
}