import base64
import json

from django.http import HttpResponse

from android.contract.request_interface import common


def save(request):
    print(request.POST)
    info = eval(request.body)
    print(json.dumps(info))
    ret_info = common
    file_str = open('media/avatars/android/' + info['name'], 'wb')
    file_str.write(base64.b64decode(info['content']))
    file_str.close()
    info = {"code": 800, "path": 'media/avatars/android/' + info['name']}
    ret_info['result'] = info
    ret_info['status'] = 1
    return HttpResponse(json.dumps(ret_info))
