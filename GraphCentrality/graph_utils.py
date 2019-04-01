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

def sample_graph_details(G):
    df = pd.read_csv('data/linkedin_data.csv',
                     usecols=['m_urn', 'age', 'gender', 'ethnicity'], encoding='ISO-8859-1')
    df = df.drop_duplicates()
    gender_hist = df.gender.value_counts().sort_index()
    gender_hist_probs = gender_hist / len(df.age)
    gender_hist_values = gender_hist.index
    gender = np.random.choice(gender_hist_values, 1, p=gender_hist_probs)[0]

    ethnicity_hist = df.ethnicity.value_counts().sort_index()
    ethnicity_hist_probs = ethnicity_hist / len(df.age)
    ethnicity_hist_values = ethnicity_hist.index
    ethnicity = np.random.choice(ethnicity_hist_values, 1, p=ethnicity_hist_probs)[0]

    age_stats = df.groupby(['ethnicity', 'gender']).agg(['mean', 'std'])
    relevant_stats = age_stats.loc[ethnicity, gender]
    age = relevant_stats[1] * np.random.randn() + relevant_stats[0]

    return {'gender': gender, 'ethnicity': ethnicity, 'age': age}
