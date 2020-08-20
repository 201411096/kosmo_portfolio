import glob
import socket
import time
import tensorflow as tf
from PIL import Image
import os, glob, numpy as np
from PIL import ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True

from PIL import Image
from io import BytesIO


# 서버 소켓 오픈
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(("", 8001))
server_socket.listen(5)

print("TCPServer Waiting for client on port 8001")

while True:

    # 클라이언트 요청 대기중 .
    client_socket, address = server_socket.accept()
    # 연결 요청 성공
    print("I got a connection from ", address)

    data = None

    #data 변수에 byte형의 무언가를 담음
    data = b''

    while 1:
        #소켓을 통해 10240byte만큼 받음
        img_bytes = client_socket.recv(10240)
        print('img_byte', img_bytes)
        data+=img_bytes
        #더이상 받을게 없으면 while문 나가기
        if not img_bytes:
            break

    img_test = Image.open(BytesIO(data))


    ################### 받은 파일 분석하기 ############################
    time.sleep(2)#이미지가 완전히 넘어올때까지 기다리기
    #caltech_dir = "./testImg"
    image_w = 32
    image_h = 32

    pixels = image_h * image_w * 3

    X = []
    #files = glob.glob(caltech_dir+"/2020_8_9_18_4_29.jpg")

    #테스트할 이미지
    test_img = img_test
    test_img = test_img.convert("RGB")
    test_img = test_img.resize((image_w, image_h))
    data = np.asarray(test_img) / 255



    X.append(data)
    X = np.array(X)

    #학습해놓은 모델 불러와서 예측하기
    model = tf.keras.models.load_model('./model/dogBreedModel4.hdf5')
    prediction = model.predict(X)
    np.set_printoptions(formatter={'float': lambda x: "{0:0.3f}".format(x)})
    cnt = 0
    # ['말티즈', '푸들', '불독', '시베리안허스키', '시츄']

    for i in prediction:

        pre_ans = i.argmax()  # 예측 레이블
        # print(i)
        print()
        pre_ans_str = ''
        if pre_ans == 0:
            pre_ans_str = "말티즈"
        elif pre_ans == 1:
            pre_ans_str = "푸들"
        elif pre_ans == 2:
            pre_ans_str = "시베리안허스키"
        elif pre_ans == 3:
            pre_ans_str = "시츄"
        else:
            pre_ans_str = "모름"
        result = max([i[0],  i[1],  i[2], i[3]])

        cnt += 1
    print('해당 사진의 견종은', round(result*100), '%의 확률로', pre_ans_str, '입니다' )
    resultSentence = '해당 사진의 견종은', result, '의 확률로', pre_ans_str, '입니다'

client_socket.close()
print("SOCKET closed... END")






