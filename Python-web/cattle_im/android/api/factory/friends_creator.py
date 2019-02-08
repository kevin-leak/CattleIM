
from android.api.factory import error_response
from android.api.wrapper.base import base_contract, user_creator
from android.contract import response_code
from db.models import Friends, User


@base_contract()
def find_new_info(user_set, holder_id):
    """
    用来获取约定中的好友信息, 这里指的是查询加好友的情况
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
def get_friend_contact(relation_set, holder_id):
    """
    获取联系人列表信息
    :param holder_id: 所查询到的联系人列表集合
    :param relation_set:
    :return: 一个约定的字典
    """
    friends = []
    for relate in relation_set:
        user = User.objects.filter(phone=relate.target).first()
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
