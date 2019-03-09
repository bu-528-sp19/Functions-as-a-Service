import pymongo
from bson.json_util import dumps
import re
import json
import uuid


# Mongodb host configuation
HOST = "mongodb://172.17.0.4:27017/"
client = pymongo.MongoClient(HOST)
db = client.passenger
col = db.info 


# Input Validation
def validate(col, info):
    errors = {}
    isValid = True

    # Validate the name
    if not info["name"]:
        errors["name"] = "Name is required"
        isValid = False 
    if len(info["name"]) > 10:
        errors["name"] = "The length of name exceeds the limit"
        isValid = False

    # Validate the phone number    
    if not info["phone_number"]:
        errors["phone_number"] = "Phone number is required"
    elif not re.match(r"^\(?([0-9]{3})\)?[-.●]?([0-9]{3})[-.●]?([0-9]{4})$", info["phone_number"]):
        errors["phone_number"] = "Invalid phone number"
        isValid = False
    elif col.find({ "phone_number": info["phone_number"] }).count() > 0:
        errors["phone_number"] = "Phone number already exists"
        isValid = False 

    return isValid, errors


def register_passenger(args):

    # Receive all the information in the request
    passenger_info = {
        "name": args.get("name", ""),
        "phone_number": args.get("phone_number", ""),
    }

    # Validate the input
    isValid, errors = validate(col, passenger_info)

    if isValid:
        passenger_info["id"] = "P" + str(uuid.uuid4().hex)
        try:
            col.insert_one(passenger_info)
            statusCode = 201
            res = dumps(col.find_one(passenger_info))
        except Exception as e:
            statusCode = 500
            res = json.dumps({ "error": e })
    else:
        statusCode = 403
        res = json.dumps(errors)

    return {
        "headers": {
            "Content-Type": "application/json"
        },
        "statusCode": statusCode,
        "body": res
    }

    

