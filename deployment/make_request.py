import requests

# url = 'http://35.232.0.63:80/predict'
url = 'http://[vm_ip]/predict'

with open('./severe2.png', 'rb') as f:
    my_img = {'image': f}
    r = requests.post(url,files=my_img)
    print(r.json())