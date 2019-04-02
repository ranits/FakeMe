import networkx as nx
import numpy as np
import pandas as pd

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


def get_graph_top_centers(G, n):
    centrality_dict = nx.degree_centrality(G)
    top_nodes = sorted(centrality_dict.items(), key=lambda x: x[1], reverse=True)[:n]
    top_nodes = [int(n[0]) for n in top_nodes]
    return top_nodes


def get_details_df_from_graph(G):
    df = pd.read_csv('data/linkedin_data.csv',
                     usecols=['m_urn', 'age', 'gender', 'ethnicity'], encoding='ISO-8859-1')
    df = df.drop_duplicates()
    df = df[['age', 'gender', 'ethnicity']]
    return df


def sample_graph_details(G):
    sampled_details = {}
    filtered_df = get_details_df_from_graph(G)
    rand_cols = list(filtered_df.columns)
    np.random.shuffle(rand_cols)
    for col in rand_cols:
        col_data = filtered_df[col].dropna()
        if np.issubdtype(col_data.dtype, np.number):
            mean = col_data.mean()
            std = col_data.std()
            col_sample = std * np.random.randn() + mean
            filtered_df = filtered_df[col_data.isin(range(int(mean - std), int(mean + std)))]
        else:
            hist = col_data.value_counts()
            hist_values = hist.index
            hist_probs = hist / len(col_data)
            col_sample = np.random.choice(hist_values, 1, p=hist_probs)[0]
            filtered_df = filtered_df[col_data == col_sample]
        sampled_details[col] = col_sample
    return sampled_details
