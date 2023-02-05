import numpy as np
import pandas as pd


class NaiveBayesBernoulli:
    def __init__(self, alpha=1):
        self.alpha = alpha
        return

    def fit(self, x, y):
        # count how many 1s are in y
        count_y1 = np.count_nonzero(y)
        # count how many 0s are in y
        count_y0 = len(y) - np.count_nonzero(y)
        # P(y = 1), probability of positive reviews
        py_pos = count_y1 / len(y)

        # P(y = 0), probability of negative reviews
        py_neg = count_y0 / len(y)  # or 1-py_pos
        log_py_pos = np.log(py_pos)
        log_py_neg = np.log(py_neg)
        self.log_py = np.vstack((log_py_neg,log_py_pos))

        # creates 2 2D numpy arrays one containing only positive reviews and one all the negative
  

        pos = np.zeros(shape=(count_y1, x.shape[1]), dtype=int)
        neg = np.zeros(shape=(count_y0, x.shape[1]), dtype=int)

        countpos = 0
        countneg = 0
        for i in range(len(y)):
            if y[i] == 1:
                pos[countpos] = x[i]
                countpos = countpos + 1

            else:
                neg[countneg] = x[i]
                countneg = countneg + 1


        # creates 2 2D numpy arrays containing the frequencies of words that appeared (1s)
        freq_pos = np.zeros(shape=(pos.shape[1]), dtype=int)
        freq_neg = np.zeros(shape=(neg.shape[1]), dtype=int)

        for j in range(neg.shape[1]):
            for i in range(neg.shape[0]):
                if neg[i][j] == 1:
                    freq_neg[j] = freq_neg[j] + 1

        for j in range(pos.shape[1]):
            for i in range(pos.shape[0]):
                if pos[i][j] == 1:
                    freq_pos[j] = freq_pos[j] + 1


        prob_pos = np.zeros(shape=(pos.shape[1]))
        prob_neg = np.zeros(shape=(neg.shape[1]))
        for j in range(len(freq_pos)):
            prob_pos[j] = (freq_pos[j] + self.alpha) / (pos.shape[0] + 2*self.alpha)
            prob_neg[j] = (freq_neg[j] + self.alpha) / (neg.shape[0] + 2*self.alpha)

        prob_x_y = np.vstack((prob_neg,prob_pos))
        self.log_positive = np.log(prob_x_y)  
        self.log_negative = np.log(1 - prob_x_y)

    def predict(self,x):
        # each column is a review
        X = np.transpose(x)
        # matrix multiplication gives attention to 1s if element = 0 then res = 0
        log_probs_1s = np.dot(self.log_positive,X)
        # matrix multiplication gives attention to 0s if element = 1 then res = 0
        log_probs_0s = np.dot(self.log_negative,1-X)
        # using log so the result doesn't need multiplication but addition
        log_final_result = log_probs_1s + log_probs_0s + self.log_py 
        # finds max probability
        prediction = np.argmax(log_final_result, axis=0)  
        return prediction