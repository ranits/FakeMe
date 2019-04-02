from flask import Flask, request, jsonify
from datetime import datetime
from GraphCentrality.graph_utils import create_graph, get_graph_top_centers, sample_graph_details

app = Flask(__name__)
graphs = {}


def get_graph_obj(graph_name):
    if graph_name in graphs:
        return graphs[graph_name]
    else:
        raise Exception(f'graph name {graph_name} not found. call /graph/<graph_name>/load_graph first')


@app.route('/graph/<graph_name>/load_graph', methods=['get'])
def graph_location(graph_name):
    nodes_path = f'../FS/{graph_name}/nodes.json'
    edges_path = f'../FS/{graph_name}/edges.csv'
    G = create_graph(nodes_file=nodes_path, edges_file=edges_path)
    graphs[graph_name] = {'graph': G, 'last_update_time': datetime.now()}
    return f'loaded data for {graph_name}'


@app.route('/graph/<graph_name>/get_center_nodes/<n>', methods=['GET'])
def get_top_center_nodes(graph_name, n):
    graph_obj = get_graph_obj(graph_name)
    top_n_nodes = get_graph_top_centers(graph_obj['graph'], int(n))
    return jsonify(top_n_nodes)


@app.route('/graph/<graph_name>/sample_details', methods=['GET'])
def sample_node_details(graph_name):
    graph_obj = get_graph_obj(graph_name)
    sample = sample_graph_details(graph_obj)
    return jsonify(sample)


if __name__ == '__main__':
    app.run(debug=True, port=5000)
