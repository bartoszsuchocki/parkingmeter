# parkingmeter
REST application for managing the city parking spaces with faked database. 
App provides following functionalities:
+ starting and stopping parking meter (e.g. when driver wants to park/finish parking),
+ checking if driver started parking meter (e.g. when his car is on a parking place and parking operator wants to check whether the driver is being charged),
+ calculating charges for drivers,
+ calculating parking incomes for a given day,
+ paying for parking (money transaction is not implemented).

Additional features/facilities:
- There can be many driver types (e.g. regular, disabled) who can have their own costs for parking,
- There can be many currencies in which drivers pay for parking and in which charges/statistics are calculated.

## Used technologies:
* Java 8
* Spring Boot
* Spring REST
* Spring Test
* JUnit
