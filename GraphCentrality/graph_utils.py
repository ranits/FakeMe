import networkx as nx
import numpy as np
import pandas as pd
import json

node_details_names = ['url', 'name']


def create_graph(nodes_file, edges_file):
    G = nx.read_edgelist(edges_file, delimiter=',')
    with open(nodes_file, 'r') as json_file:
        data = json.load(json_file)
        for node_id, details in data.items():
            G.nodes()[node_id]['details'] = details
    return G


def get_graph_top_centers(G, n):
    centrality_dict = nx.degree_centrality(G)
    top_nodes = sorted(centrality_dict.items(), key=lambda x: x[1], reverse=True)[:n]
    top_nodes_obj = {n[0]:G.nodes()[n[0]]['details'] for n in top_nodes}
    return top_nodes_obj


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
