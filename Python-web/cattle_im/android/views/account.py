import base64
import json

from django.contrib import auth
from django.contrib.auth import authenticate
from django.contrib.auth.hashers import make_password
from django.http import HttpResponse
from django.views.decorators.http import require_POST

from android.contract import respone_code
from android.contract.request_interface import common

from android.utils.phone_tools import check_phone
from db.models import User


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
    print(json.dumps(info))
    ret_info = common
    # print('from', info)

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
    elif len(info['username']) > 10:
        ret_info['status'] = respone_code.FORMAT_ERROR_USER_LENGTH
    elif info['username'].strip() == '':
        ret_info['status'] = respone_code.NULL_USERNAME
    elif same_phone is not None:
        ret_info['status'] = respone_code.SAME_PHONE
    elif same_username is not None:
        print("---------")
        ret_info['status'] = respone_code.SAME_USERNAME
    else:
        # todo 解密
        info['password'] = make_password(info['password'].strip())
        print(info)
        # User.objects.create(**info)
        # User.objects.get(phone='18870742138')
        info.pop('password')
        ret_info['result'] = info

    # file_str = open('2.jpg', 'wb')
    msg = 'pp'
    # file_str.write(base64.b64decode(info['avatar']))
    # file_str.close()
    print('to', json.dumps(ret_info))
    return HttpResponse(json.dumps(ret_info))
