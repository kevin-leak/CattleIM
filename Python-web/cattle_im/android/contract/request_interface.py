import json
import time
from datetime import date
from functools import wraps

common = {
    "status": 0,
    "result": [],
    "message": "ok",
    "date": time.strftime("%Y-%m-%dT%H:%M:%S", time.localtime())
}

account = {
    "user": {
        "id": "",
        "username": "",
        "phone": "",
        "avatar": "",
        "desc": "",
        "sex": 0,
        "alias": "",
        "friends": 0,
        "isFriend": True,
        "modifyAt": ""
    },
    "account": "",
    "isBind": False
}



