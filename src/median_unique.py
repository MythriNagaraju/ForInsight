# example of program that calculates the median number of unique words per tweet.
from heapq import heappush, heappop, heappushpop
from collections import defaultdict
import sys
import profile

class Median:
    """
    Calculates the median for each incoming tweet

    How it works:

    Maintains 2 heaps - one max heap for the smaller half of 
    the lengths of unique words in the incoming tweets and 
    a min heap for the bigger half. This way at any time, 
    calculating the median would be either the average of the
    roots of both the heaps(in case of even number of tweets)
    or the root of the min heap(considering that the root of
    the min heap is always greater than the root of the max 
    heap), which can be done in O(1) time . This way at the 
    arrival of each tweet, the number of unique words for the
    current tweet is inserted into the heap (insertion is 
    always made to the max heap and then both the heaps are 
    rearranged such that max heap can either have one greater 
    element or equal number of elements as the min heap, which
    takes O(logn) time) and the median is calculated in O(1)
    + O(logn) time.  
    """

    def __init__(self, infile, outfile):
        """
        Initializes min and max heaps, used to calculate
        median of continuous stream of tweets
        """
        self.infile = infile
        self.outfile = outfile
        self.min_heap, self.max_heap = [], [] 
        self.num_tweets = 0
    
    def process_tweets(self):
        """
        Calculates median after each incoming tweet
        """
        with open(self.infile) as input,\
            open(self.outfile, 'wb') as output:
            tweets = input.readlines()
            for tweet in tweets:
                num = self.calculate_num_words(tweet)
                self.insert(num)
                output.write(str(self.calculate_median())+'\n')

    def calculate_num_words(self, tweet):
        """
        Calculate number of unique words in each tweet
        """
        distinct_words = defaultdict(int)
        for word in tweet.split():
            distinct_words[word] +=1
        return len(distinct_words.keys())

    def insert(self,num):
        """
        Inserts the number of unique words in the incoming
        tweet on to the max heap
        """
        #print "inserting", num
        #A number is always inserted to max heap
        if self.num_tweets%2==0:
            heappush(self.max_heap, -1*num)
            self.num_tweets+=1
            if len(self.min_heap)==0:
                return
            if -1*self.max_heap[0] > self.min_heap[0]:
                #After inserting into max heap, check if 
                #root of max heap is greater than the root
                #of min heap. If yes, swap the roots, 
                #because elements in min heap should always
                #be greater than or equal to those in max 
                #heap
                to_min_heap=-1*heappop(self.max_heap)
                to_max_heap=heappop(self.min_heap)
                heappush(self.max_heap, -1*to_max_heap)
                heappush(self.min_heap, to_min_heap)
        else:
            to_min_heap = -1*heappushpop(self.max_heap, -1*num)
            heappush(self.min_heap, to_min_heap)
            self.num_tweets += 1

    def calculate_median(self):
        if self.num_tweets%2==0:
            #print "what s in max heap and min heap", self.max_heap, self.min_heap
            median =  (-1*self.max_heap[0]+self.min_heap[0])/2.0
        else:
            median =  -1*self.max_heap[0]
        return median 
        
def main():
    if len(sys.argv) != 3:
	#there should be 2 inputs - input and output file path names
	print 'Please provide 2 file paths'
    else:
	infile, outfile = sys.argv[1], sys.argv[2]
	median_unique = Median(infile,outfile)
        median_unique.process_tweets()

if __name__=='__main__': 
    main()
profile.run('main()')
