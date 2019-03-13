# Simulation 
## 1.Overview
## 2.Basic condition
### 2.1 Condition Description
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



