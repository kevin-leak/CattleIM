import json
import time
from datetime import date
from functools import wraps

common = {
    "status": 0,
    "result": [],
    "message": "ok",
    "date": time.strftime("%Y-%m-%dT%H:%M:%S", time.localtime())
}

account = {
    "user": {
        "id": "",
        "username": "",
        "phone": "",
        "avatar": "",
        "desc": "",
        "sex": 0,
        "alias": "",
        "friends": 0,
        "isFriend": True,
        "modifyAt": time.strftime("%Y-%m-%dT%H:%M:%S", time.localtime())
    },
    "account": "",
    "isBind": False
}

user = {
    "id": "",
    "username": "",
    "phone": "",
    "avatar": "",
    "desc": "",
    "sex": 0,
    "alias": "",
    "friends": 0,
    "isFriend": True,
    "modifyAt": ""
}


# web socket 发送的基本包
wskt_base = {
    "status": 1,
    "pushId": "",
    "message": []
}

# 消息的传送格式
message = {
    "chatId": "",
    "fromId": "",
    "toId": "",
    "type": "",
    "info": [],
    "createTime": ""
}

message_info = {
    "category": "",
    "content": ""
}
