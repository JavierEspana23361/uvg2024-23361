import random
import csv

#
#
# GNOMESORT
#
#

def gnomesort(arr):
    i = 0
    while i < len(arr):
        if i == 0 or arr[i] >= arr[i-1]:
            i += 1
        else:
            arr[i], arr[i-1] = arr[i-1], arr[i]
            i -= 1
    return arr

#
#
# MERGESORT
#
#

def mergesort(arr):
    if len(arr) > 1:
        mid = len(arr) // 2
        left_half = arr[:mid]
        right_half = arr[mid:]

        mergesort(left_half)
        mergesort(right_half)

        i = j = k = 0

        # Comparación y fusión de las dos mitades ordenadas
        while i < len(left_half) and j < len(right_half):
            if left_half[i] < right_half[j]:
                arr[k] = left_half[i]
                i += 1
            else:
                arr[k] = right_half[j]
                j += 1
            k += 1

        # Verificar elementos restantes en ambas mitades
        while i < len(left_half):
            arr[k] = left_half[i]
            i += 1
            k += 1

        while j < len(right_half):
            arr[k] = right_half[j]
            j += 1
            k += 1

    return arr

#
#
# QUIKCSORT
#
#

# Se lo pedí a Copilot con el promt ("Generate quicksort algorithm"), posteriormente lo modifiqué debido a que no funcionaba correctamente por la recursividad.
def quicksort(arr):
    if len(arr) <= 1:
        return arr

    stack = [(0, len(arr) - 1)]

    while stack:
        low, high = stack.pop()
        if low < high:
            pivot = arr[high]
            i = low - 1
            for j in range(low, high):
                if arr[j] < pivot:
                    i += 1
                    arr[i], arr[j] = arr[j], arr[i]
            arr[i + 1], arr[high] = arr[high], arr[i + 1]

            stack.append((low, i))
            stack.append((i + 2, high))

    return arr

#
#
# RADIXSORT
#
#




def radixsort(arr):
    def countingSort(arr, place):
        size = len(arr)
        output = [0] * size
        count = [0] * 10

        for i in range(0, size):
            index = arr[i] // place
            count[index % 10] += 1

        for i in range(1, 10):
            count[i] += count[i - 1]

        i = size - 1
        while i >= 0:
            index = arr[i] // place
            output[count[index % 10] - 1] = arr[i]
            count[index % 10] -= 1
            i -= 1

        for i in range(0, size):
            arr[i] = output[i]


    max_element = max(arr)

    place = 1
    while max_element // place > 0:
        countingSort(arr, place)
        place *= 10
    return arr

#
#
# SELECTIONSORT
#
#

def selectionsort(arr):
    for i in range(len(arr)):
        min_index = i
        for j in range(i+1, len(arr)):
            if arr[j] < arr[min_index]:
                min_index = j
        arr[i], arr[min_index] = arr[min_index], arr[i]
    return arr

#
#
# SHELLSORT
#
#

def shellsort(arr):
    n = len(arr)
    gap = n // 2
    while gap > 0:
        for i in range(gap, n):
            temp = arr[i]
            j = i
            while j >= gap and arr[j - gap] > temp:
                arr[j] = arr[j - gap]
                j -= gap
            arr[j] = temp
        gap //= 2
    return arr

#
#
# HEAPSORT
#
#



def heapsort(arr):
    def heapify(arr, n, i):
        largest = i
        left = 2 * i + 1
        right = 2 * i + 2

        if left < n and arr[left] > arr[largest]:
            largest = left

        if right < n and arr[right] > arr[largest]:
            largest = right

        if largest != i:
            arr[i], arr[largest] = arr[largest], arr[i]
            heapify(arr, n, largest)


    n = len(arr)

    # Construir un max-heap
    for i in range(n // 2 - 1, -1, -1):
        heapify(arr, n, i)

    # Extraer elementos uno por uno del heap
    for i in range(n - 1, 0, -1):
        arr[i], arr[0] = arr[0], arr[i]  # Intercambiar el máximo con el último elemento
        heapify(arr, i, 0)
    return arr

#
#
# Read SEQUENCE
#
#

def readsequence():
    with open('sequence.csv', newline='') as file:
        reader = csv.reader(file, delimiter=',')
        for row in reader:
            arr = list(map(int, row))
    return arr

#
#
# MENU
#
#

def menu():
    print("1. Gnome Sort")
    print("2. Merge Sort")
    print("3. Quick Sort")
    print("4. Radix Sort")
    print("5. Selection Sort")
    print("6. Shell Sort")
    print("7. Heap Sort")
   
    choice = int(input("Enter your choice: "))
    return choice

def sorts(choice, arr):
    sorted = []
    if choice == 1:
        sorted = gnomesort(arr)
        savesorted(sorted, "gnomesort.csv")
        print("")
    elif choice == 2:
        sorted = mergesort(arr)
        savesorted(sorted, "mergesort.csv")
        print("")
    elif choice == 3:
        sorted = quicksort(arr)
        savesorted(sorted, "quicksort.csv")
        print("")
    elif choice == 4:
        sorted = radixsort(arr)
        savesorted(sorted, "radixsort.csv")
        print("")
    elif choice == 5:
        sorted = selectionsort(arr)
        savesorted(sorted, "selectionsort.csv")
        print("")
    elif choice == 6:
        sorted = shellsort(arr)
        savesorted(sorted, "shellsort.csv")
        print("")
    elif choice == 7:
        sorted = heapsort(arr)
        savesorted(sorted, "heapsort.csv")
        print("")
    else:
        print("Opción no válida")
        print("")

#
#
# Save SORTED
#
#

def savesorted(sorted, filename):
    with open(filename, 'w', newline="") as file:
        writer = csv.writer(file, delimiter=',')
        writer.writerow(sorted)

