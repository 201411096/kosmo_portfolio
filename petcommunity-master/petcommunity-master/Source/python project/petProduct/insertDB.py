import cx_Oracle as oci

def insertData():
    conn = oci.connect("teamproject/1234@192.168.0.18:1521/orcl")
    cursor = conn.cursor()

    f = open('data/pawInHand_list.csv', 'r', encoding='utf-8-sig', newline='')
    list = f.readlines()
    print(list)
    for line in list:
        web_name=line.split(',')[0]
        category=line.split(',')[1]
        product_name=line.split(',')[2]
        product_price=line.split(',')[3]
        product_img=line.split(',')[4]
        product_link=line.split(',')[5]

        sql = '''
                INSERT INTO SHOP
                (SHOP_ID, SHOP_NAME, SHOP_CATEGORY, SHOP_PRODUCTNAME, SHOP_PRODUCTPRICE, SHOP_IMG, SHOP_LINK) 
                VALUES(SHOP_ID_SEQ.nextval, :web_name, :category, :product_name, :product_price, :product_img, :product_link)
        '''
        cursor.execute(sql, (web_name, category, product_name, product_price, product_img, product_link))
    conn.commit()
    cursor.close()
    conn.close()

    # print("웹사이트: ",web_name)
    # print("카테고리: ",category)
    # print("상품명: ",product_name)
    # print("가격: ",product_price)
    # print("이미지: ",product_img)
    # print("링크: ",product_link)