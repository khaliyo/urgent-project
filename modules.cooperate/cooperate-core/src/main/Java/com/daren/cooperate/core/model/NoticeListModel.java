package com.daren.cooperate.core.model;

/**
 * @类描述：用于返回日程列表的类
 * @创建人：xukexin
 * @创建时间：2014/9/10 15:12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class NoticeListModel {

    private Long id;                //主键id
    private long user_id;         //发布人id
    private String title;           //标题
    private String content;         //内容
    private String notice_time;     //日程时间
    private String create_time;     //发布时间
    private String name;            //发布人姓名
    private int reply_type;         //确认类型0:待回复；1-确认；2-拒绝

    public NoticeListModel(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(String notice_time) {
        this.notice_time = notice_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReply_type() {
        return reply_type;
    }

    public void setReply_type(int reply_type) {
        this.reply_type = reply_type;
    }
}
