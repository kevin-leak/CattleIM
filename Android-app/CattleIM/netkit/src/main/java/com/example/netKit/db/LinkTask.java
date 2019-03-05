package com.example.netKit.db;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

@Table(database = AppDatabase.class)
public class LinkTask  extends BaseDdModel<TimeLine>{


    public static final int TOPIC = 3;
    public static final int NOTICE = 4;
    public static final int TASK = 5;


    public static final int TASK_PHOTOS = 1;
    public static final int TASK_FILES = 3;


//
//    lid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
//    create = models.ForeignKey(to="User", to_field="uid", on_delete=models.CASCADE)
//    group = models.ForeignKey(to=Group, null=True, on_delete=models.CASCADE)
//    tag = models.ForeignKey(to=Tag, null=True, on_delete=models.CASCADE)
//    type = models.IntegerField()
//    content = models.TextField(null=False)
//    attach = models.CharField(max_length=255)
//    member_count = models.IntegerField()
//    create_time = models.DateTimeField(auto_now_add=True)
//    start_time = models.DateTimeField(auto_now_add=True)
//    end_time = models.DateTimeField(null=False)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PrimaryKey
    private String id;



    @Override
    public boolean isSame(TimeLine old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(TimeLine old) {
        return false;
    }
}
