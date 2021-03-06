package com.daren.operations.api.biz;

import com.daren.core.api.biz.IBizService;
import com.daren.operations.entities.OperationsBean;

import java.util.List;

/**
 * @类描述：安全资格证书(培训)
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface IOperationsService extends IBizService {
    public List<OperationsBean> getOperationsByLoginName(String loginName);
}
