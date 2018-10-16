import json

from django.shortcuts import render, HttpResponse
from dwebsocket.decorators import accept_websocket,require_websocket


# Create your views here.
def test(request):
    resp = {'errorcode': 100, 'detail': 'Get success'}
    return HttpResponse(json.dumps(resp), content_type="application/json")
