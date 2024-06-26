import networkx as nx
import matplotlib.pyplot as plt
import random

def read_graph_from_file(file_path):
    G = nx.DiGraph()
    with open(file_path, 'r') as file:
        lines = file.readlines()
        for line in lines:
            num = random.randint(1, 4) # Generar un clima aleatorio para cada ciudad
            city1, city2, normal, rain, snow, storm = line.split()
            if num == 1:
                G.add_edge(city1, city2, weight=int(rain))
            elif num == 2:
                G.add_edge(city1, city2, weight=int(snow))
            elif num == 3:
                G.add_edge(city1, city2, weight=int(storm))
            else:
                G.add_edge(city1, city2, weight=int(normal))

    return G

def main():
    file_path = 'src/logistica.txt'
    graph = read_graph_from_file(file_path)

    while True:
        print("\nOpciones:")
        print("1. Encontrar ruta más corta entre dos ciudades.")
        print("2. Encontrar ciudad que queda en el centro del grafo.")
        print("3. Modificar el grafo.")
        print("4. Finalizar el programa.")

        option = input("Ingrese la opción deseada: ")

        if option == "1":
            city1 = input("Ingrese el nombre de la ciudad de origen: ")
            city2 = input("Ingrese el nombre de la ciudad de destino: ")
            shortest_path = nx.shortest_path(graph, source=city1, target=city2, weight='weight')
            shortest_distance = nx.shortest_path_length(graph, source=city1, target=city2, weight='weight')
            print(f"La ruta más corta entre las ciudades {city1} y {city2} es de {shortest_distance} horas.")
            if len(shortest_path) > 2:
                cities = " -> ".join(shortest_path)
                print(f"Ciudades intermedias: {cities}")  
        elif option == "2":
            betweenness = nx.betweenness_centrality(graph)
            max_betweenness = max(betweenness.values())
            center_nodes = [node for node, centrality in betweenness.items() if centrality == max_betweenness]
            print(f"Las ciudades que quedan en el centro del grafo son: {center_nodes}")
        elif option == "3":
            graph = modify_graph(graph)
            shortest_path = nx.shortest_path(graph, source=city1, target=city2, weight='weight')
            shortest_distance = nx.shortest_path_length(graph, source=city1, target=city2, weight='weight')
            print(f"La nueva ruta más corta entre las ciudades {city1} y {city2} es de {shortest_distance} horas.")
            if len(shortest_path) > 2:
                cities = " -> ".join(shortest_path)
                print(f"Ciudades intermedias: {cities}")  

            betweenness = nx.betweenness_centrality(graph)
            max_betweenness = max(betweenness.values())
            center_nodes = [node for node, centrality in betweenness.items() if centrality == max_betweenness]
            print(f"Las nuevas ciudades que quedan en el centro del grafo son: {center_nodes}")
        elif option == "4":
            break
        else:
            print("Opción no válida. Intente de nuevo.")

def modify_graph(graph):
    while True:
        print("\nOpciones de modificación:")
        print("1. Interrupción de tráfico entre un par de ciudades.")
        print("2. Establecer una conexión entre dos ciudades.")
        print("3. Indicar el clima entre un par de ciudades.")
        print("4. Finalizar la modificación.")

        option = input("Ingrese la opción deseada: ")

        if option == "1":
            city1 = input("Ingrese el nombre de la primera ciudad: ")
            city2 = input("Ingrese el nombre de la segunda ciudad: ")
            if graph.has_edge(city1, city2):
                graph.remove_edge(city1, city2)
                print(f"Se ha interrumpido el tráfico entre {city1} y {city2}.")
            else:
                print(f"No existe una conexión entre {city1} y {city2}.")
        elif option == "2":
            city1 = input("Ingrese el nombre de la primera ciudad: ")
            city2 = input("Ingrese el nombre de la segunda ciudad: ")
            weight = input("Ingrese el tiempo de viaje entre las ciudades: ")
            graph.add_edge(city1, city2, weight=int(weight))
            print(f"Se ha establecido una conexión entre {city1} y {city2} con un tiempo de viaje de {weight} horas.")
        elif option == "3":
            city1 = input("Ingrese el nombre de la primera ciudad: ")
            city2 = input("Ingrese el nombre de la segunda ciudad: ")
            weather = input("Ingrese el clima (normal, lluvia, nieve o tormenta): ")
            if graph.has_edge(city1, city2):
                graph[city1][city2]['weight'] = weather
                print(f"Se ha actualizado el clima entre {city1} y {city2} a {weather}.")
            else:
                print(f"No existe una conexión entre {city1} y {city2}.")
        elif option == "4":
            break
        else:
            print("Opción no válida. Intente de nuevo.")

    return graph




if __name__ == "__main__":
    main()