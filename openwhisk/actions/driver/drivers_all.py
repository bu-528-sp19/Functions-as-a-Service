import redis
import json

HOST = "10.35.252.107"

def main(params):
    # db 0: passenger, db 1: driver, db 2: geohash
    driver_db = redis.Redis(host=HOST, port=6379, db=1)

    # find all driver locations in driver db
    try:
        # create a empty list
        driver_locations = []
        # iterate the database
        for key in driver_db.scan_iter("*"):
            # store the id and locations in the dict
            location = {}
            location['id'] = key.decode('utf-8')
            location['latitude'] = driver_db.lindex(key, 0).decode('utf-8')
            location['longitude'] = driver_db.lindex(key, 1).decode('utf-8')
            # store the object into list
            driver_locations.append(location)
        # make response
        statusCode = 200
        res = json.dumps({ 
          "result": "success", 
          "locations": driver_locations
        })
    except (redis.exceptions.ConnectionError, redis.exceptions.TimeoutError):
        statusCode = 403
        res = json.dumps({"result": "connection failed"})
    except Exception as e:
        statusCode = 403
        res = json.dumps({"result": "something error", "msg": str(e)})
        
    return {
        "headers": {
            "Content-Type": "application/json",
        },
        "statusCode": statusCode,
        "body": res
    }
