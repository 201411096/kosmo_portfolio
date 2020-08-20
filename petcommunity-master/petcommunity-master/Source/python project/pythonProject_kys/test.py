import base64

with open("a.jpg", "rb") as imageFile:
    imagestr = base64.b64encode(imageFile.read())
    print(imagestr)

with open("b.jpg", "wb") as imgFile:
    imgFile.write(base64.b64decode(imagestr))