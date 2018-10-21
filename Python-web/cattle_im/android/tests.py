from django.test import TestCase
from bs4 import BeautifulSoup
# Create your tests here.


soup = BeautifulSoup('<b class="boldest">Extremely bold</b>', "html.parser")
tag = soup.b
print(tag)