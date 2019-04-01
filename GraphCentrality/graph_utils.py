import networkx as nx

node_details_names = ['url', 'name']


def create_graph(nodes_file, edges_file):
    G = nx.read_edgelist(edges_file)
    with open(nodes_file, 'r') as file:
        for line in file:
            node_details = line.split('\t')
            if len(node_details) > 1:
                id = node_details[0]
                if G.has_node(id):
                    for index, detail in enumerate(node_details[1:]):
                        G.nodes()[id][node_details_names[index]] = detail
    return G


def calculate_graph_centrality(G):
    centrality_dict = nx.degree_centrality(G)
    top_nodes = sorted(centrality_dict.items(), key=lambda x: x[1], reverse=True)[:5]
    top_nodes = [int(n[0]) for n in top_nodes]
    return top_nodes
