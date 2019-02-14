class Passenger:
	def __init__(self, _x, _y):
		self.x = _x
		self.y = _y



#currently x/y range from 1 to 10
class Street:
	def __init__(self, blockNumber):
		self.blockNumber = blockNumber
		self.block = [[0 for x in range(blockNumber)] for y in range(blockNumber)]

	def addPassenger(self, passenger):
		x = passenger.x
		y = passenger.y
		if (x >=0 and x <= 10 and y >=0 and y <= 10):
			x /= (10/self.blockNumber)
			y /= (10/self.blockNumber)
			x = int(x)
			y = int(y)
			self.block[x][y] += 1

	def show(self):
		maxi = 0
		maxj = 0
		maxNumber = 0
		print("the total data distribution is\n")
		for i in range(self.blockNumber):
			print(self.block[i])
			for j in range(self.blockNumber):
				if (self.block[i][j] > maxNumber):
					maxNumber = self.block[i][j]
					maxi = i
					maxj = j

		print("\nThe block with most passenger call is\n", i, j)
		print("\nThe number of the passenger in the block is\n", maxNumber)


if __name__ == "__main__":
	street = Street(3)
	print("\nplease enter the position of the passengers looking for taxi")
	for i in range(10):
		inp = input().split(" ")
		x = int(inp[0])
		y = int(inp[1])
		tempPassenger = Passenger(x, y)
		street.addPassenger(tempPassenger)
	street.show()


