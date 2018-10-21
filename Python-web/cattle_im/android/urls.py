from django.contrib import admin
from django.urls import path, include

from android.views import test, account

urlpatterns = [
    path('post_test/', test.post_test),
    path('get_test/', test.get_test),
    path('login/', account.login),
    path('register/', account.register),
]
