import redis
import json

HOST = "127.0.0.1"

def find_all_locations(params):
    # db 0: passenger, db 1: driver, db 2: geohash
    passenger_db = redis.Redis(host=HOST, port=6379, db=0)

    #find all passenger locations in driver db
    try:
        # create a empty list
        passenger_locations = []
        # iterate the database
        for key in passenger_db.scan_iter("*"):
            # store the id and locations in the dict
            location = {}
            location['id'] = key
            location['latitude'] = passenger_db.lindex(key, 0)
            location['longitude'] = passenger_db.lindex(key, 1)
            # store the object into list
            passenger_locations.append(location)
        # make response
        statusCode = 200
        res = json.dumps({ 
          "result": "success" 
          "locations": passenger_locations  
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