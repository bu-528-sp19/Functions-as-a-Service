import redis
import geohash
import json


# Redis configuration
HOST = "10.35.252.107"
# db 0: passenger, db 1: driver, db 2: geohash
driver_db = redis.Redis(host=HOST, port=6379, db=1)
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

    # if the input is valid, update the driver database and geocode database   
    if isValid:
        # convert the latitude and longitude to geocode
        geocode = geohash.encode(float(info['latitude']), float(info['longitude']), 5)

        #find out whether the id exists in driver db
        try:
            if driver_db.exists(info['id']):
                # comparing with previous geocode, if different, delete id from previous geocode
                prev_geocode = driver_db.lindex(info['id'], 2)
                if prev_geocode.decode('utf-8') != geocode:
                    geocode_db.srem(prev_geocode, info['id'])
                driver_db.delete(info['id'])
            # update location info for driver db
            driver_db.rpush(info['id'], info['latitude'], info['longitude'], geocode)
            # add time expiration limitation
            # driver_db.expire(info['id'], 10)
            # update driver info for geocode db
            geocode_db.sadd(geocode, info['id'])
            # add time expiration limitation
            # geocode_db.expire(geocode, 10)
            # make response
            statusCode = 200
            res = json.dumps({ "result": "success" })
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
            "Content-Type": "application/json"
        },
        "statusCode": statusCode,
        "body": res 
    }
