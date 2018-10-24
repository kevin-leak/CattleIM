import json

from django.http import HttpResponse


# def dict_switch(card=None, *args, **kwargs):
#     """用来抽象请求中抽象的方法"""
#     def wrap(func):
#         def inner(request):
#             info = request.body
#             ret = func(info)
#             return HttpResponse(json.dumps(ret))
#         return inner
#     return wrap

