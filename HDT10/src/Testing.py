import unittest
from unittest.mock import patch
from io import StringIO
import networkx as nx
import matplotlib.pyplot as plt
import random
from Main import main, read_graph_from_file, modify_graph

class TestMain(unittest.TestCase):

    def test_shortest_path(self):
        graph = nx.DiGraph()
        graph.add_edge('A', 'B', weight=5)
        graph.add_edge('B', 'C', weight=3)
        graph.add_edge('A', 'C', weight=8)

        with patch('builtins.input', side_effect=['1', 'A', 'C', '4']):
            with patch('sys.stdout', new=StringIO()) as fake_output:
                main()
                output = fake_output.getvalue().strip()
                self.assertEqual(output, "La ruta más corta entre las ciudades A y C es de 8 horas.")

    def test_center_of_graph(self):
        graph = nx.DiGraph()
        graph.add_edge('A', 'B', weight=5)
        graph.add_edge('B', 'C', weight=3)
        graph.add_edge('C', 'D', weight=2)
        graph.add_edge('D', 'A', weight=4)

        with patch('builtins.input', side_effect=['2', '4']):
            with patch('sys.stdout', new=StringIO()) as fake_output:
                main()
                output = fake_output.getvalue().strip()
                self.assertEqual(output, "Las ciudades que quedan en el centro del grafo son: ['B', 'C', 'D']")

    def test_modify_graph(self):
        graph = nx.DiGraph()
        graph.add_edge('A', 'B', weight=5)
        graph.add_edge('B', 'C', weight=3)
        graph.add_edge('C', 'D', weight=2)
        graph.add_edge('D', 'A', weight=4)

        with patch('builtins.input', side_effect=['3', '2', 'A', 'E', '6', '4']):
            with patch('sys.stdout', new=StringIO()) as fake_output:
                main()
                output = fake_output.getvalue().strip()
                self.assertEqual(output, "La nueva ruta más corta entre las ciudades A y E es de 11 horas.\nLas nuevas ciudades que quedan en el centro del grafo son: ['B', 'C', 'D']")
    
if __name__ == '__main__':
    unittest.main()

def test_read_graph_from_file():
    G = read_graph_from_file('src/empty.txt')
    assert isinstance(G, nx.DiGraph)
    assert len(G.nodes) == 0
    assert len(G.edges) == 0

    G = read_graph_from_file('src/one_line.txt')
    assert isinstance(G, nx.DiGraph)
    assert len(G.nodes) == 2
    assert len(G.edges) == 1
    assert G.has_edge('city1', 'city2')
    assert G['city1']['city2']['weight'] == 1

    G = read_graph_from_file('src/multiple_lines.txt')
    assert isinstance(G, nx.DiGraph)
    assert len(G.nodes) == 4
    assert len(G.edges) == 4
    assert G.has_edge('city1', 'city2')
    assert G.has_edge('city2', 'city3')
    assert G.has_edge('city3', 'city4')
    assert G.has_edge('city4', 'city1')
    assert G['city1']['city2']['weight'] == 2
    assert G['city2']['city3']['weight'] == 3
    assert G['city3']['city4']['weight'] == 4
    assert G['city4']['city1']['weight'] == 1

    G = read_graph_from_file('src/invalid_format.txt')
    assert isinstance(G, nx.DiGraph)
    assert len(G.nodes) == 0
    assert len(G.edges) == 0

test_read_graph_from_file()

def test_modify_graph():
    graph = nx.DiGraph()
    graph.add_edge('A', 'B', weight=2)
    graph.add_edge('B', 'C', weight=3)
    graph.add_edge('C', 'D', weight=4)

    graph_copy = graph.copy()
    modify_graph(graph_copy, '1', 'B', 'C')
    assert not graph_copy.has_edge('B', 'C')
    assert graph_copy.has_edge('A', 'B')
    assert graph_copy.has_edge('C', 'D')

    graph_copy = graph.copy()
    modify_graph(graph_copy, '2', 'D', 'E', '5')
    assert graph_copy.has_edge('D', 'E')
    assert graph_copy['D']['E']['weight'] == 5

    graph_copy = graph.copy()
    modify_graph(graph_copy, '3', 'A', 'B', 'rain')
    assert graph_copy['A']['B']['weight'] == 'rain'

    graph_copy = graph.copy()
    modify_graph(graph_copy, '5', 'A', 'B')
    assert graph_copy == graph

    print("All test cases passed!")

test_modify_graph()