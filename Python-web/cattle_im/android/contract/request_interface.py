import time
from datetime import date

common = {
    "status": 0,
    "result": [],
    "message": "",
    "date": time.strftime("%Y-%m-%dT%H:%M:%S", time.localtime())
}

account = {
    "user": {
        "id": "",
        "username": "name",
        "phone": "188707421",
        "avatar": "media/avatars/android/xx.jpg",
        "desc": "",
        "sex": 0,
        "alias": "备注",
        "friends": 0,
        "isFriend": True,
        "modifyAt": "2018/12/15"
    },
    "account": "",
    "isBind": True
}
