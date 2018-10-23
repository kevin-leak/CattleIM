from android.api.factory import user_card


def login_wrap(func):
    def inner(request):
        # 处理数据错误的逻辑
        # 进行数据库的缓存
        # 对返回数据的取出
        ret = func(request)  # 并且字符串转为json, 以及其他一些文件的处理
        # 对数据进行加密，封装头信息
        return ret

    return inner


def register_wrap(func):
    def inner(request):
        # 处理数据错误的逻辑
        # 进行数据库的缓存
        # 对返回数据的取出
        user_card.Card(eval(request.body))
        ret = func(request)  # 并且字符串转为json, 以及其他一些文件的处理
        # 对数据进行加密，封装头信息
        return ret

    return inner



