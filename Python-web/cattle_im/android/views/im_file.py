import base64
import json

from django.http import HttpResponse
from django.views.decorators.http import require_POST

from android.contract.request_interface import common


@require_POST
def save(request):
    global file_str
    info = eval(request.body)
    ret_info = common
    try:
        file_str = open('media/avatars/android/' + info['name'], 'wb')
        file_str.write(base64.b64decode(info['content']))
        info = {"path": 'media/avatars/android/' + info['name']}
    except Exception:
        info = {"path": ''}
        ret_info['status'] = 0
    finally:
        file_str.close()
        ret_info['result'] = info
        print(json.dumps(ret_info, ensure_ascii=False))
        return HttpResponse(json.dumps(ret_info, ensure_ascii=False))
