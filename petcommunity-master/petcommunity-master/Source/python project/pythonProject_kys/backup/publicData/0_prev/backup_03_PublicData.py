# 0808 백업
# json 파일 형식으로 저장
import requests
import xmltodict
import json
import time
import sys

now = time.localtime()

def getPublicData(beginDate=str(now.tm_year)+'0801', endDate=str(now.tm_year)+'1231', numOfRows='10'):
    pageNo = '1'
    requestUrl = 'http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=' + beginDate + '&endde=' + endDate + '&pageNo=' + pageNo + '&numOfRows=' + numOfRows + '&ServiceKey='
    serviceKey = 'Cs8el%2FuhtlYCY%2BHBBp9jCapmuo%2FmEjVkn0P%2BU6BY78tnS%2BTrPlz7BUEk%2BDfKOvvioI9hcaSuAJT%2FpgGsqAQG9A%3D%3D'
    result = requests.get(requestUrl+serviceKey)
    dataFromXml = xmltodict.parse(result.content)
    # itemList = list(dataFromXml['response']['body']['items']['item'])
    # return itemList
    # itemList = list(dataFromXml['response']['body']['items']['item'])
    # jsonObject = json.dumps(itemList, ensure_ascii=False)
    # print(jsonObject)
    # jsonObject = jsonObject.encode('utf-8')
    # print(jsonObject)
    # return jsonObject
    with open('../../../publicData.json', 'w', encoding="utf-8") as make_file:
        json.dump(dataFromXml, make_file, ensure_ascii=False, indent=4)
    return

# getPublicData()
if(sys.argv[1]=='1'):
    getPublicData()
else:
    getPublicData(sys.argv[2], sys.argv[3], sys.argv[4])

