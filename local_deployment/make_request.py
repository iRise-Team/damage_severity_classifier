import requests

url = 'http://192.168.1.80:5000/predict'

with open('./severe2.png', 'rb') as f:
    my_img = {'image': f}
    r = requests.post(url,files=my_img)
    print(r.json())