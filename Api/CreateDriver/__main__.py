import pymongo
from bson.json_util import dumps
import re
import json

def main(args):
    # Config mongodb
    client = pymongo.MongoClient("mongodb://172.17.0.4:27017/")
    db = client.driver
    col = db.info

    # Receive all the information in the request
    driver_info = {
        "name": args.get("name", ""),
        "model": args.get("model", ""),
        "license_plate": args.get("license_plate", ""),
        "phone_number": args.get("phone_number", "")
    }

    # Validate the input
    ret = validate(col, driver_info)

    if ret[0]:
        col.insert_one(driver_info)
        statusCode = 201
        res = dumps(col.find_one(driver_info))
    else:
        statusCode = 403
        res = json.dump(ret[1])

    return {
        "headers": {
            "Content-Type": "application/json"
        },
        "statusCode": statusCode,
        "body": res
    }

def validate(col, driver_info):
    errors = {}
    isValid = True
    if not driver_info.name:
        errors.name = "Name is required"
        isValid = False
    # if len(info.name) > 10:
    #     errors.name = "The length of name exceeds the limit"
    #     isValid = False
    # if not model:
    #     errors.model = "Car model is required"
    #     isValid = False
    # if not license_plate:
    #     errors.license_plate = "License plate is required"
    #     isValid = False
    # elif not re.match(r"^[A-Z]{1,3}-[A-Z]{1,2}-[0-9]{1,4}$", info.license_plate):
    #     errors.license_plate = "Invalid license plate"
    #     isValid = False
    # elif col.find({ "license_plate": info.license_plate }).count() > 0:
    #     errors.license_plate = "License plate already exists"
    #     isValid = False
    # if not phone_number:
    #     errors.phone_number = "Phone number is required"
    # elif not re.match(r"^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$", info.phone_number)
    #     errors.phone_number = "Invalid phone number"
    #     isValid = False
    # elif col.find({ "phone_number": info.phone_number }).count() > 0:
    #     errors.phone_number = "Phone number already exists"
    #     isValid = False
    return [isValid, errors]        

