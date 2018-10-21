from bs4 import BeautifulSoup
from django import template
from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import render
from django.views.decorators.http import require_POST, require_GET
from pyDes import des


@require_POST
def post_test(request):
    print(request.POST)
    return HttpResponse("ok")


def get_test(request):
    html = "<html><body>{% csrf_token %}</body></html>"
    # # 对其进行渲染
    template.Template(html).render(template.Context())
    # render(request, 'index.html')
    return HttpResponse("ok")