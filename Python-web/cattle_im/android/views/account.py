import base64
import json
import time

from django.contrib import auth
from django.contrib.auth import authenticate
from django.contrib.auth.hashers import make_password
from django.http import HttpResponse
from django.views.decorators.http import require_POST
from pymysql import Date

from android.contract import respone_code
from android.contract.request_interface import common, account

from android.utils.phone_tools import check_phone
from db.models import User, Friends, Profile


@require_POST
def login(request):
    """
    {
    "phone": 18870742138,
    "password": "lkkzbl"
    }
    """
    info = eval(request.body)
    print(info)
    ret_info = common

    if info['password'].strip() == '':
        ret_info['status'] = respone_code.NULL_PASSWORD
    elif len(info['password']) < 6 or len(info['password']) > 16:
        ret_info['status'] = respone_code.FORMAT_ERROR_PASSWORD_LENGTH
    elif info['phone'].strip() == '':
        ret_info['status'] = respone_code.NULL_PHONE
    elif len(info['phone']) < 6 or len(info['phone']) > 16:
        ret_info['status'] = respone_code.FORMAT_ERROR_PHONE_LENGTH
    elif check_phone(info['phone']) is None:
        ret_info['status'] = respone_code.FORMAT_ERROR_PHONE
    else:
        user = User.objects.filter(phone=info['phone']).first()
        if user:
            user = authenticate(username=user.username, password='lkkzbl123888')
        else:
            ret_info['status'] = respone_code.NULL_USER
            return HttpResponse(json.dumps(ret_info))
        if user:
            print(user.username)
            info.pop('password')
            info['avatar'] = "user.avatar"  # todo 处理解码问题
            info['username'] = user.username
            ret_info['result'] = info
            if user.is_active:
                ret_info['status'] = respone_code.ERROR_REPEAT_LOGIN
                return HttpResponse(json.dumps(ret_info))
            auth.login(request, user)
        else:
            ret_info['status'] = respone_code.ERROR_PASSWORD

    return HttpResponse(json.dumps(ret_info))


@require_POST
def register(request):
    """
    {
      "phone":"18870742138",
      "username": "kevin",
      "password": "199shadjfk",
      "avatar": "sdajfklajflksdaj;"
    }
    """
    info = eval(request.body)
    # 获取公共的返回模块
    ret_info = common
    # 获取注册时产生的结果
    result = account

    same_phone = User.objects.filter(phone=info['phone']).first()
    same_username = User.objects.filter(username=info['username']).first()

    # 处理password， name, password, 为空的情况
    # 处理字段相同问题
    if info['password'].strip() == '':
        ret_info['status'] = respone_code.NULL_PASSWORD
    elif len(info['password']) < 6 or len(info['password']) > 16:
        ret_info['status'] = respone_code.FORMAT_ERROR_PASSWORD_LENGTH
    elif info['avatar'].strip() == '':
        ret_info['status'] = respone_code.NULL_AVATAR
    elif info['phone'].strip() == '':
        ret_info['status'] = respone_code.NULL_PHONE
    elif len(info['phone']) < 6 or len(info['phone']) > 16:
        ret_info['status'] = respone_code.FORMAT_ERROR_PHONE_LENGTH
    elif check_phone(info['phone']) is None:
        ret_info['status'] = respone_code.FORMAT_ERROR_PHONE
    elif len(info['username']) > 15:
        ret_info['status'] = respone_code.FORMAT_ERROR_USER_LENGTH
    elif info['username'].strip() == '':
        ret_info['status'] = respone_code.NULL_USERNAME
    elif same_phone is not None:
        ret_info['status'] = respone_code.SAME_PHONE
    elif same_username is not None:
        ret_info['status'] = respone_code.SAME_USERNAME
    else:

        # todo 解密
        # 进行一个Django自带的一个加密功能
        info['password'] = make_password(info['password'].strip())
        user = User.objects.create(**info)
        user.profile_id = Profile.objects.create(sex='1').id
        # 进行了修改需要提交，不然后面创建friend关系没有用
        user.save()

        # 将密码弹出
        info.pop('password')
        # 拼接数据
        user_collect = result.get('user')
        user_collect.update(info)
        user_collect['id'] = str(user.uid)
        user_collect['sex'] = user.profile.sex
        user_collect['alias'] = user.username
        user_collect['modifyAt'] = time.strftime('%Y-%m-%dT%H:%M:%S', time.localtime(time.time()))
        Friends.objects.create(origin_id=user, target_id=user, alias=user.username)

        result['account'] = user.phone
        result.get('user').update(user_collect)
        ret_info['result'] = result

        ret_info['status'] = 1
    return HttpResponse(json.dumps(ret_info))


