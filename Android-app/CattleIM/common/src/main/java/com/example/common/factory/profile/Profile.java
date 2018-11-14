package com.example.common.factory.profile;

/**
 * 实现个人的基本资料， me界面用户的基本要求
 */
public interface Profile {

    String getId();

    void setId(String id);

    String getUsername();

    void setUsername(String name);

    String getAvatar();

    void setAvatar(String portrait);


}
