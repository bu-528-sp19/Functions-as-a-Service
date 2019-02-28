wsk action -i create driver_location_update --docker junoth/driver-service location_update.py --web true
wsk api -i create /drivers /location post driver_location_update --response-type http