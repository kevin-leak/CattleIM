#  CattleIM

[TOC]













## 项目介绍

整个项目，暂时由我一个人负责，并完成。从需求--->界面设计---> 数据库搭建 ---> 技术选型 --> 技术实现。

<a href="https://github.com/kevin-leak/CattleIM ">github地址</a>

### 项目需求

这是一款以娱乐作业为中心的实时通信app，针对用户是大学生和高中生

设计应该有的功能：聊天，俱乐部，以个人为中心的日程、任务、话题

给一张，一开始对需求对的**头脑风暴图**。

<img src="/IMSoftData/DemandAnalysis/android/demand.png">



### 项目环境搭建

#### 客户端

- 客户端：android，测试用的红米，以及Google自带的虚拟机

- 适配：

  ```groovy
  minSdkVersion 15
  targetSdkVersion 28
  ```

- 第三方框架

  ```groovy
  ext{
      // view 的框架注解
      butterknifeVersion = '8.5.1'
      butterknifeCompilerVesion = '8.8.1'
      // 图片加载库
      glideVersion = '3.7.0'
      circleimageviewVersion = '2.1.0'
      // 权限检测
      easyPMVersion = "0.3.0"
      // 裁剪图片
      ucropVersion = "2.2.0-native"
      // json 转化
      gsonVersion = '2.8.0'
      // 本地数据库
      dbflowVersion = "4.0.0-beta7""
      websocket = "1.3.7"
  }
  ```



#### 服务器端

- 服务器搭建：python + Django

- 导入的第三方包：

  |  package   | vision | lastVision  |
  | :--------: | :----: | :---------: |
  |   Naked    | 0.1.31 |   0.1.31    |
  |  PyMySQL   | 0.9.2  |    0.9.3    |
  |   PyYAML   |  3.13  | 4.2b4.win32 |
  | asn1crypto | 0.24.0 |   0.24.0    |
  | dwebsocket | 0.5.5  |   0.5.10    |
  |  requests  | 2.19.1 |   2.21.0    |
  | setuptools | 39.1.0 |   40.8.0    |
  |    xlrd    | 1.1.0  |    1.2.0    |



## APP效果

- 基本界面显示：用户信息填写，已经实现

  <img src="/IMSoftData/images/launch.png" height="250px" style="height:250px;"><img src="/IMSoftData/images/account.png" height="250px" style="height:250px"><img src="/IMSoftData/images/settings.png" height="250px" style="height:250px"><img src="/IMSoftData/images/profile.png" height="250px" style="height:250px">

- 2019/3/3

  **目前实现**：

  - 加好友(多搜索)，与好友列表展示。

    <img src="/IMSoftData/images/search_user.png" height="250px" style="height:250px"><img src="/IMSoftData/images/friends.png" style="height:250px">

  - 聊天，消息通知，消息列表

    <img src="/IMSoftData/images/conversation_1.png" height="250px" style="height:250px"><img src="/IMSoftData/images/conversation_2.jpeg" height="250px" style="height:250px"><img src="/IMSoftData/images/message_remind.png" height="250px" style="height:250px"><img src="/IMSoftData/images/chat.png" height="250px" style="height:250px">

- schedule 界面显示：**待完善**

  <img src="/IMSoftData/images/schedule.jpeg" height="200px" style="height:200px"><img src="/IMSoftData/images/link_create.png" height="200px" style="height:200px"><img src="/IMSoftData/images/link.png"  height="200px" style="height:200px"><img src="/IMSoftData/images/calendar.png" height="200px" style="height:200px">

## 项目架构

### 客户端

**采用mvp模式建立的包结构**

- netkit ：负责网络端的数据转化和android 本地数据库的操作，也就是model
- factory：负责界面与数据之间的逻辑处理，也就是presenter
- app：负责界面的处理，也就是我们的View
- common：发一些公用的资源，以及可以在其他项目中用到的包
- lang、face_res：目前打算用来处理国际化，以及表情包的作用，后面遇到商城的页面还会添加一个js的包，看后面有没有时间写

