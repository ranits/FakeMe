from flask import Flask, request

app = Flask(__name__)


@app.route('/image', methods=['POST'])
def retrieve_image():
    if request.method == 'POST':
        age = request.json.get("age")
        gender = request.json.get("gender")
        race = request.json.get("race")
        return "https://drive.google.com/file/d/1ZYAiJjrO3pPPalkO8ZAlOJ56caewB5pD/view"
    else:
        raise Exception("Method type not allowed! use POST.")


if __name__ == '__main__':
    app.run(debug=True, port=5000)