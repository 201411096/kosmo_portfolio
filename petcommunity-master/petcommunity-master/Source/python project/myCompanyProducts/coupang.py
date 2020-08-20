import csv
from selenium import webdriver
import time
from bs4 import BeautifulSoup
from urllib.parse import urljoin
from urllib.request import urlretrieve
import insertDB



# 웹드라이버 가져오기
driver = webdriver.Chrome('./webdriver/chromedriver')
driver.implicitly_wait(3)
origin_url = 'http://www.dogskingdom.co.kr/shop/goods/goods_list.php?&category=002'

# div.list_subcate_wrap > ul > li:nth-child(1)
# 웹페이지 소스 파싱
driver.get(origin_url)
time.sleep(2)
html = driver.page_source
soup = BeautifulSoup(html, 'html.parser')

# 카테고리 url 및 명칭 가져오기
category_list = []
category_name_list = []
for i in range(1,20):
    temp1 = 'div.list_subcate_wrap > ul.list_subcate > li:nth-child('+str(i)+') > a'                                                                     ''
    print(temp1)
    category_list += [soup.select(temp1)[0].attrs['href']]
    category_name_list += [soup.select(temp1)[0].text]
print(category_list)
print(category_name_list)
final_list=[]
crawling_baseurl = 'http://www.dogskingdom.co.kr/shop/goods/goods_list.php{category}'
for index, category in enumerate(category_list):
    url = crawling_baseurl.format(category=category)
    # 카테고리 이름 저장
    product_feature = category_name_list[index].replace(",", "")
    print(url)
    print(product_feature)
    driver.get(url)
    time.sleep(2)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    # 카테고리별 페이지 개수
    pages = soup.select('div.pagediv > a.navi')
    # print(len(pages)+1)
    for page in range(1,len(pages)+2):
        print("page", page)
        page_url='&page='+str(page)
        final_url = url+page_url
    #     final_url=urljoin(url, page_url)
    #     print(final_url)
        driver.get(final_url)
        time.sleep(2)
        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')

        # 이미지, 제품명, 가격, 컨텐츠 저장
        img = soup.select('div > div.thumbnail > a > img')
        name = soup.select('div > div.txt > a > strong')
        price = soup.select('div.price > a > span > strong')
        content = soup.select('div.txt > a > em')
        # print(content.text())
        # if content.isnull():
        #     content = "kkkkk"

        # 파싱한 각 항목별 value 추출 후 product_list에 추가
        for i, n, p, c in zip(img, name, price, content):
            product_img = i.attrs['src'].replace("..", "http://www.dogskingdom.co.kr/shop")
            product_name = n.text.replace(",", "").replace("/", " ").replace("[", "(").replace("]", ")").replace("%","퍼센트")+'-'+str(len(final_list)+1)+'-'
            product_price = p.text.replace(",","")
            product_content = c.text.replace(",","+").replace(".","+").replace("/", "+")
            product_list = product_img, product_name, product_price, 100, product_feature, product_content
            final_list+=[product_list]
            # print(len(img))
            print(product_list)
            print(len(final_list))

            # 이미지로 저장
            downPath = 'product_image/'
            urlretrieve(product_img, downPath + product_list[1] + '.jpg')

# 파일로 저장
f = open('data/product_list.csv', 'w', encoding='utf-8-sig', newline='')
csvWriter = csv.writer(f)
for i in final_list:
    csvWriter.writerow(i)
f.close()

# DB에 저장
insertDB.insertData()