<img src="/IMSoftData/ArchitectureAnalysis/appArchitecture.png" height="250px" style="height:250px">



### 服务器端

分包比较简单，为了以后对web端的扩张，我单独把数据建立了一个包，再自定义三个包。

- android：主要处理android的逻辑，里面又分：api、contract、view\push、以及根据客户端需求建立对应的处理包
- db：数据库
- web：后面写网页要用的包



## 数据库搭建

这一块的关系，还是需要梳理一段时间，后期可能进行一个修改



<img src="/IMSoftData/DatabaseDesign/pythonWeb/BackendDatabase.png">





## 技术难点

### 推送平台的搭建

按照自己的理解，稍微参考了一下极光推送的搭建模式

先给个流程图

<img src="/IMSoftData/ArchitectureAnalysis/push/android_push.png" style="">

#### 客户端

实际用上的类是：**netkit 包下的 net.push**

- contract 包是来定义发送数据包的协议的

  - MessageContract：定义了基础的数据包的协议，比如心跳包，回送包，确认包

  - PushClientContract：定义了监听反馈的Listener，同时定义了Client 应该具有的功能

    大多是一些状态码，发送消息的接口，过滤消息的方法。

- pieces 是具体的协议包

- PushClient与ClientHelper 一起实现数据的反馈与发送，将数据传送到PushService

  这里处理的有点冗余，在设计包回送的监听器里面比较复杂

  - PushClient：主要是管理消息发送接口以及回送到android其他包，以及连接问题

  - ClientHelper ：负责对后端回送的数据进行过滤标记，并信息作出一定的拦截

- ConversationFactory：用来根据factory传过来的参数，生成对应的对话，向后端发送，后端进行处理

- 在app包里面，就是EventReceiver服务，对netkit中PushService 传来的数据进行一个分发

##### 进程保活

还需要修改，有点简陋

- 监听流量与WiFi开关的广播
- 监听手机启动开关
- 只启用了一个service，如果后台被清除，则无法保住

一旦清除后天，数据将无法同步，会发送一条信息给后台，使进行当前push的下线

#### 服务器

对消息进行筛选与判别，前期做的是，后期还需要添加redis

- 定义一个**message_queue**，键为pushID，具体的看下面的api，结构还是很重要
- 定义一个**websocket队列**，键为pushId，具体的看下面的api，结构还是很重要
- 当第一次心跳时
  - 将websocket对象添加到websocket队列里面
  - 在Profile表中修改pushID的绑定状态，回送确认包，附加账户信息。
  - 拉去发给自己的消息，并清除message_queue队列中的消息，回送给客户端
    - 发送失败，则将需要发送对象在websocket队列中进行一个清理
    - 发送成功，清空message_queue中自己的消息，在PushHistory表中拉去消息到message_queue
- 当消息来的时候
  - 先对消息回送确认，消息发送成功
  - 对消息进行解析，处理不同的消息
    - **客户端用户注销**：websocket队列进行相应的清空
    - **消息阅读确认**：message_queue中自己的消息进行清空，通过表PushHistory进行向具体的表进行处理，比如存储到Conversation表，存储到Chat 表中，或者LinkTask表中，具体如何根据信息的type值来确定，具体看api定义的数据结构
    - **如果是正常消息**：存储在消息队列里面，同时存储到数据表**PushHistory**（是于两个推送id来作为唯一的索引，具体的表结构，看上面的数据库的建立），否则回送失败。搜索websocket队列中需要推送的对象，检测是否可用，进行消息推送
- 重新连接：这里只需要做数据包重新发送，以及pushId 的绑定状态的改变



#### 待处理及方案

暂时想到的，未做的，以及处理方案

- 消息爆炸：一个人连续发送多条，为了减小链路延迟，应该先在前端显示，打包发送给服务器，服务器对消息进行派送。
- 消息比重分配：这里遇到如果先发了一个图片，紧接着发了一个文本消息。我们不管在服务器还是客户端，可以建立一个不同属性的消息队列，设置不同信息的权重，根据他的权重来发送。这个可以模仿进程的调度算法
- 消息分片：像这种超大文本，需要将其分片再发送



