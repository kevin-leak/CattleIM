[TOC]

----


### 公共访问接口
```json

{
 "status": 0,
  "result": [],
  "message": ""
}
```

#### login 登入接口约定
>登入的信息：
```json

{
    "phone": 18870742138,
    "password": "lkkzbl"
}
```



#### 公共的状态码

```
SUCCESS_STATUS = 1								# 操作正确
FAILURE_STATUS = 0								# 操作失败
NULL_PASSWORD = 6000							# 密码为空
NULL_USERNAME = 6001							# 用户密码为空
NULL_AVATAR = 6002								# 头像path为空
NULL_PHONE = 6003								# 电话号码为空
NULL_USER = 6004								# 用户为空
SAME_USERNAME = 7000							# 用户名字已经存在
SAME_PHONE = 7001								# 用户电话号码已经存在
FORMAT_ERROR_PHONE = 8000						# 电话号码格式错误
FORMAT_ERROR_PHONE_LENGTH = 8001				# 电话号码长度有问题
FORMAT_ERROR_PASSWORD = 8002					# 用户密码错误
FORMAT_ERROR_PASSWORD_LENGTH = 8003				# 用户的密码长度不符合要求
FORMAT_ERROR_USER_LENGTH = 8004					# 用户的名字长度不符合要去
FORMAT_ERROR_FILE = 8005						# 传入的file文件有问题
ILLEGAL_USERNAME = 9000							# 不合法的用户名
ERROR_PASSWORD = 10000							# 密码错误
ERROR_REPEAT_LOGIN = 11000						# 重复登入
ERROR_NET = 11001								# 网络错误


```



#### register 传入信息接口约定

```json
{
  "phone":"18870742138",
  "username": "kevin",
  "password": "199shadjfk",
  "avatar": "sdajfklajflksdaj;"
}
```


### 反馈用户信息接口
````json

{
  "username": "name",
  "phone": "188707421",
  "avatar": "9999999ikjlk aksdmkljfckasjmfklmasklmfksalmjfkasjfkljaslkdfjklsajfkljaskldjfklsa"

}
````

### 存储文件接口

from
```json
{
  "name": "ksadjk",
  "content": "asdfjkal"
}
```

to
```json
{
  "code": 800,
  "path": "aksdmjalskjl;k"
}
```
