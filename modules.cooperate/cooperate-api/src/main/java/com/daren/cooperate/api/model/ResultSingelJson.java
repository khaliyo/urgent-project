package com.daren.cooperate.api.model;

import com.daren.core.api.persistence.PersistentEntity;

/**
 * @类描述：JSON返回GET 详情结果类
 * @创建人：xukexin
 * @创建时间：2014/9/5 15:07
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ResultSingelJson {

    private StatusJson statusJson = new StatusJson();
    private PersistentEntity entity;

    public StatusJson getStatusJson() {
        return statusJson;
    }

    public void setStatusJson(StatusJson statusJson) {
        this.statusJson = statusJson;
    }

    public PersistentEntity getEntity() {
        return entity;
    }

    public void setEntity(PersistentEntity entity) {
        this.entity = entity;
    }
}
