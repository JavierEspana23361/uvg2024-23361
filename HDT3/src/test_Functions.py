import unittest
from Functions import *

class TestGnomeSort(unittest.TestCase):
    def test_empty_list(self):
        arr = []
        self.assertEqual(gnomesort(arr), [])

    def test_sorted_list(self):
        arr = [1, 2, 3, 4, 5]
        self.assertEqual(gnomesort(arr), [1, 2, 3, 4, 5])

    def test_reverse_sorted_list(self):
        arr = [5, 4, 3, 2, 1]
        self.assertEqual(gnomesort(arr), [1, 2, 3, 4, 5])

    def test_unsorted_list(self):
        arr = [3, 1, 4, 2, 5]
        self.assertEqual(gnomesort(arr), [1, 2, 3, 4, 5])

    def test_duplicate_elements(self):
        arr = [3, 1, 4, 2, 5, 2, 4]
        self.assertEqual(gnomesort(arr), [1, 2, 2, 3, 4, 4, 5])


class TestGnomeSort(unittest.TestCase):
    def test_empty_list(self):
        arr = []
        self.assertEqual(gnomesort(arr), [])

    def test_sorted_list(self):
        arr = [1, 2, 3, 4, 5]
        self.assertEqual(gnomesort(arr), [1, 2, 3, 4, 5])

    def test_reverse_sorted_list(self):
        arr = [5, 4, 3, 2, 1]
        self.assertEqual(gnomesort(arr), [1, 2, 3, 4, 5])

    def test_unsorted_list(self):
        arr = [3, 1, 4, 2, 5]
        self.assertEqual(gnomesort(arr), [1, 2, 3, 4, 5])

    def test_duplicate_elements(self):
        arr = [3, 1, 4, 2, 5, 2, 4]
        self.assertEqual(gnomesort(arr), [1, 2, 2, 3, 4, 4, 5])

class TestMergeSort(unittest.TestCase):
    def test_empty_list(self):
        arr = []
        self.assertEqual(mergesort(arr), [])

    def test_sorted_list(self):
        arr = [1, 2, 3, 4, 5]
        self.assertEqual(mergesort(arr), [1, 2, 3, 4, 5])

    def test_reverse_sorted_list(self):
        arr = [5, 4, 3, 2, 1]
        self.assertEqual(mergesort(arr), [1, 2, 3, 4, 5])

    def test_unsorted_list(self):
        arr = [3, 1, 4, 2, 5]
        self.assertEqual(mergesort(arr), [1, 2, 3, 4, 5])

    def test_duplicate_elements(self):
        arr = [3, 1, 4, 2, 5, 2, 4]
        self.assertEqual(mergesort(arr), [1, 2, 2, 3, 4, 4, 5])

class TestHeapSort(unittest.TestCase):
    def test_empty_list(self):
        arr = []
        self.assertEqual(heapsort(arr), [])

    def test_sorted_list(self):
        arr = [1, 2, 3, 4, 5]
        self.assertEqual(heapsort(arr), [1, 2, 3, 4, 5])

    def test_reverse_sorted_list(self):
        arr = [5, 4, 3, 2, 1]
        self.assertEqual(heapsort(arr), [1, 2, 3, 4, 5])

    def test_unsorted_list(self):
        arr = [3, 1, 4, 2, 5]
        self.assertEqual(heapsort(arr), [1, 2, 3, 4, 5])

    def test_duplicate_elements(self):
        arr = [3, 1, 4, 2, 5, 2, 4]
        self.assertEqual(heapsort(arr), [1, 2, 2, 3, 4, 4, 5])


class TestShellSort(unittest.TestCase):
    def test_empty_list(self):
        arr = []
        self.assertEqual(shellsort(arr), [])

    def test_sorted_list(self):
        arr = [1, 2, 3, 4, 5]
        self.assertEqual(shellsort(arr), [1, 2, 3, 4, 5])

    def test_reverse_sorted_list(self):
        arr = [5, 4, 3, 2, 1]
        self.assertEqual(shellsort(arr), [1, 2, 3, 4, 5])

    def test_unsorted_list(self):
        arr = [3, 1, 4, 2, 5]
        self.assertEqual(shellsort(arr), [1, 2, 3, 4, 5])

    def test_duplicate_elements(self):
        arr = [3, 1, 4, 2, 5, 2, 4]
        self.assertEqual(shellsort(arr), [1, 2, 2, 3, 4, 4, 5])

class TestSelectionSort(unittest.TestCase):
    def test_empty_list(self):
        arr = []
        self.assertEqual(selectionsort(arr), [])

    def test_sorted_list(self):
        arr = [1, 2, 3, 4, 5]
        self.assertEqual(selectionsort(arr), [1, 2, 3, 4, 5])

    def test_reverse_sorted_list(self):
        arr = [5, 4, 3, 2, 1]
        self.assertEqual(selectionsort(arr), [1, 2, 3, 4, 5])

    def test_unsorted_list(self):
        arr = [3, 1, 4, 2, 5]
        self.assertEqual(selectionsort(arr), [1, 2, 3, 4, 5])

    def test_duplicate_elements(self):
        arr = [3, 1, 4, 2, 5, 2, 4]
        self.assertEqual(selectionsort(arr), [1, 2, 2, 3, 4, 4, 5])


class TestRadixSort(unittest.TestCase):
    def test_empty_list(self):
        arr = []
        self.assertEqual(radixsort(arr), [])

    def test_sorted_list(self):
        arr = [1, 2, 3, 4, 5]
        self.assertEqual(radixsort(arr), [1, 2, 3, 4, 5])

    def test_reverse_sorted_list(self):
        arr = [5, 4, 3, 2, 1]
        self.assertEqual(radixsort(arr), [1, 2, 3, 4, 5])

    def test_unsorted_list(self):
        arr = [3, 1, 4, 2, 5]
        self.assertEqual(radixsort(arr), [1, 2, 3, 4, 5])

    def test_duplicate_elements(self):
        arr = [3, 1, 4, 2, 5, 2, 4]
        self.assertEqual(radixsort(arr), [1, 2, 2, 3, 4, 4, 5])

class TestQuicksort(unittest.TestCase):
    def test_empty_list(self):
        arr = []
        self.assertEqual(quicksort(arr), [])

    def test_sorted_list(self):
        arr = [1, 2, 3, 4, 5]
        self.assertEqual(quicksort(arr), [1, 2, 3, 4, 5])

    def test_reverse_sorted_list(self):
        arr = [5, 4, 3, 2, 1]
        self.assertEqual(quicksort(arr), [1, 2, 3, 4, 5])

    def test_unsorted_list(self):
        arr = [3, 1, 4, 2, 5]
        self.assertEqual(quicksort(arr), [1, 2, 3, 4, 5])

    def test_duplicate_elements(self):
        arr = [3, 1, 4, 2, 5, 2, 4]
        self.assertEqual(quicksort(arr), [1, 2, 2, 3, 4, 4, 5])

if __name__ == '__main__':
    unittest.main()