### 单聊功能

这里涉及的是消息的发送与展示，以及event(对话消息)条目的形成

在处理消息重复，后端消息的推送中，对消息的状态以及event列表的状态还是费了一点功夫

- 这里设计的是，消息对话的id是一样的每个用户之间的交互，存的conversation id是一样的，但event是不一样的

- event是根据conversation抽象出来的，因为各个用户创建的conversation的时间的是不一样的，有的可以还把消息记录给删除了这样就无法复用原来的event id，这样就会出现于对方之前就出现对话而本用户没有记录这种情况下，我采用的是丢弃，本用户重新建立一个进行调度，这样有一个弊端就是检索起来=是不是一个event有点麻烦，这个处理起来要特别小心。

- 在数据本地化的时候，因为数据的是杂乱的每次都需要检测是否存在，否则建立

- 在回送数据确定包的时候，我们采用的是如果消息列表被点击，则情况一次，向后端发送一次确认包，本地数据进行一次改变，而但正在聊天界面的时候，本地化数据直接设置为已读，在聊天界面被销毁的时候发送一下消息确认包

附上一张图

<img src="/IMSoftData/ProblemLogic/chat/chat_logic.png" width="500px" style="width:500px">

### 数据更新通知与缓存

处理的问题主要是

- 在朋友之间的聊天消息的更新
- 添加好友的通知
- 消息队列的更新
- 消息提示的更新

差不多从这三块抽象出来一个更新的思想，后期再其他比如日程的建立，也是以这种方式来更新

但要采取怎么样的模式与方法呢？？根据以前做的实时通信有两个相似的方案。

#### 方案

1. 将发送消息的输入的那一块view。进行一个封装，当消息发送的时候，通知所有需要更新的界面

   先要实现订阅。对于后端的消息也是这样，采用**订阅者模式**。

2. 在本地数据存储的时候，不管是本地要产生的数据还是服务器要产生的数据，都建立一个观察者，用来通知消息，这采用的是**观察者模式**

仔细分析一下：观察者模式的订阅者与发布者之间是存在依赖的，而发布/订阅模式则不会。

我们在发送消息的时候，采用的是mvp模式，发送消息的是自己，先本地存储，再展示订阅者也是自己，这里也就一大坨了；而订阅者模式，在处理后端发来的消息的时候可能会解耦更多，方便做通知栏的处理，但就显得比较零碎

本项目采用的是观察者模式：

这个东西的设计原型类似于邮政的取件箱子，但箱子是长期固化属于个来类型的：

1. 我们在网上买了一个物件(消息)，请求淘宝(服务器，或者本地)
2. 分配中心(**DispatchCenter**)分配派送员(**DbHelper**)；
3. 不同的派送员(好友列表派送，消息派送)来到学校的邮政箱子(基础仓库**BaseDbRepository**)
4. 派送员对物件进行扫描(**scanner**)，对初始仓库赋予物品的信息，使他成为特殊的仓库
5. 派送员把物件存好，关上门，开始对数据进行监控(这物品的**listener**)
6. 旁边的电脑就将对应得仓库信息加载到电脑(**观察容器**)，观察看到有信息来了就通知请求群可以去本地化的数据了。

<img src="/IMSoftData/ArchitectureAnalysis/db/db_observer.png" width="400px" style="width:400px">

附上一张好友信息列表的更新的流程图：

这里处理数据的本地化，开启了子线程，需要注意的是在更新界面的时候要再UI线程

<img src="/IMSoftData/ArchitectureAnalysis/db/dispatch_center.png"  width="600px" style="width:600px">



### RecycleView的封装

- 建立数据更新协议，这里建立一个缓冲机制

- 建立holder的点击、长按事件，在view 的内部定义协议，实现可以复写当前方法

- 重写多个添加数据的方法：加一个，建一个列表

- 为了利用recycle 的复用性，我们将type，转化为布局的资源文件，并且可以由子类复写。

