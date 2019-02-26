import redis
import geohash
import json

def main(args):
    driver_db = redis.Redis(host = "172.17.0.6", port = 6379)
    geocode_db = redis.Redis(host = "172.17.0.7", port = 6379)
    info = {
        "id": args.get("driver_id", ""),
        "latitude": args.get("latitude", "")
        "longitude": args.get("longitude", "")
    }

    # validate the input 
    res = validate(r, info)

    # if the input is valid, update the driver database and geocode database   
    if res[0]:
        # convert the latitude and longitude to geocode
        geocode = geohash.encode(info.latitude, info.longitude, 5)
        # find out whether the id exists in driver db
        if driver_db.exists(info.id):
            # comparing with previous geocode, if different, delete id from previous geocode
            prev_geocode = driver_db.lindex(info.id, 2)
            if prev_geocode.decode('utf-8') != geocode:
                geocode_db.srem(prev_geocode, info.id)
            driver_db.delete(info.id)
        # update location info for driver db
        driver_db.rpush(info.id, info.latitude, info.longitude, geocode)
        # add time expiration limitation
        driver_db.expire(info.id, 20)
        # update driver info for geocode db
        geocode_db.sadd(geocode, info.id)
        # add time expiration limitation
        geocode_db.expire(geocode, 20)
        # make response
        statusCode = 200
        res = json.dump({ "update": "success" })
    else:
        statusCode = 403
        res = json.dump(res[1])
        
    return {
        "headers": {
            "Content-Type": "application/json",
        },
        "statusCode": statusCode,
        "body": res 
    }

def validate(info):
    errors = {}
    isValid = True
    if not info.id:
        errors.driver_id = "Driver Id is required"
        isValid = False
    if not info.latitude:
        errors.latitude = "Latitude is required"
        isValid = False
    else:
        try:
            d = double(info.latitude)
            if d < -90 or d > 90:
                errors.latitude = "Latitude is invalid"
                isValid = False
        except ValueError:
            errors.latitude = "Latitude is invalid"
            isValid = False
    if not info.longitude:
        errors.longitude = "Longitude is required"
        isValid = False
    else:
        try:
            d = double(info.longitude)
            if d < -180 or d > 180:
                errors.longitude = "Longitude is invalid"
                isValid = False
        except ValueError:
            errors.longitude = "Longitude is invalid"
            isValid = False

    return [isValid, errors]