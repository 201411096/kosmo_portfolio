# 리턴값을 확인함 -> ignore문도 같이 나오는 문제가 있음 ...
# pip install tensorflow==2.0.0
import glob
import socket
import time
import tensorflow as tf
from PIL import Image
import os, glob, numpy as np
from PIL import ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True
import sys
from PIL import Image
from io import BytesIO
import base64
import json
# import warnings
# warnings.filterwarnings('ignore')

# 리턴값 확인하는 부분
def classifyingImage(data):
    result = {
        "a":"aa",
        "b":"bb",
        "c":"cc"
    }
    result2 = 'aaabbbcccs'
    print(result)
    print(result2)
    # print(result)
    # return json.dumps(result)
    return result2

# def classifyingImage(data):
#     # img = Image.open(path)
#     # buf = BytesIO()
#     #
#     # # Save the image as jpeg to the buffer
#     # img.save(buf, 'jpeg')
#     # buf.seek(0)
#     # img_test = Image.open(BytesIO(buf.read()))
#
#     # base64buf = base64.b64encode(bytes(data, 'utf-8'))
#     # img = Image.open(BytesIO(base64.b64decode(base64buf)))
#     # buf = BytesIO()
#     # img.save(buf, 'jpeg')
#     # buf.seek(0)
#     # img_test = Image.open(BytesIO(buf.read()))
#     return '123'
#     img = Image.open(BytesIO(bytes(data)))
#
#     ################### 받은 파일 분석하기 ############################
#     time.sleep(2)#이미지가 완전히 넘어올때까지 기다리기
#     #caltech_dir = "./testImg"
#     image_w = 32
#     image_h = 32
#
#     pixels = image_h * image_w * 3
#
#     X = []
#     #files = glob.glob(caltech_dir+"/2020_8_9_18_4_29.jpg")
#
#     #테스트할 이미지
#     test_img = img_test
#     test_img = test_img.convert("RGB")
#     test_img = test_img.resize((image_w, image_h))
#     data = np.asarray(test_img) / 255
#
#
#
#     X.append(data)
#     X = np.array(X)
#
#     #학습해놓은 모델 불러와서 예측하기
#     model = tf.keras.models.load_model('./model/dogBreedModel4.hdf5')
#     prediction = model.predict(X)
#     np.set_printoptions(formatter={'float': lambda x: "{0:0.3f}".format(x)})
#     cnt = 0
#     # ['말티즈', '푸들', '불독', '시베리안허스키', '시츄']
#
#     for i in prediction:
#
#         pre_ans = i.argmax()  # 예측 레이블
#         # print(i)
#         print()
#         pre_ans_str = ''
#         if pre_ans == 0:
#             pre_ans_str = "말티즈"
#         elif pre_ans == 1:
#             pre_ans_str = "푸들"
#         elif pre_ans == 2:
#             pre_ans_str = "시베리안허스키"
#         elif pre_ans == 3:
#             pre_ans_str = "시츄"
#         else:
#             pre_ans_str = "모름"
#         result = max([i[0],  i[1],  i[2], i[3]])
#
#         cnt += 1
#     print('해당 사진의 견종은', round(result*100), '%의 확률로', pre_ans_str, '입니다' )
#     resultSentence = '해당 사진의 견종은', result, '의 확률로', pre_ans_str, '입니다'
#     return resultSentence
#
# path = 'data1.jpg'
# classifyingImage(path)
if(sys.argv[1]=='1'):
    classifyingImage('data1.jpg')
else:
    classifyingImage(sys.argv[2])