- 实现holder与adapter的绑定，定义抽修的接口，要求子类复写holder，这样就是实现了

  针对并不用的数据，采用不同的布局，以及不同的操作，而大部分的相同操作有被隐藏

  好比：消息列表与好友列表

- 这样同时可以以每一个holder为一个view，实现mvp模式





















## API 文档

**还待更新和修改，还有许多功能在开发中**



### 公共访问接口

```json
{
 "status": 0,
  "result": [],
  "message": "",
  "Date": "2018/3/6"
}
```

#### login 登入接口约定

> 登入的信息：

```json
{
    "phone": 18870742138,
    "password": "lkkzbl"
}
```

#### session设定

```
request.session["userId"] = str(user.uid)
# 获取
if request.session.get("userId", "") == "":
   return error_response.session_error()
holder_id = request.session["userId"]
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
  "phone":"110",
  "password": "199shadjfk"
}
```

### 账户信息

```json
{
  "user":{
      "id": "",
      "username": "name",
      "phone": "188707421",
      "avatar": "media/avatars/android/xx.jpg",
      "desc": "",
      "sex": 0,
      "alias": "备注",
      "friends": 0,
      "isFriend": false,
      "modifyAt": "2018/12/15"
  },
  "account": "",
  "isBind": true
}

```



### 账户信息完善接口定义

#### android

```json
{
    "userId":"",
    "avatar":"media/avatars/android/xx.jpg",
    "username":"",
    "desc":"",
    "sex": 0
}
```

#### web

```json
{
  "user":{
      "id": "",
      "username": "name",
      "phone": "188707421",
      "avatar": "media/avatars/android/xx.jpg",
      "desc": "",
      "sex": 0,
      "alias": "备注",
      "friends": 0,
      "isFriend": false,
      "modifyAt": "2018/12/15"
  },
  "account": "",
  "isBind": true
}
```

### 存储文件接口

from

```json
{
  "name": "当前文件的名字",
  "content": "经过md5的加密"
}
```

to

```json
{
  "path": "media/avatars/android/xx.jpg"
}
```



### 推送

------

~~规定客户端，发送消息，每次只能发送一种会话的消息，消息可以联系发送~~

~~而服务器回送消息，是可以由不同的会话~~



#### 心跳包/回送包

```python
message_set = {
    "status": 1,
    "pushId": "",
    "message": ""
}
```

 message约定

- 消息包：message=“ok", 
- 启动包：message=“start”
- 重启包：message=“restart”

#### message

可以为空(心跳包)，也可以是一个字符串(反馈),  正式通信如下：

```json
[{
	"chatId": "",
	"fromId": "",
	"toId": "",
	"type": "",
	"info": {
			"category": "",
			"content": ""
		},
	"createTime": ""
},{}]
```

chatId：指的是普通对话， tag，group，link

这里重新规定，info 不能为数组，每次发一条信息，我们都在message 里面增加一个 字典

type

```json
{ 
	"0":"系统消息",
	"1":"普通消息",
	"2":"请求消息",
	"3":"主题消息",
	"4":"公告消息",
	"5":"任务消息",
	"6":"关联消息",
    "7":"群消息"
}
```

category

```json
{
	"0":"文本",
	"1":"图片",
	"2":"语音",
	"3":"文件"
}
```



消息回送确认包

```json
{
	"fromId": "",
	"toId": "",
    "chatId":"",
    "content": "ok"
}
```

回送包和消息包，分开来发

#### message_queue(消息队列)

```python
message_queue = {
	"pushId": [{
			"chatId": "",
			"fromId": "",
			"toId": "",
			"type": "",
			"info": {
					"category": "",
					"content": ""
					},
			"createTime": ""
		},
		{},
	],
	"second": []
}
```



### search搜索

user的搜索

```python 
# 使用get请求，传入username 或则 phone
```

返回接口为空的情况

```json
{"status": 0, "result": [], "message": "ok", "date": "2018-11-14T21:40:56"}
```



#### friends

```
# 好友关系建立用get，请求,传入用户的id，将好友账户信息反馈
/(?P<field>\w+)/(?P<page>\d+)/
```



#### Group

```python
# get方式传入name
```



