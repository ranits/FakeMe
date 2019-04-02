from flask import Flask, request
import json
import os
from PictureGenerator.generator import get_info_from_pic, generate_picture

app = Flask(__name__)


@app.route('/image_info', methods=['POST'])
def retrieve_image_info():
    if request.method == 'POST':
        data = {}
        for id in request.json:
            data[id] = {}
            path = request.json.get(id)
            pictures_list = generate_picture(path)
            for pic_url, con in pictures_list.items():
                pic_info = get_info_from_pic(pic_url)
                pic_info["confidence"] = round(con, 4)
                data[id][os.path.basename(pic_url)] = pic_info
        return json.dumps(data)
    else:
        raise Exception("Method type not allowed! use POST.")


if __name__ == '__main__':
    app.run(debug=True, port=5000)
