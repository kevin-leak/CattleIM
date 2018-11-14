import json

from django.http import HttpResponse

from android.contract import response_code
from android.contract.request_interface import common


def session_error(ret=common):
    """
    使用的比较频繁单独拿出来，session的错误
    """
    ret["status"] = response_code.ERROR_SESSION
    return HttpResponse(json.dumps(ret, ensure_ascii=False))


def base_error(error_type, ret=common):
    ret["status"] = error_type
    ret["message"] = "failure"
    return ret
