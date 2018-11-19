"""
用来对好友操作模块
"""
import json

from django.contrib.auth.decorators import login_required
from django.http import HttpResponse
from django.views.decorators.http import require_POST

from android.api.factory import error_response, friends_creator
from android.contract import response_code
from db.models import Friends


@login_required
def create(request, uid):
    """
    建立好友关系
    :param uid: 好友的id
    :return:
    """
    if request.session.get("userId", "") == "":
        return error_response.session_error()
    holder_id = request.session["userId"]
    print(holder_id)
    is_exists = Friends.objects.filter(origin_id=holder_id, target_id=uid).exists()
    if is_exists:
        ret = error_response.base_error(response_code.EXIST_FRIENDS)
    else:
        ret = friends_creator.relation(holder_id, uid)
    print(json.dumps(ret))
    return HttpResponse(json.dumps(ret))


@login_required
def delete(request, uid):
    pass


@login_required
def change_info(request, uid):
    pass


def send_message():
    pass
