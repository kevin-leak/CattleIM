package com.example.netKit.model;


import com.example.common.factory.profile.Profile;
import com.example.netKit.db.BaseDdModel;
import com.example.netKit.db.User;

import java.sql.Date;

/**
 * 1.集合所有用户的信息
 */
public class UserModel implements Profile {


    /**
     * id :
     * username : name
     * phone : 188707421
     * avatar : media/avatars/android/xx.jpg
     * desc :
     * sex : 0
     * alias : 备注
     * friends : 0
     * isFriend : false
     * modifyAt : 2018/12/15
     */

    private String id;
    private String username;
    private String phone;
    private String avatar;
    private String desc;
    private int sex;
    private String alias;
    private int friends;
    private boolean isFriend;
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

    public void setFriends(int friends) {
        this.friends = friends;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }



    // 缓存一个对应的User, 不能被GSON框架解析使用
    private transient User user;

    /**
     * 建立user与数据库的联系，同时建立缓存机制
     */
    public User build() {
        if (user == null) {
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setAvatar(avatar);
            user.setPhone(phone);
            user.setDesc(desc);
            user.setSex(sex);
            user.setFriend(isFriend);
            user.setFriends(friends);
            user.setModifyAt(modifyAt);
            this.user = user;
        }
        return user;
    }

}
