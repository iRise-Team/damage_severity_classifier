import json
import numpy as np
import tensorflow as tf
from PIL import Image
from flask import Flask, request

app = Flask(__name__)

model = tf.keras.models.load_model("./dmgSev_vgg2.h5")
BATCH_SIZE = 80
IMG_SIZE = 300
category = ['little_or_none', 'mild', 'severe']

@app.route("/")
def hello():
    return "Hello, World!"

@app.route("/predict", methods=["POST"])
def predict():
    # get img data
   img = request.files['image']

   # resize then convert to arrray
   img = Image.open(img.stream)
   newsize = (IMG_SIZE, IMG_SIZE)
   img = img.resize(newsize)
   np_im = np.array(img)
   
   # normalization
   x=np.expand_dims(np_im, axis=0)
   images = np.vstack([x])/255 

   # predict image
   classes = model.predict(images, batch_size=BATCH_SIZE)
   label = np.argmax(classes)
   ctg = category[label]

   response_json = {
       "label":str(label)
   }
   print('category:', label)
   return json.dumps(response_json)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80)