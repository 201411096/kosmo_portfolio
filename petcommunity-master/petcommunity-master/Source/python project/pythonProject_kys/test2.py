from datetime import datetime
import time
print(str(datetime.today().year)+str(datetime.today().month)+str(datetime.today().day))
now = time.localtime()
today = str(now.tm_year)+str(now.tm_mon)+str(now.tm_mday) # 사용안함
print(today)