package com.wk.projects.activities.data;

import com.wk.projects.common.configuration.WkProjects;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/06/23 <br/>
 *      desc   : 活动所属的类别  <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>
 */

@SuppressWarnings(WkProjects.UNUSED)
public class WkActivity extends LitePalSupport {

    public static final long NO_PARENT=-1;
    public static final String ACTIVITY_ITEM_NAME="itemName";
    public static final String ACTIVITY_CREATE_TIME="createTime";
    public static final String ACTIVITY_PARENT_ID="parentId";
    public static final String ACTIVITY_IS_SYSTEM="isSystem";
    public static final String ACTIVITY_ID="activityId";

    /**
     * 类别名称
     * */
    private String itemName;

    /**
     * 类别创建时间
     * */
    private Long createTime;

    /**
     * 所属的类别 {@link #NO_PARENT}-1表示没有
     * */
    private Long parentId;

    /**
     * 是否能够被删除
     * */
    private boolean isSystem;

    /**
     * 所属的活动
     * */
    private ArrayList<ScheduleItem> scheduleItems=new ArrayList<>();

    public WkActivity(String itemName, Long createTime, Long parentId, boolean isSystem) {
        this.itemName = itemName;
        this.createTime = createTime;
        this.parentId = parentId;
        this.isSystem = isSystem;
    }

    public WkActivity(String itemName, Long createTime, Long parentId, boolean isSystem, ArrayList<ScheduleItem> scheduleItems) {
        this.itemName = itemName;
        this.createTime = createTime;
        this.parentId = parentId;
        this.isSystem = isSystem;
        this.scheduleItems = scheduleItems;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public ArrayList<ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public void setScheduleItems(ArrayList<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
    }

    @Override
    public long getBaseObjId() {
        return super.getBaseObjId();
    }

    @Override
    public String toString() {
        return "WkActivity{" +
                "itemName='" + itemName + '\'' +
                ", createTime=" + createTime +
                ", parentId=" + parentId +
                ", isSystem=" + isSystem +
                ", scheduleItems=" + scheduleItems +
                '}';
    }
}
