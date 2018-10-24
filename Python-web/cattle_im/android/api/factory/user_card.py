from db import models


#
# def get_name(user_id):
#     return models.User.objects.get(id=user_id)
#
#
# def get_avatar():
#     return models.User.objects.get("avatar")
#
#
# def get_phone():
#     return models.User.objects.get("phone")
#
#
# def get_email():
#     return models.User.objects.get("email")


# def card():
#     json_dict = {
#         "status": 0,
#         "name": get_name(),
#         "avatar": get_avatar(),
#         "phone": get_phone(),
#         "email": get_email(),
#     }
#     return json_dict


class Card(object):
    def __init__(self):
        """"创建数据库对象"""

        # self.user = models.User.objects.create(**json_dic)

    def verify_login(self, phone, password):
        pass

    def verify_register(self, json_dic):
        pass

    @staticmethod
    def get_user_info():
        json_info = ''
        return json_info


if __name__ == '__main__':

    bokeyuan = {"b": 1,
                "o": 2,
                "k": 3,
                "e": 4,
                "y": 5,
                "u": 6,
                "a": 7,
                "n": 8,
                }

    Card.__dict__.update(bokeyuan)
    print(Card().__dict__.items())
