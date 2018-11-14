import json
import uuid

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from dwebsocket import accept_websocket

# todo 后期redis进行一个存储，到后面的将历史消息存储到mysql数据库
from db.models import User

'''用来存储当前拥有的websocket对象'''
queue = {}

'''用来作为心跳和重连包的反馈'''
ret = {
    "status": 1,
    "pushId": "",
    "message": ""
}

# 设置 message_queue 的大小
message_queue = {}


# 系统级别的推送
def push_to_all(request):
    user = User.objects.filter(phone='18870742138').first()
    print(str(user.avatar))
    ret_ok = ret
    ret_ok['message'] = 'ok'
    print(queue)
    for q in queue.values():
        q.send(json.dumps(ret_ok, ensure_ascii=False))
    return HttpResponse("ok")


def pack_ret(wb_id, message, status=1):
    """
    进行包打包
    :param wb_id: 推送的id
    :param message: 推送的信息
    :param status: 推送的状态
    :return:
    """
    pack = ret
    pack['status'] = status
    pack['pushId'] = wb_id
    pack['message'] = message
    return pack


@csrf_exempt
@accept_websocket
def wbskt(request):
    if request.session.get("userId", "") == "":
        print("----->")
        return
    while True:
        if not hasattr(request, 'websocket'):
            return
        # 处理没有登入的情况
        # if not hasattr(request, "user"):
        #     request.websocket.send(pack_ret())
        try:
            message = request.websocket.wait()
        except Exception:
            return

        message_set = eval(message)
        print(message_set)
        user = User.objects.get(uid=request.session["userId"])
        # 有pushId 存在两种情况，1. 断开重新连接 2. 正常情况
        if message_set['pushId'] != '':
            if message_set['status'] == 0:  # 重新连接操作
                queue[message_set['pushId']] = request.websocket
                request.websocket.send(json.dumps(pack_ret(message_set['pushId'], message='重连成功')))
            else:
                dispatch(message, request.websocket, user)
        else:
            add_pusher(message_set, request.websocket, user)


def dispatch(message, websocket, user):
    """
    根据message，
    1. 寻找当前用是否具有websocket对象: 没有--> message_queue --> redis --> mysql
    2. 根据消息的不同进行分发处理 ---> 多线程处理并进行回收 --> 并进行一个处理反馈
    3. 消息回送
    """
    pass


def add_pusher(message, websocket, user):
    """处理 1.消息查询 2.加入消息队列 3. 反馈成功 """
    if user:
        print('=====>', user.username)
        message['pushId'] = str(user.profile.push_id)
        print(str(user.profile.push_id))
        queue[message['pushId']] = websocket
        user.profile.is_bind = True
        print(json.dumps(pack_ret(wb_id=message['pushId'], message='连接成功'), ensure_ascii=False))
        websocket.send(json.dumps(pack_ret(wb_id=message['pushId'], message='连接成功')))
        # 拉取消息，后期处理redis, 以及mysql，
        if not message_queue.get(message['pushId']):
            return
        for message in message_queue[message['pushId']].items():
            websocket.send(json.dumps(message))
    else:
        print('=====')
