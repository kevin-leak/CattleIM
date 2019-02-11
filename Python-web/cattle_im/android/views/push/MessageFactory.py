#!/usr/bin/env python
# -*- coding:utf-8 -*- 
# Author: KevinLeak


# 队列信息存储格式
# {
#     "pushId": [{
#         "chatId": "",
#         "fromId": "",
#         "toId": "",
#         "type": "",
#         "info": [{
#             "category": "",
#             "content": ""
#         },
#             {}
#         ],
#         "createTime": ""
#     },
#         {},
#         {}
#     ],
#     "second": []
# }


# 消息回送的确认格式
# {
#     "fromId": "",
#     "toId": "",
#     "info": "ok",
# }
import copy
import json

from android.contract import request_interface
from db.models import User, PushHistory, Conversation, Event


class MessageFactory:
    """
    用来处理信息的解码与组装
    """
    # 指的是我们将该需要push的设备push 完了.我们进行清空处理
    PUSH_MSG = 2
    # 表示当前的返回的queue 是需要添加进消息队列
    MSG = 1
    # 表示当前的返回的queue 是需要清空进消息队列
    CLEAR_MSG = 0

    def __init__(self):
        pass

    @classmethod
    def decode(cls, message):
        """
        消息的基本格式
        [{
            "chatId": "",
            "fromId": "",
            "toId": "",
            "type": "",
            "info": [{
                "category": "",
                "content": ""
            },
                {}
            ],
            "createTime": ""
        },
        {},]
        将发送过的消息解析成 push id  的模式
        :param message: 一个总的消息字典
        :return: 一个字典:
        """
        if message is 'ok':
            print("-----push回送------")
            # 这里是push回送包
            return None, MessageFactory.PUSH_MSG
        elif type(message).__name__ is 'list' and message.index(0)['info'] is 'ok':
            # （这里是一个会话发送一次回送包，表示客户端已经收到信息），做多个会话回送处理
            # 确认回送包
            # [{
            #     "fromId": "",
            #     "toId": "",
            #     "chatId": "",
            #     "info": "ok",
            # },]
            # 用来存储当前已经push成功的 id
            # 要求我们这里，回送消息一起发
            print("-------会话回送-------")
            for msg in message:
                cls.save_db(msg)
            return None, MessageFactory.CLEAR_MSG
        else:
            # 获取push id
            # message_queue = {"pushId": ...}
            print('------消息暂时存储-----')
            message_queue = {}
            print(message)
            # 获取不同会话信息的信息
            for msg in message:
                toId = msg["toId"]
                toPushId = User.objects.get(uid=toId).profile_id
                fromId = msg["fromId"]
                fromPushId = User.objects.get(uid=fromId).profile_id
                # 存储成通用模式
                message_queue[str(toPushId)] = msg
                # 进行数据库的暂时储存
                cls.save_push(fromPushId, msg, toPushId)
            # 这里处理
            return message_queue, MessageFactory.MSG

    @classmethod
    def save_db(cls, msg):
        """
        将信息永久存储，到conversation和evet 表中
        :param msg: 指的是每个会话
        :return:
        """
        toId = msg["toId"]
        toPushId = User.objects.get(uid=toId).profile_id
        fromId = msg["fromId"]
        fromPushId = User.objects.get(uid=fromId).profile_id
        pushHistory = PushHistory.objects.filter(send_id=fromPushId, receive_id=toPushId).first()
        # 获取消息队列, entity，指的是一个会话里面的内容
        entity = eval(pushHistory.entity)
        # 建立conversation
        for info in entity['info']:
            category = int(info['category'])
            content = int(info['content'])
            # "0": "文本",
            # "1": "图片",
            # "2": "语音",
            # "3": "文件"
            # 建立event 对象
            con = Conversation.objects.create(category=category, send_id=fromId,
                                              receive_id=toId, content=content)
            # 建立event 对象
            Event.objects.create(conversation=con, chatId=msg['chatId'], type=msg['type'])

    @classmethod
    def save_push(cls, fromPushId, msg, toPushId):
        """
        将信息存储到PushHistory,暂时储存
        :param fromPushId: 消息来自于那个用户
        :param msg:
        :param toPushId:
        :return:
        """
        # 将信息存储到PushHistory
        # 按push  id 来存储，也为chat id，一个会话一个会话存储
        pushHistory = PushHistory.objects.filter(send_id=fromPushId, receive_id=toPushId).first()
        if pushHistory:
            entity = pushHistory.entity
            entity = eval(entity)
            # 对消息进行扩展
            entity['info'].extend(msg['info'])
            pushHistory.entity = str(json.dumps(entity))
            pushHistory.save()
        else:
            PushHistory.objects.create(send_id=fromPushId, receive_id=toPushId,
                                       entity=str(json.dumps(msg)))

    @classmethod
    def create_beat_heart(cls, user):
        """

        :param user: 当前发送连接web socket 请求的用户
        :return: 返回一个json数据包
        """
        beat_heart = copy.copy(request_interface.wskt_base)
        beat_heart['message'] = 'ok'
        beat_heart['pushId'] = str(user.profile_id)
        return json.dumps(beat_heart)

    @classmethod
    def base_pack(cls, message):
        ret = copy.copy(request_interface.wskt_base)
        ret['message'] = message
        ret['pushId'] = ' '
        return json.dumps(ret)
