from Graph import *

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
            shortest_distance = graph.shortest_path(city1, city2)
            print(f"La ruta más corta entre las ciudades {city1} y {city2} es de {shortest_distance} horas.")
        elif option == "2":
            center = graph.get_center()
            print(f"La ciudad que queda en el centro del grafo es: {center}")
        elif option == "3":
            # Implementa la lógica para modificar el grafo
            pass
        elif option == "4":
            break
        else:
            print("Opción no válida. Intente de nuevo.")

if __name__ == "__main__":
    main()
