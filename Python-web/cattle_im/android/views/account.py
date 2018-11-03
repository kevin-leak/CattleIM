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
    ret_info = common
    result = account

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
        print(user.username, info['password'])
        if user:
            user = authenticate(username=user.username, password=info['password'])
            if user:
                print(user.username)
                result_user = result.get('user')
                result_user['id'] = str(user.uid)
                result_user['username'] = user.username
                result_user['phone'] = user.phone
                result_user['avatar'] = 'media/' + str(user.avatar)
                result_user['desc'] = user.profile.desc
                result_user['sex'] = user.profile.sex
                result_user['alias'] = user.username
                result_user['friends'] = Friends.objects.filter(origin_id=user).count()
                result_user['isFriend'] = True
                result_user['modifyAt'] = time.strftime('%Y-%m-%dT%H:%M:%S', time.localtime(time.time()))
                result.update(result_user)
                result['account'] = user.phone
                result['isBind'] = True
                ret_info['result'] = result
                ret_info['status'] = respone_code.SUCCESS_STATUS
                auth.login(request, user)
            else:
                ret_info['status'] = respone_code.ERROR_PASSWORD
        else:
            ret_info['status'] = respone_code.NULL_USER
            return HttpResponse(json.dumps(ret_info))
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
    print(info)
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

        # 进行一个Django自带的一个加密功能
        info['password'] = make_password(info['password'].strip())
        info['avatar'] = info['avatar'].strip('media/')[1]
        user = User.objects.create(**info)
        #  todo 后期进行一个修改,在更新个人信息处
        user.profile_id = Profile.objects.create().id
        # 进行了修改需要提交，不然后面创建friend关系没有用
        user.save()

        # 将密码弹出
        info.pop('password')
        # 拼接数据
        result_user = result.get('user')
        result_user.update(info)
        result_user['id'] = str(user.uid)
        result_user['sex'] = user.profile.sex
        result_user['desc'] = user.profile.desc
        result_user['alias'] = user.username
        result_user['modifyAt'] = time.strftime('%Y-%m-%dT%H:%M:%S', time.localtime(time.time()))
        Friends.objects.create(origin_id=user, target_id=user, alias=user.username)

        result['account'] = user.phone
        result.get('user').update(result_user)
        ret_info['result'] = result

        ret_info['status'] = respone_code.SUCCESS_STATUS

        auth.login(request, user)
    return HttpResponse(json.dumps(ret_info))


