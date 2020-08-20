import csv
import time
from bs4 import BeautifulSoup
from selenium import webdriver
from urllib.request import urlretrieve
from urllib.parse import urljoin
import insertDB

driver = webdriver.Chrome('./webdriver/chromedriver')
driver.implicitly_wait(3)

# 사이트 주소
origin_url = 'https://smartstore.naver.com/'
# 사이트 카테고리별 정렬
category_list =['fa3d28f25e674590a77bc99248b25519', '555dbf4b619a4d6c900856531969dfea', 'e871cb17ee8b4f3f8b19f49afc168890',
                'd37d51c009034e188622620b2c90efea', 'd5f76d2db14f4becb6df6f02365e21a2', '9e3b5915a6454e0abfb98bea9e9e7ea9']

# 크롤링한 data 담을 리스트
shop_list=[]
for category in category_list:
    # 웹페이지 소스 파싱
    url_base = origin_url+ 'pawinhand/category/{category}?cp=1'
    url = url_base.format(category=category)
    driver.get(url)
    time.sleep(2)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    # 제품 카테고리, 이름, 가격, 이미지, 링크 크롤링
    category = soup.select('div.header_sub > h3.title_sub')
    name = soup.select('strong.title')
    price = soup.select('div.area_price > strong:nth-child(1) > span.number')
    # img = soup.select('div.thumbnail > img.image')
    img = soup.select('a.N\=a\:lst\.product.area_overview > div.thumbnail > img')
    link = soup.select('a.N\=a\:lst\.product.area_overview')
    product_category = category[0].attrs['title']


    # 가져온 data 리스트로 가공
    site_name='pawInHand'
    product_list = []
    downPath = 'product_image/'+ site_name + '/'
    for n, p, i, l in zip(name, price, img, link):
        product_name = n.attrs['title'].replace(",", " ").replace("[", "(").replace("]", ")")
        product_price = p.text.replace(",", "")
        temp = i.attrs['src']
        if temp[0:5] == 'https':
            product_img = temp

            product_link = urljoin(origin_url, l.attrs['href'])
            product_list = site_name, product_category, product_name, product_price, product_img, product_link
            shop_list.append(product_list)

        # 이미지로 저장
            urlretrieve(product_img, downPath + product_name + '.jpg')

        # print(product_img)
            print(shop_list)
            print(len(shop_list))

# 파일로 저장
f = open('data/pawInHand_list.csv', 'w', encoding='utf-8-sig', newline='')
csvWriter = csv.writer(f)
for i in shop_list:
    csvWriter.writerow(i)
f.close()

# DB에 저장
insertDB.insertData()
