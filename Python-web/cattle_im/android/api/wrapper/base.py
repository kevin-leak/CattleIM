import copy
import time

from android.api.factory import user_card
from datetime import date

from android.contract import response_code, request_interface
from functools import wraps
import json

# def login_wrap(func):
#     def inner(request):
#         # 处理数据错误的逻辑
#         # 进行数据库的缓存
#         # 对返回数据的取出
#         ret = func(request)  # 并且字符串转为json, 以及其他一些文件的处理
#         # 对数据进行加密，封装头信息
#         return ret
#
#     return inner
#
#
# def register_wrap(func):
#     def inner(request):
#         # 处理数据错误的逻辑
#         # 进行数据库的缓存
#         # 对返回数据的取出
#         user_card.Card(eval(request.body))
#         ret = func(request)  # 并且字符串转为json, 以及其他一些文件的处理
#         # 对数据进行加密，封装头信息
#         return ret
#
#     return inner

from django.http import HttpResponse

from db.models import User, Friends


def base_contract(contract=None):
    """
    用来制定基础的协议
    :param contract: 基础协议默认为common
    :return: 返回加工过的数据
    """
    if contract is None:
        contract = request_interface.common

    def outer(func):
        @wraps(func)
        def inner(*args, **kwargs):
            ret = func(*args, **kwargs)
            if ret:
                contract["status"] = response_code.SUCCESS_STATUS
            else:
                contract["status"] = response_code.FAILURE_STATUS
            contract["result"] = ret
            return contract

        return inner

    return outer


def account_creator(holder_id, current_id):
    """
    则获取 current_id ，自身的信息，以及与holder_id的关系
    建立账户信息，与下面的user-creator 就是有无设备绑定的问题
    :param ret: 定义的用户接口类型， 不允许自定义
    :param holder_id: 当前用户登入的id
    :param current_id: 此时连接传递过来的id
    :return: 返回一个规定的接口集合类型
    """

    ret = copy.deepcopy(request_interface.account)
    user = User.objects.get(uid=current_id)
    if user:
        ret["user"]["id"] = str(user.uid)
        ret["user"]["username"] = user.username
        ret["user"]["phone"] = user.phone
        ret["user"]["avatar"] = str(user.avatar)
        ret["user"]['modifyAt'] = time.strftime('%Y-%m-%dT%H:%M:%S', time.localtime(time.time()))
        # 当前用户自己的好友， 别人添加的，不一定是自己的好友，可能是单向的
        ret["user"]['friends'] = Friends.objects.filter(origin=user).count()
        ret["account"] = user.phone

        if user.profile is not None:
            if user.profile.desc:
                ret["user"]["desc"] = user.profile.desc
            ret["user"]["sex"] = user.profile.sex
            ret["isBind"] = user.profile.is_bind

        if holder_id == current_id:
            ret["user"]['isFriend'] = True
        else:
            target = User.objects.get(uid=holder_id)
            if target:
                friends = Friends.objects.filter(origin=user, target=target).first()
                if friends:
                    ret["user"]["isFriend"] = True
                    ret["user"]["alias"] = friends.alias
    return ret


def user_creator(holder_id, current_id):
    """
    建立用户信息，用来处理查询好友的时候用的，和建立好友关系后生产好友信息。
    :param holder_id: 当前用户的id
    :param current_id: 指的是查询或者建立的id
    :return:
    """

    ret = copy.deepcopy(request_interface.user)

    user = User.objects.get(uid=current_id)
    if user:
        ret["id"] = str(user.uid)
        ret["username"] = user.username
        ret["phone"] = user.phone
        ret["avatar"] = str(user.avatar)
        ret['modifyAt'] = time.strftime('%Y-%m-%dT%H:%M:%S', time.localtime(time.time()))
        # 当前用户自己的好友， 别人添加的，不一定是自己的好友，可能是单向的
        ret['friends'] = Friends.objects.filter(origin=user).count()

        if user.profile is not None:
            if user.profile.desc:
                ret["desc"] = user.profile.desc
            ret["sex"] = user.profile.sex

        if holder_id == current_id:
            ret['isFriend'] = True
        else:
            target = User.objects.get(uid=holder_id)
            if target:
                friends = Friends.objects.filter(origin=user, target=target).first()
                if friends:
                    ret["isFriend"] = True
                    ret["alias"] = friends.alias
                else:
                    ret["isFriend"] = False
                    ret["alias"] = ""
            else:
                ret["isFriend"] = False
                ret["alias"] = ""

    return ret
