import json
import uuid

from django.contrib.auth.decorators import login_required
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from dwebsocket import accept_websocket

# todo 后期redis进行一个存储，到后面的将历史消息存储到mysql数据库
from android.views.push.MessageFactory import MessageFactory
from db.models import User

'''用来存储当前拥有的websocket对象'''
socket_queue = {}

# 设置 message_queue 的大小
# message_queue = {"pushId": [], "sendId": {}}
# todo 处理，不同的request进行了操作，出现数据混乱的问题
message_queue = {}


# 系统级别的推送
def push_to_all(request):
    user = User.objects.filter(phone='18870742138').first()
    print(str(user.avatar))
    ret = {
        "status": 1,
        "pushId": "",
        "message": "",
    }
    ret_ok = ret
    ret_ok['message'] = 'ok'
    print(socket_queue)
    for q in socket_queue.values():
        q.send(json.dumps(ret_ok, ensure_ascii=False))
    return HttpResponse("ok")


@csrf_exempt
@accept_websocket
def wbskt(request):
    if request.session.get("userId", "") == "":
        print("wbskt: -----> userId 为空")
        return
    while True:
        if not hasattr(request, 'websocket'):
            return
        try:
            # 获取当前发送过来的信息
            message = request.websocket.wait()
        except Exception:
            return
        message_set = eval(message)
        print("-----")
        print(message_set)
        user = User.objects.get(uid=request.session["userId"])
        # 有pushId 存在两种情况，1. 断开重新连接 2. 正常情况
        if message_set['pushId'] != '':
            if message_set['status'] == 0:  # 重新连接操作
                # 更换掉，没有用的web socket
                socket_queue[message_set['pushId']] = request.websocket
                print("-------重启-----------")
                request.websocket.send(MessageFactory.create_beat_heart(user))
            else:
                print("-------发送消息-------")
                websocket = socket_queue[str(user.profile.push_id)]
                send_message("ok", websocket)
                # fixme 先只处理单聊
                dispatch(message_set, user)
        else:
            print("-----------绑定push id------")
            add_pusher(request.websocket, user)


def dispatch(message_set, user):
    """
    根据message，这里的消息指的是
    1. 寻找当前用是否具有websocket对象: 没有--> message_queue --> redis --> mysql
    2. 根据消息的不同进行分发处理 ---> 多线程处理并进行回收 --> 并进行一个处理反馈
    3. 消息回送
    """
    message = message_set['message']
    entity, mode = MessageFactory.decode(message)
    if mode is MessageFactory.MSG:
        # 指的是当前用户发送消息给某个用户
        add_message_queue(entity)
        for pushId, websoket in socket_queue.items():
            if pushId in message_queue.keys():
                print("message arrvial")
                send_message(message=message_queue[pushId], websocket=websoket)
        pass
    elif mode is MessageFactory.CLEAR_MSG:
        # 指的是当前用户点击了查看摸个会话的，反馈回来消息确认这个消息查收成功
        pass
    elif mode is MessageFactory.PUSH_MSG:
        entity = message_set['pushId']
        clear_message_queue(entity)
        # 指的是push 成功


def clear_message_queue(entity):
    """
    用来清除队列消息
    :param entity:
    :return:
    """
    print(entity)
    if entity in message_queue.keys():
        print("-----清楚成功----")
        message_queue.pop(entity)


def add_message_queue(entity):
    """
    如果摸个pusher 像某个 pusher 发送了消息，这个方法提供添加的功能
    :param entity: 格式是：{"pushId": ...}
    :return:
    """
    for push_id, value in entity.items():
        # chat_id = entity[push_id]['chatId']
        if push_id in message_queue.keys():
            # 查看消息是不是同一个会话的
            # if chat_id is message_queue[push_id]['chatId']:
            #     # 这里这样操作可能出现消息的发送出现先后不一致
            #     message_queue[push_id]['info'].append(entity[push_id]['info'])
            message_queue[push_id].append(value)
        else:
            message_queue[push_id] = []
            message_queue[push_id].append(value)


def add_pusher(websocket, user):
    """
    处理 1.消息查询 2.加入消息队列 3. 反馈成功
    :param websocket:
    :param user:
    :return:
    """
    if user:
        push_id = str(user.profile.push_id)
        # 将web socket对象存入socket_queue队列
        socket_queue[push_id] = websocket

        websocket.send(MessageFactory.create_beat_heart(user))

        # 添加完消息队列后，进行第一次数据拉取
        if not message_queue.get(push_id):
            return
        # 如果是在消息队列中，能够找到用户的push id 说明有消息要发，我们在这里给他发送过去
        send_message(message_queue.get(push_id), websocket)
    else:
        print('=====')


def send_message(message, websocket):
    """
    :param message: 是一个list集合
    :param websocket: 要发送给消息的websocket
    :return: 返回一个接口对象
    """
    ret = MessageFactory.base_pack(message)
    print('---发送到另外一用户手中-----')
    print(ret)
    websocket.send(ret)
