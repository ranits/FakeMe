from flask import Flask, request

app = Flask(__name__)


@app.route('/graph-location/<file_location>', methods=['POST'])
def example(file_location):
    # call to graph loader (location)
    # call to centrality calculator
    return "Start create new graph......."


if __name__ == '__main__':
    app.run(debug=True, port=5000)
