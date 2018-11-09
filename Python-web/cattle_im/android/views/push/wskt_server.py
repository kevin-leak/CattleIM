import json
import uuid

from django.contrib.auth.models import User
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from dwebsocket import accept_websocket

# todo 后期redis进行一个存储，到后面的将历史消息存储到mysql数据库


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
        q.send(json.dumps(ret_ok))
    return HttpResponse("ok")


# 进行包打包
def pack_ret(wb_id, message, status=1):
    pack = ret
    pack['status'] = status
    pack['pushId'] = wb_id
    pack['message'] = message
    return pack


@csrf_exempt
@accept_websocket
def wbskt(request):
    while True:
        if not hasattr(request, 'websocket'):
            return
        message = request.websocket.wait()
        message_set = eval(message)
        # 有pushId 存在两种情况，1. 断开重新连接 2. 正常情况
        if message_set['pushId'] != '':
            if message_set['status'] == 0:  # 重新连接操作
                queue[message_set['pushId']] = request.websocket
                request.websocket.send(json.dumps(pack_ret(message_set['pushId'], message='重连成功')))
            else:
                dispatch(message, request.websocket, request.user)
        else:
            add_pusher(message_set, request.websocket, request.user)


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
    message['push_id'] = user.profile.push_id
    queue[message['pushId']] = websocket
    user.profile.is_bind = True
    websocket.send(json.dumps(pack_ret(wb_id=str(message['pushId']), message='连接成功')))
    # 拉取消息，后期处理redis, 以及mysql，
    for message in message_queue[message['push_id']].items():
        websocket.send(json.dumps(message))
