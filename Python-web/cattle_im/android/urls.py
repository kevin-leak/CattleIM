from django.conf.urls import url
from django.urls import path, include

from android.utils import excel_tools
from android.views import test, account, im_file, search, friend
from android.views.push import wskt_server

urlpatterns = [
    path('login/', account.login),
    path('register/', account.register),
    path('out/', account.out),

    path('save_file/', im_file.save),

    path('web_view/', include("android.web_view_urls")),
    path('websocket/', wskt_server.wbskt),
    path('push_to_all/', wskt_server.push_to_all),

    url(r'^user_search/(?P<field>\w+)/(?P<page>\d+)/$', search.friends),
    url(r'^group_search/(?P<field>\w+)/(?P<page>\d+)/$', search.group),
    path('time_search/', search.time),
    path('task_search/', search.task),
    path('topic_search/', search.topic),
    path('notice_search/', search.notice),

    url(r"^add_friend/(?P<uid>[a-zA-Z0-9-]*)/$", friend.create),

    path("account_test/", test.test_account),
    path('xlrd/', excel_tools.parse_excel),
    path('post_test/', test.post_test),
    path('get_test/', test.get_test),
]
