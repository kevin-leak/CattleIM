from android.api.factory import error_response
from android.api.wraper.base import base_contract, user_creator
from android.contract import response_code
from db.models import Friends


@base_contract()
def info(user_set, holder_id):
    """
    用来获取约定中的好友信息
    :param user_set: 数据库中查询到的用户集合
    :param holder_id: 当前用户的id
    :return: 返回约定中的信息
    """
    friends = []
    for user in user_set:
        info_set = user_creator(holder_id, user.uid)
        friends.append(info_set)
    return friends


@base_contract()
def relation(holder_id, current_id):
    """
    用来打包发送好友请求的信息
    :param holder_id:
    :param current_id:
    :return:
    """
    Friends.objects.create(origin_id=holder_id, target_id=current_id)
    return user_creator(holder_id, current_id)
