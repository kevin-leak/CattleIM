package com.example.netKit.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

@Table(database = AppDatabase.class)
public class TimeLine extends BaseDdModel<TimeLine> {

//
//    user = models.ForeignKey(to="User", to_field='uid', null=False, on_delete=models.CASCADE)
//    content = models.TextField(null=False)
//    create_time = models.DateTimeField(auto_now_add=True)
//    start_time = models.DateTimeField(auto_now_add=True)
//    end_time = models.DateTimeField(null=False)

    @PrimaryKey
    private String id;

    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    private User user;


    @Column
    private String content;

    @Column
    private Date createTime;

    @Column
    private Date startTime;

    @Column
    private Date endTime;


    public static int TYPE_EVERY = 0;
    public static int TYPE_EVERY_WEEK = 1;
    public static int TYPE_EVERY_MOTH = 2;


    @Column
    private int repeatTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    @Override
    public boolean isSame(TimeLine old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(TimeLine old) {
        return false;
    }

    public int getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(int repeatTime) {
        this.repeatTime = repeatTime;
    }
}
