---
author: Fouad Elbakly
title: "Princeton Algorithms Course Part 1"
numbersections: true
output: pdf_document
---

# Elementary Sorts

## Selection sort

* Algorithm scans from left to right.
* Entries left of index are fixed and sorted
* Entries right of index are not fixes and are not sorted

### Java Implementation

~~~Java
for (int i = 0; i < N; i++) {
 int min = i;
 for (int j = i + 1; j < N; j++)
  if (a[j] < a[min])
   min = j;
 swap(arr[i], arr[min]);
}
~~~

### Time Complexity

Takes $O(n^2)$ time due to worst case scenario, outer loop N times, and first
inner loop iteration N times.

## Insertion Sort

* Scans from left to right
* Elements to the left of index are sorted
* Elements to the right of index have not been seen and are not sorted

### Java Implementation

~~~Java
for (int i = 0; i < N; i++) {
 for (int j = i; j > 0; j--) {
  if (a[i] < a[j - 1])
   swap(a[j], a[j - 1])
  else
   break
 }
}
~~~

### Time Complexity

Takes $O(n^2)$ time due to worst case scenario being a reversely ordered array.
Thus, N iterations in outer loop and N iterations in inner loop.

## Shellsort

* Moves entries more than one position at a time by h-sorting an array.
* When h is large, the number of subarrays is small,  and as h gets smaller
 the array is nearly in order.
* Use the Knuth increment sequence $h = 3h + 1,  (1, 4, 13, 40, 121, 364,...)$

### Java Implementation

~~~Java
int h = 1;

while (h < N/3)
 h = 3*h - 1;

while (h >= 1) {
 for (int i = h; i < N; i++)
  for (int j = i; j >= h && a[j] < a[j-h]; j -= h)
   swap(a[j], a[j - h]);
 h /= 3;
}
~~~

### Time Complexity

Worst case time complexity is $O(n^2)$, but the average time complexity is
unknown.

## Shuffle Sort (Knuth Shuffle)

* Loop from left to right
* in interation i pick a random integer r between 0 and i.
* swap a[i] and a[r]

### Java Implementation

~~~Java
int N = a.length;
for (int i = 0; i < N; i++) {
 int r = Random.nextInt(i + 1);
 swap(a[i], a[r]);
}
~~~

### Time Complexity

Takes $O(n)$ time due to the need to loop through the array of size n only once.

