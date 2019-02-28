wsk -i action create updateDriver --docker junoth/driver-service ./driver/location/location_update.py --web true

wsk -i action get updateDriver --url

wsk -i action create searchPassenger --docker junoth/driver-service ./driver/location/location_search.py --web true

wsk -i action get searchPassenger --url

wsk -i action create updatePassenger --docker junoth/driver-service ./passenger/location/location_update.py --web true

wsk -i action get updatePassenger --url

wsk -i action create searchDriver --docker junoth/driver-service ./passenger/location/location_search.py --web true

wsk -i action get searchDriver --url