import xlrd
from django.shortcuts import render


def parse_excel(request):
    print()
    data = xlrd.open_workbook('media/excel/lkk.xlsx')  # 打开xls文件
    print(type(data))
    table = data.sheets()[0]  # 打开第一张表
    nrows = table.nrows  # 获取表的行数
    print()
    data = []
    for i in range(nrows):  # 循环逐行打印
        if i == 0:  # 跳过第一行
            continue
        data.append(table.row_values(i)[:13])
        print()  # 取前十三列
    return render(request, 'tables.html', {'data': data})
# parse_excel()