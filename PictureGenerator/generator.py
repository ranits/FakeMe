import re
from pybetaface.api import BetaFaceAPI

default_name = "fakemeuser"
namespace = "fakeme"
client = BetaFaceAPI()


def get_info_from_pic(path, name=None):
    if name is None:
        name = default_name
    info = client.upload_face(path, name + '@' + namespace)
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
    max_pic = 6
    matches = client.recognize_faces(path, namespace)
    similar_pictures = {}
    for i, (same_pic_name, confidence) in enumerate(matches.items()):
        if similar_pictures.__len__() < max_pic:
            if not same_pic_name.__contains__(default_name):
                same_pic_url = get_url(same_pic_name)
                if params_identical(path, same_pic_url):
                    similar_pictures[same_pic_url] = confidence
        else:
            return similar_pictures
    return similar_pictures


def get_url(pic_name):
    name = pic_name.split('@')[0]
    return "C:\\Users\\IT\\Downloads\\results\\" + name + ".jpg"


def params_identical(actual_path, new_path):
    expected_info = get_info_from_pic(new_path)
    actual_info = get_info_from_pic(actual_path)
    if expected_info['gender'] == actual_info['gender']:
        if abs(int(expected_info['age']) - int(actual_info['age'])):
            if expected_info['race'] == actual_info['race']:
                return True
    return False
