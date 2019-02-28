import redis
import geohash
import json


# Redis Configuration
HOST = "172.17.0.6"
# db 0: passenger, db 1: driver, db 2: geohash
passenger_db = redis.Redis(host=HOST, port=6379, db=0)
geocode_db = redis.Redis(host=HOST, port=6379, db=2)


# Input validation 
def validate(info):
    errors = ""
    isValid = True
    if 'id' not in info or info['id'] == "":
        errors += "Driver Id is required\n"
        isValid = False
    if 'latitude' not in info or info['latitude'] == "":
        errors += "Latitude is required\n"
        isValid = False
    else:
        try:
            d = float(info['latitude'])
            if d < -90 or d > 90:
                errors += "Latitude is invalid\n"
                isValid = False
        except ValueError:
            errors += "Latitude is invalid\n"
            isValid = False
    if 'longitude' not in info or info['longitude'] == "":
        errors += "Longitude is required\n"
        isValid = False
    else:
        try:
            d = float(info['longitude'])
            if d < -180 or d > 180:
                errors += "Longitude is invalid"
                isValid = False
        except ValueError:
            errors += "Longitude is invalid"
            isValid = False

    return isValid, errors

def main(params):

    info = {
        "id": params.get("driver_id", ""),
        "latitude": params.get("latitude", ""),
        "longitude": params.get("longitude", "")
    }

    isValid, errors = validate(info)

    if isValid:
        # convert the latitude and longitude to geocode
        geocode = geohash.encode(float(info['latitude']), float(info['longitude']), 5)

        qualified_list = []
        try:
            # scan all member in the same geocode
            for member in geocode_db.smembers(geocode):
                # check if it is a passenger
                if member.decode('utf-8')[0] == 'P':
                    pid = member.decode('utf-8')
                    passenger = {"id": pid}
                    person_info = [x.decode('utf-8') for x in passenger_db.lrange(pid,0,1)]
                    passenger["latitude"] = person_info[0]
                    passenger["longitude"] = person_info[1]
                    qualified_list.append(passenger)

            statusCode = 200
            res = json.dumps({"result": "success", "passengers": qualified_list})
        except (redis.exceptions.ConnectionError, redis.exceptions.TimeoutError):
            statusCode = 403
            res = json.dumps({"result": "connection failed"})
        except Exception as e:
            statusCode = 403
            res = json.dumps({"result": "something error", "msg": str(e)})
    else:
        statusCode = 403
        res = json.dumps({"result": errors})
        
    return {
        "headers": {
            "Content-Type": "application/json",
        },
        "statusCode": statusCode,
        "body": res 
    }