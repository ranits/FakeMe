import re
from pybetaface.api import BetaFaceAPI

default_name = "fakemeuser"
namespace = "fakeme"
client = BetaFaceAPI()


def get_info_from_pic(path):
    info = client.upload_face(path, default_name + '@' + namespace)
    img_uid = info['img_uid']
    person_info = client.get_image_info(img_uid)
    raw = person_info['raw_content']
    m = re.search(
        ".*<name>age</name><value>([0-9]+)</value>.*<name>gender</name><value>(female|male)</value>.*<name>race</name><value>(.*)(</value>)",
        raw)
    age = m.group(1)
    gender = m.group(2)
    race = m.group(3).split('<')[0]
    return {
            'url': path,
            'age': age,
            'gender': gender,
            'race': race
        }


def generate_picture(path):
    matches = client.recognize_faces(path, namespace)
    similar_pictures = {}
    for i, (key, value) in enumerate(matches.items()):
        if i < 6 and (not key.__contains__(default_name)):
            url = get_url(key)
            similar_pictures[url] = value
        else:
            if similar_pictures.__len__() >= 5:
                return similar_pictures
    return similar_pictures


def get_url(pic_name):
    name = pic_name.split('@')[0]
    return "C:\\Users\\IT\\Downloads\\results\\" + name + ".jpg"

