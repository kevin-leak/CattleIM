

def dict_switch(card=None, *args, **kwargs):
    """用来抽象请求中抽象的方法"""
    def wrap(func):
        def inner(request):
            ret = func(request)
            return ret
        return inner
    return wrap

