class Graph:
    def __init__(self):
        self.graph = {}
        self.index_to_city = {}
        self.next_index = 0

    def add_edge(self, city1, city2, weight):
        if city1 not in self.graph:
            self.graph[city1] = {}
            self.index_to_city[self.next_index] = city1
            self.next_index += 1
        if city2 not in self.graph:
            self.graph[city2] = {}
            self.index_to_city[self.next_index] = city2
            self.next_index += 1
        self.graph[city1][city2] = weight

    def floyd_warshall(self):
        dist = self.graph

        for k in range(self.V):
            for i in range(self.V):
                for j in range(self.V):
                    dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])

        return dist
    
    def get_center(self):
        min_distances = []
        for i in range(self.V):
            max_distance = max(self.graph[i])
            min_distances.append(max_distance)

        return min_distances.index(min(min_distances))
    
    def shortest_path(self, city1, city2):
        u = self.graph[city1]
        v = self.graph[city2]
        return self.graph[u][v]

def read_graph_from_file(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()
        graph = Graph()

        for line in lines:
            city1, city2, normal, rain, snow, storm = line.split()
            u = city1
            v = city2
            graph.add_edge(u, v, int(normal))  # Solo agregamos la arista en una dirección

        return graph
