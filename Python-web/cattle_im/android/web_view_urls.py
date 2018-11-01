from django.contrib import admin
from django.urls import path, include

from android.utils import excel_tools
from android.views import test, account, im_file, web_views

urlpatterns = [
    path('index.html', web_views.index),
]