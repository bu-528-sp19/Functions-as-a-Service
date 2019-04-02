# Simulation 
## 1.Overview
This documentation will describe the simulation work of our project. From basic simulating to passenger-driver pairing.
## 2.Basic part
Before pairing, passengers and drivers move and update their locations to server continuously. The requests sent from users(passengers and drivers) should contain following parameters:
* ID: each user should have a unique ID. In our project, we randomly generate 16 digit id for each user, and use first digit to distinguish between passenger and driver.
* Location: Combination of latitude and longitude  

To simulate movement of user, we also set different moving parameters for passengers(0.9m/s) and drivers(10m/s), and generate new locations based on that.  
Server deals with requests from users and store the location at Redis database
## 3. Basic Pairing
### 3.1 Description
Let's start from considering a simple process of taking a taxi, there will be one passenger who want to taking a taxi to a specific destination, one driver who want to pick a passenger and make money by sending this passenger to his/her destination.

During this process. there are several states for both passenger and driver:  
For passenger:
1. Waiting for order: passenger specify his/her destination but haven't been assigned a specific driver for his/her order  
2. Waiting for driver: system schedules a driver for this passenger, one order is set up. Passenger waits for the driver picking him/her.
3. Heading to destination: driver picks passenger, and sends passenger to the destination.
4. Order finished: Driver has sent passenger to the destination, order finished. In this case, passenger will leave this system and driver starts waiting for a new order.  

For driver:
1. Waiting for order: driver waits for the order, he keeps this state until system assigns a order for him.
2. Picking passenger: system schedules a driver for this driver, one order is set up. Driver will get passenger's location and pick him.
3. Heading to destination: driver picks passenger, and sends passenger to the destination.
4. Order finished: Driver has sent passenger to the destination, order finished. In this case, passenger will leave this system and driver starts waiting for a new order.  

Below are some figures to show the process:  
<img width="50%" height="50%" src="https://github.com/bu-528-sp19/Functions-as-a-Service/blob/documentation/images/simulation/basic_pro_1.jpg" align=center/>  
<img width="50%" height="50%" src="https://github.com/bu-528-sp19/Functions-as-a-Service/blob/documentation/images/simulation/basic_pro_2.jpg" align=center/>  
<img width="50%" height="50%" src="https://github.com/bu-528-sp19/Functions-as-a-Service/blob/documentation/images/simulation/basic_pro_3.jpg" align=center/>  
<img width="50%" height="50%" src="https://github.com/bu-528-sp19/Functions-as-a-Service/blob/documentation/images/simulation/basic_pro_4.jpg" align=center/>  
### 3.2 Implementation for basic condition
Implementation can be divided into two parts, one part play role of user, in this example we use a Java program to generate fake data, one thread for passenger, one for driver. corresponding actions file at OW side is written in python.  
Information sent from drivers and passengers are composed of:
* passenger/driver ID: a unique ID
* location: including latitude and longitude
* state: definition of state is mentioned before
* destination: for specific state
* time stamp: for metric part and future use
#### 3.3 Handling work by OW
For requests sent from users, server updates their information using Redis database. Redis stores each user's info using hash value.
