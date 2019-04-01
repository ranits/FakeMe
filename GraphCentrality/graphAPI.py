from flask import Flask, request

app = Flask(__name__)


@app.route('/example')
def example():
    return "example 1"


if __name__ == '__main__':
    app.run(debug=True, port=5000)
