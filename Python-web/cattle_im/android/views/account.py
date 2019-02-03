import base64
import json
import time

from django.contrib import auth
from django.contrib.auth import authenticate, logout
from django.contrib.auth.hashers import make_password
from django.http import HttpResponse
from django.views.decorators.http import require_POST

from android.contract import response_code


@require_POST
def login(request):
    act = Account(request)
    ret = json.dumps(act.to_login(), ensure_ascii=False)
    print(ret)
    return HttpResponse(ret)


@require_POST
def register(request):
    act = Account(request)
    return HttpResponse(json.dumps(act.to_register(), ensure_ascii=False))


@require_POST
def complete_account(request):
    act = Account(request)
    return HttpResponse(json.dumps(act.complete_account(), ensure_ascii=False))


def out(request):
    logout(request)
    ret = copy.deepcopy(request_interface.common)
    ret['result'] = "ok"
    ret['status'] = 1
    print(ret)
    return HttpResponse(json.dumps(ret, ensure_ascii=False))


from android.contract import request_interface
import copy
from android.api.factory import user_card
from android.utils.phone_tools import check_phone
from db.models import User, Profile


class Account:

    def __init__(self, req):
        """
        piece 获取从android获取的数据，并将其转化为字典
        base_ret account 反馈的基础信息
        :param req:
        """
        self.piece = eval(req.body)
        self.req = req
        self.base_ret = copy.deepcopy(request_interface.common)
        self.base_ret['status'] = response_code.SUCCESS_STATUS

    def password_filter(self):
        if self.piece['password'].strip() == '':
            self.base_ret['status'] = response_code.NULL_PASSWORD
        elif len(self.piece['password']) < 6 or len(self.piece['password']) > 16:
            self.base_ret['status'] = response_code.FORMAT_ERROR_PASSWORD_LENGTH
        if self.base_ret['status'] != response_code.SUCCESS_STATUS:
            return False

    def phone_filter(self):
        if self.piece['phone'].strip() == '':
            self.base_ret['status'] = response_code.NULL_PHONE
        elif len(self.piece['phone']) < 6 or len(self.piece['phone']) > 16:
            self.base_ret['status'] = response_code.FORMAT_ERROR_PHONE_LENGTH
        elif check_phone(self.piece['phone']) is None:
            self.base_ret['status'] = response_code.FORMAT_ERROR_PHONE
        same_phone = User.objects.filter(phone=self.piece['phone']).first()
        if same_phone is not None:
            self.base_ret['status'] = response_code.SAME_PHONE

        if self.base_ret['status'] != response_code.SUCCESS_STATUS:
            return False

    def name_filter(self):
        same_username = User.objects.filter(username=self.piece['username']).first()
        if len(self.piece['username']) > 15:
            self.base_ret['status'] = response_code.FORMAT_ERROR_USER_LENGTH
        elif self.piece['username'].strip() == '':
            self.base_ret['status'] = response_code.NULL_USERNAME
        elif same_username is not None:
            self.base_ret['status'] = response_code.SAME_USERNAME
        if self.base_ret['status'] != response_code.SUCCESS_STATUS:
            return False

    def avatar_filter(self):
        if self.piece['avatar'].strip() == '':
            self.base_ret['status'] = response_code.NULL_AVATAR
        if self.base_ret['status'] != response_code.SUCCESS_STATUS:
            return False

    def to_login(self):
        user = User.objects.filter(phone=self.piece['phone']).first()
        if user is not None:
            user = authenticate(phone=user.phone, password=self.piece['password'])
            if user is not None:
                auth.login(self.req, user)
                self.req.session['userId'] = str(user.uid)
                return user_card.account(user.uid)
            else:
                self.base_ret['status'] = response_code.ERROR_PASSWORD
                self.base_ret['result'] = copy.deepcopy(request_interface.account)
                return self.base_ret
        else:
            self.base_ret['status'] = response_code.NULL_USER
            self.base_ret['result'] = copy.deepcopy(request_interface.account)
            return self.base_ret

    def to_register(self):
        if self.password_filter() is not False and self.phone_filter() is not False:
            user = self.create_user()
            if user is not None:
                return self.to_login()
            else:
                raise Exception("Account.class：数据插入数据出异常")
        else:
            self.base_ret['result'] = copy.deepcopy(request_interface.account)
            return self.base_ret

    def create_user(self):
        # 为了保持self.piece 里面的值不变，做一个替换
        password = self.piece['password'].strip()
        self.piece['password'] = make_password(self.piece['password'].strip())
        user = User.objects.create(**self.piece)
        user.save()
        self.piece['password'] = password
        return user

    def complete_account(self):
        if self.piece['userId'] != '':
            user = User.objects.get(uid=self.piece['userId'])
            if user is not None:
                user.profile_id = Profile.objects.create(sex=self.piece['sex'], desc=self.piece['desc']).push_id
                user.avatar = self.piece['avatar']
                user.username = self.piece['username']
                user.save()
                return user_card.account(user.uid)
            else:
                self.base_ret['status'] = response_code.NULL_USER
                self.base_ret['result'] = copy.deepcopy(request_interface.account)
                return self.base_ret
        else:
            self.base_ret['status'] = response_code.NULL_USER
            self.base_ret['result'] = copy.deepcopy(request_interface.account)
            return self.base_ret
