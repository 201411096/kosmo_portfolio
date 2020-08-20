import requests
import xmltodict
import sys
import time

now = time.localtime()

def getPublicData(beginDate=str(now.tm_year)+'0101', endDate=str(now.tm_year)+'1231', numOfRows='100000'):
    pageNo = '1'
    requestUrl = 'http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=' + beginDate + '&endde=' + endDate + '&pageNo=' + pageNo + '&numOfRows=' + numOfRows + '&ServiceKey='
    serviceKey = 'Cs8el%2FuhtlYCY%2BHBBp9jCapmuo%2FmEjVkn0P%2BU6BY78tnS%2BTrPlz7BUEk%2BDfKOvvioI9hcaSuAJT%2FpgGsqAQG9A%3D%3D'
    result = requests.get(requestUrl+serviceKey)
    dataFromXml = xmltodict.parse(result.content)
    itemList = dataFromXml['response']['body']['items']
    print(itemList)
    # for item in itemList['item']:
    #     print(item['happenDt'])
    #     print(item['happenPlace'])
    # print('데이터 길이', len(itemList['item']))
    return itemList['item']
if(sys.argv[1]=='1'):
    getPublicData()
else:
    getPublicData(sys.argv[2], sys.argv[3], sys.argv[4])

