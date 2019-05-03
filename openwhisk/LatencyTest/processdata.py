import numpy as np


if __name__ == "__main__":
	filename = "./record.csv"
	data = np.loadtxt(filename, delimiter=',')
	print(data)
