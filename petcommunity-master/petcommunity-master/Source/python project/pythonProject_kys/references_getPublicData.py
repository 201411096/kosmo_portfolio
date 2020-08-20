import requests
import xmltodict

beginDate = '20200101'
endDate = '20201231'
pageNo = '1'
numOfRows = '1000000'

requestUrl = 'http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=' + beginDate + '&endde=' + endDate + '&pageNo=' + pageNo + '&numOfRows=' + numOfRows + '&ServiceKey='
serviceKey = 'Cs8el%2FuhtlYCY%2BHBBp9jCapmuo%2FmEjVkn0P%2BU6BY78tnS%2BTrPlz7BUEk%2BDfKOvvioI9hcaSuAJT%2FpgGsqAQG9A%3D%3D'

result = requests.get(requestUrl+serviceKey)
dataFromXml = xmltodict.parse(result.content)


itemList = dataFromXml['response']['body']['items']
for item in itemList['item']:
    print(item['happenDt'])
    print(item['happenPlace'])

print('데이터 길이', len(itemList['item']))
