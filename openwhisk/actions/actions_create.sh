wsk -i action create updateDriver --docker junoth/driver-service ./driver/location/location_update.py --web true

wsk -i action get updateDriver --url

wsk -i action create searchPassenger --docker junoth/driver-service ./driver/location/location_search.py --web true

wsk -i action get searchPassenger --url

wsk -i action create updatePassenger --docker junoth/driver-service ./passenger/location/location_update.py --web true

wsk -i action get updatePassenger --url

wsk -i action create searchDriver --docker junoth/driver-service ./passenger/location/location_search.py --web true

wsk -i action get searchDriver --url

wsk -i action create findAllDriver --docker junoth/driver-service ./driver/drivers_all.py --web true

wsk -i action get findAllDriver --url

wsk -i action create findAllPassenger --docker junoth/driver-service ./passenger/passenger_all.py --web true

wsk -i action get findAllPassenger --url
