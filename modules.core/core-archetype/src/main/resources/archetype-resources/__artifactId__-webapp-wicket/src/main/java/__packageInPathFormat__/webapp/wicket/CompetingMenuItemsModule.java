package ${package}.webapp.wicket;

import ${package}.webapp.wicket.page.CompetingPage;
import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.Page;

/**
 * @类描述：竞品对比子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午9:52
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CompetingMenuItemsModule implements IMenuItemsModule {
    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 40;
    }

    @Override
    public Class<? extends Page> getPageClass() {
        return CompetingPage.class;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return "竞品对比";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "cartype.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
