import csv
import random

def generate():
    sequence = []
    for i in range(3000):
        sequence.append(random.randint(0, 10000))
    return sequence

def save(sequence, filename):
    with open(filename, 'w', newline="") as csvfile:
        writer = csv.writer(csvfile, delimiter=',')
        writer.writerow(sequence)

sequence = generate()
save(sequence, 'sequence.csv')
