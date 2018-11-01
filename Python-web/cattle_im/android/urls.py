from django.contrib import admin
from django.urls import path, include

from android.utils import excel_tools
from android.views import test, account, im_file

urlpatterns = [
    path('post_test/', test.post_test),
    path('get_test/', test.get_test),
    path('login/', account.login),
    path('register/', account.register),
    path('out/', test.out),
    path('save_file/', im_file.save),
    path('xlrd/', excel_tools.parse_excel),
    path('web_view/', include("android.web_view_urls"))
]
