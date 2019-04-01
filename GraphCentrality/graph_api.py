from flask import Flask, request
from graph_utils import create_graph, calculate_graph_centrality

app = Flask(__name__)


@app.route('/graph-location', methods=['POST'])
def graph_location():
    if request.method == 'POST':
        nodes_path = request.json.get("nodes_path")
        edges_path = request.json.get("edges_path")
        G = create_graph(nodes_file=nodes_path, edges_file=edges_path)
        top_n = calculate_graph_centrality(G)
        return top_n
    else:
        raise Exception("Method type not allowed! use POST.")


if __name__ == '__main__':
    app.run(debug=True, port=5000)

