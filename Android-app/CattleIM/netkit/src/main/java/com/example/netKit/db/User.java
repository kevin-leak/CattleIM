package com.example.netKit.db;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.common.factory.profile.Profile;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;
import java.util.Objects;

/**
 * 建立用户表,
 * 先定义了很多字段
 */

@Table(database = AppDatabase.class)
public class User extends BaseDdModel<User> implements Profile {

    public static final int SEX_MAN = 1;
    public static final int SEX_WOMAN = 2;

    // 主键
    @PrimaryKey
    private String id;
    @Column
    private String username;
    @Column
    private String phone;
    @Column
    private String avatar;

    // 个人的描述简介
    @Column
    private String desc;
    @Column
    private int sex = 0;

    // 我对某人的备注信息，也应该写入到数据库中
    @Column
    private String alias;

    // 好友的数量
    @Column
    private int friends;


    // 我与当前User的关系状态，是否已经关注了这个人
    @Column
    private boolean isFriend;

    // 时间字段
    @Column
    private Date modifyAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getFriends() {
        return friends;
    }

    public void setFriends(int friend) {
        this.friends = friend;
    }


    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return sex == user.sex
                && friends == user.friends
                && isFriend == user.isFriend
                && Objects.equals(id, user.id)
                && Objects.equals(username, user.username)
                && Objects.equals(phone, user.phone)
                && Objects.equals(avatar, user.avatar)
                && Objects.equals(desc, user.desc)
                && Objects.equals(alias, user.alias)
                && Objects.equals(modifyAt, user.modifyAt);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean isSame(User old) {
        // 主要关注Id即可
        return this == old || Objects.equals(id, old.id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean isUiContentSame(User old) {
        // 显示的内容是否一样，主要判断 名字，头像，性别，是否已经关注
        return this == old || (
                Objects.equals(username, old.username)
                        && Objects.equals(avatar, old.avatar)
                        && Objects.equals(sex, old.sex)
                        && Objects.equals(isFriend, old.isFriend)
        );
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", desc='" + desc + '\'' +
                ", sex=" + sex +
                ", alias='" + alias + '\'' +
                ", friends=" + friends +
                ", isFriend=" + isFriend +
                ", modifyAt=" + modifyAt +
                '}';
    }

}
