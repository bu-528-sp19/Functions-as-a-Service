wsk action -i create passenger_location_update --docker junoth/driver-service location_update.py --web true
wsk api -i create /passengers /location post passenger_location_search --response-type http