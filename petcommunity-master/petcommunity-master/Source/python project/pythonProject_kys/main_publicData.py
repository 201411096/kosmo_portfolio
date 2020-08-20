import requests
import xmltodict
import json
import time
import sys

now = time.localtime()
today = str(now.tm_year)+str(now.tm_mon)+str(now.tm_mday) # 사용안함

def getPublicData(beginDate=str(now.tm_year-1)+'0101', endDate=str(now.tm_year)+'1231', numOfRows='10000'):
    pageNo = '1'
    requestUrl = 'http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=' + beginDate + '&endde=' + endDate + '&pageNo=' + pageNo + '&numOfRows=' + numOfRows + '&ServiceKey='
    serviceKey = 'Cs8el%2FuhtlYCY%2BHBBp9jCapmuo%2FmEjVkn0P%2BU6BY78tnS%2BTrPlz7BUEk%2BDfKOvvioI9hcaSuAJT%2FpgGsqAQG9A%3D%3D'
    result = requests.get(requestUrl+serviceKey)
    dataFromXml = xmltodict.parse(result.content)
    itemList = list(dataFromXml['response']['body']['items']['item'])
    resultList = []
    for item in itemList:
        print(type(item))
    with open('main_publicData_' + today + '.json', 'w', encoding="utf-8") as make_file:
        json.dump(itemList, make_file, ensure_ascii=False, indent=4)

# print(sys.argv[1])
# print(sys.argv[2])
# print(sys.argv[3])
# print(sys.argv[4])
getPublicData()
print('aa')
# if(sys.argv[1]=='1'):
#     getPublicData()
# else:
#     getPublicData(sys.argv[2], sys.argv[3], sys.argv[4])



