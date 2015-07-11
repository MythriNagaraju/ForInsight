Insight Data Engineering - Coding Challenge
===========================================================

1. Median.java:

   Uses 2 heaps - a max heap for storing the smaller of the numbers in the incoming stream and 
   a min heap for storing the larger half. In Java, a PriorityQueue is used to create and maintain
   both these heaps.

   Chose heap datastructure , because with every incoming tweet, it takes only O(logn) to insert an
   element onto the heap and constant time to look up the roots of both the heaps and calculate the
   median. So the total running time for n tweets is O(nlogn)

2. WordCount.java:
   
   Uses a HashMap to maintain a count of all the unique words in the entire input file. Chose HashMap
   because at any time, to check if a word already exists takes constant time.

3. Used Java version "1.6.0_65"

4. Initially coded both the programs in Python(Python 2.7.6), but when I profiled the programs, the program
   to calculate median took a relatively long time to run compared to Java, for large datasets.  

