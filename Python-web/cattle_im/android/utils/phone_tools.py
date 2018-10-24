import re


def check_phone(phe):
    phone_pat = re.compile("^[1][3578][0-9]{9}$")
    res = phone_pat.search(phe)
    return res
