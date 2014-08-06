package com.daren.rescue.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.rescue.api.biz.IRescueBeanService;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RescuePage extends BasePanel {

    @Inject
    private IRescueBeanService rescueBeanService;

    public RescuePage(String id, WebMarkupContainer wmc) {


        super(id, wmc);

        List list=rescueBeanService.getAllEntity();
    }
}