import time

from django.http import HttpResponse

from android.api.wraper.base import base_contract, user_creator, account_creator
from android.contract.request_interface import account
from db import models

from db.models import User, Friends


@base_contract()
def account(holder_id):
    """
    用来构造用户注册登入的用户信息
    """
    account_creator(holder_id, holder_id)

#  todo 建造一个朋友类
