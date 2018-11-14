"""
用来处理客户端的搜索
"""
import json

from django.contrib.auth.decorators import login_required
from django.http import HttpResponse

from android.api.factory import error_response, friends_creator, groups_creator
from android.contract import response_code
from android.utils import phone_tools
from db.models import User, Group


@login_required
def friends(request, field, page):
    """
    搜索好友
    :param field: 肯能是username，也肯是uid
    :param page: 当前页
    :return:
    """

    # todo 服务器必须筛选，当前用户已经通过了对方验证的用户
    page = int(page)
    print(field, page)
    if request.session.get("userId", "") == "":
        return error_response.session_error()
    holder_id = request.session["userId"]
    if phone_tools.check_phone(field):
        all_user = User.objects.filter(phone=field)[page*10:10*(page+1)]
        friends_ret = friends_creator.info(all_user, holder_id)
    else:
        search = field
        all_user = User.objects.filter(username__contains=search)[page*10:10*(page+1)]
        friends_ret = friends_creator.info(all_user, holder_id)
    if not friends_ret['result']:
        friends_ret['status'] = response_code.NULL_DATA
    print(json.dumps(friends_ret, ensure_ascii=False))
    return HttpResponse(json.dumps(friends_ret, ensure_ascii=False))


@login_required
def group(request, field, page):
    """
    群端搜索
    """
    page = int(page)
    if request.session.get("userId", "") == "":
        return error_response.session_error()
    holder_id = request.session["userId"]
    all_gopup = Group.objects.filter(name__contains=field)[(page-1)*10:10*(page-1)+10]
    goups_ret = groups_creator.info(all_gopup, holder_id)
    return HttpResponse(json.dumps(goups_ret, ensure_ascii=False))


@login_required
def time(request):
    """
    时间点事件搜索
    """
    return None


@login_required
def task(request):
    """
    task line 任务链的搜索
    """
    return None


@login_required
def topic(request):
    """
    话题，主题的搜索
    """
    return None


@login_required
def notice(request):
    """
    公告，提醒的搜索端搜索
    """
    return None
