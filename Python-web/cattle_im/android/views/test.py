import json
import random
import sys
import uuid
from time import sleep

from bs4 import BeautifulSoup
from cryptography.hazmat.primitives.ciphers.algorithms import AES
from django import template
from django.contrib.auth import logout
from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import render
from django.template import RequestContext
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_POST, require_GET
from dwebsocket import require_websocket, accept_websocket
from dwebsocket.websocket import WebSocket
from pyDes import des
from requests import auth

from db.models import User


@require_POST
def post_test(request):
    print(request.POST)
    logout(request)
    return HttpResponse("ok")


@require_GET
def get_test(request):
    html = "<html><body>{% csrf_token %}</body></html>"
    # # 对其进行渲染
    template.Template(html).render(RequestContext(request))
    # render(request, 'index.html')
    return HttpResponse("ok")




