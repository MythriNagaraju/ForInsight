# example of program that calculates the total number of times each word has been tweeted.
from collections import defaultdict #Counter
import sys
import profile

class WordCount:
    """
    Calculates the number of occurences of each word in tweets
    """
    def __init__(self,infile,outfile):
        """
        Initialize dictionary to hold distinct words and their
        counts
        """
        self.words = []
        # Initialise count for all words to 0
        self.input_file = infile
        self.output_file = outfile
        self.result_dict = defaultdict(int)

    def read_input(self):
        """
        Read input file, containing all the tweets
        """
	with open(self.input_file) as input:
            #self.words = [word for word in input.read().split()]
            for word in input.read().split():
                self.result_dict[word] += 1

    #def increment(self):
    #    for word in self.words:
    #        self.result_dict[word] += 1
        
    def write_to_output(self):
        """
        Write distinct words, in alphabetic order, and their counts,
        to output file
        """
	with open(self.output_file,'w') as output:
	    #for key in sorted(self.result_dict, key=self.result_dict.get):
            self.result_dict.keys().sort()
	    for key in self.result_dict.keys():
                output.write(key.ljust(150,' ')+str(self.result_dict[key])+'\n')

def main():
    if len(sys.argv) != 3:
	#there should be 2 inputs - input and output file path names
	print 'Please provide 2 file paths'
    else:
	infile, outfile = sys.argv[1], sys.argv[2]
	word_counter = WordCount(infile,outfile)
	word_counter.read_input()
	word_counter.write_to_output()

if __name__=='__main__': main()
profile.run('main()')
