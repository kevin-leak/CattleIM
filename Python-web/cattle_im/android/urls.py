from django.urls import path, include

from android.utils import excel_tools
from android.views import test, account, im_file
from android.views.push import wskt_server

urlpatterns = [
    path('post_test/', test.post_test),
    path('get_test/', test.get_test),
    path('login/', account.login),
    path('register/', account.register),
    path('out/', account.out),
    path('save_file/', im_file.save),
    path('xlrd/', excel_tools.parse_excel),
    path('web_view/', include("android.web_view_urls")),
    path('websocket/', wskt_server.wbskt),
    path('push_to_all/', wskt_server.push_to_all),
]
