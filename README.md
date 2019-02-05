# Project Name: Function as a Service  

## 1.   Vision and Goals Of The Project:

The goals of our project can be summarized as two parts:
	* Firstly, we want to build a mobile application whose scenario can leverage the strength of FaaS. For this app, the computing requirement for OpenWhisk is dynamic and may have high variance for a very short time.
	* Secondly, we also want to see the performance of FaaS when it exercises with dynamic amounts of load. We may also go deep into the OpenWhisk’s source code to evaluate the benchmark of the system.
Our final product would be an application that assists taxi taking process, it receives real time data of drivers and passengers. Considering some regular events such as rush hour, and some sudden events such as a heavy rain or the end of an activity for a specific area, all those conditions will lead to a significant variance of the taxi requirement. Therefore, the scenario is obviously suitable for FaaS. <br>

The functions covered by application can be divided into two layers:
	* The application will directly read the location data of passengers and drivers and provide users a hot spot map to directly show the distribution.
	* The application will recommend the location that is most likely to make a deal for both passengers and drivers, that would be a trade-off between distance and probability.z`

** **
## 2. Users/Personas Of The Project:

The final application will be used by either passengers or drivers. The running system, especially the computational part which using FaaS service, would be further evaluated by researcher. <br>
Application End User:
	* Passengers: Passengers can see a real time hot spot map that shows the distribution of drivers and other passengers. If it is not likely to hail a taxi for their current location, the application will suggest them a better location.
	* Drivers: Most functions are same with the passengers’ side, but considering higher movement flexibility comparing with passengers, the suggesting algorithm would be different for drivers.
System Researcher:
	* We will start from using historical knowledge, which would give us high freedom to simulate some extreme conditions that maximize the performance of FaaS when testing the application, the system researchers can use it to further evaluate the characteristics of FaaS platform.    

** **

## 3.   Scope and Features Of The Project:

The Scope places a boundary around the solution by detailing the range of features and functions of the project. This section helps to clarify the solution scope and can explicitly state what will not be delivered as well.

** **

## 4. Solution Concept

This section provides a high-level outline of the solution.

Global Architectural Structure Of the Project:

This section provides a high-level architecture or a conceptual diagram showing the scope of the solution. If wireframes or visuals have already been done, this section could also be used to show how the intended solution will look. This section also provides a walkthrough explanation of the architectural structure.


Design Implications and Discussion:

This section discusses the implications and reasons of the design decisions made during the global architecture design.

 ** **

## 5. Acceptance criteria

This FaaS application could:  
1. Store and present real-time taxi trip data on websites or mobile application.  
2. Show how many trips passed through georect defined by two latitude/longitude points.  
3. Show how many trips were occurring at a given point in time.  
4. Show how many trips started or stopped within a georect, and the sum total of their fares.  
5. Pair the customers and drivers for a trip.  


 ** **

## 6.  Release Planning:

Iteration1(Due to 2.14):  
1. set up one-node Kubernetes Cluster on our own computers.  
2. deploy OpenWhisk over Kubernetes.  
3. Automated the installation and deployment of OpenWhisk.  
4. Provide a demo to test the OpenWhisk.  

Iteration2(Due to 2.28):  
1. implement our first feature: store data from resources to DB.  
2. implement a front-end to present data stored in DB.  

Iteration3(Due to 3.21):  
1. implement a program to mimic the behaviors of taxi drivers and customers.  
2. test based on demo2.  
3. explore & develope more features.  

Iteration4(Due to 4.04):  
1. explore & develope more features.  
...  

Iteration5(Due to 4.18):  
1. scale up Kubernetes to multiple nodes.  
2. move service from local computer to public cloud providers.  


---
Mentors:
- Tommy Unger
- Tsz Yan Lam

---
Team Members:
- Zhuyu Li
- Yuhang Miao
- Yuxuan Su
- Lian Duan
