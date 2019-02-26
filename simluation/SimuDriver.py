import random
import time
import geohash
import redis

# Area limitation, in this case is a 50*50 km2 square
lati_min = 42.3471319
lati_max = 42.7071319
long_min = -71.1147236
long_max = -71.4747236
step_size = 0.36

# frequency for updating locations
sleeptime = 2

# redis db connection, p for passenger, d for driver, g for geocode
con_p = redis.Redis(host='127.0.0.1', port=6379, db=0)
con_d = redis.Redis(host='127.0.0.1', port=6379, db=1)
con_g = redis.Redis(host='127.0.0.1', port=6379, db=2)


def sendRedisLocationP(id, latitude, longitude):
    # generate corresponding geocode
    temp_geocode = geohash.encode(latitude, longitude, 5)
    if con_d.exists(id):
        # comparing with previous geocode, if different, delete id from previous geocode
        prev_geocode = con_d.lindex(id, 2)
        if prev_geocode.decode('utf-8') != temp_geocode:
            con_g.srem(prev_geocode, id)
        con_d.delete(id)
    # update location info for driver database and geocode database
    con_d.rpush(id, latitude, longitude, temp_geocode)
    # add time expiration limitation
    con_d.expire(id, 20)
    con_g.sadd(temp_geocode, id);
    con_g.expire(temp_geocode, 20)
    # get passengers in same geocode area
    getPotentialPassengers(temp_geocode, id)

def getPotentialPassengers(geocode, id):
    qualified_list = []
    person_info = []

    # scan all member in the same geocode
    for member in con_g.smembers(geocode):
        # check if it is a passenger
        if member.decode('utf-8')[0] == 'P':
            member = member.decode('utf-8')
            person_info.append(member)
            person_info.append([x.decode('utf-8') for x in con_p.lrange(member,0,1)])
        qualified_list.append(person_info)
    print('For driver: ' + id)
    print(qualified_list)
    print("========================================")



if __name__ == '__main__':
    driver_list = {}
    # Generate random driver ID, start from 'D', with 2 random digit and one random letter, total length is 4
    for i in range(100):
        temp_id = 'D'
        temp_id += str(random.randint(0,9)) + str(random.randint(0,9))
        temp_id += random.choice('qwertyuiopasdfghjklzxcvbnm')
        driver_list[temp_id] = [0,0]


    while True:
        time.sleep(sleeptime)
        # Generating new location for each driver
        for driver in driver_list:
            new_lati = random.random() * step_size + lati_min
            new_longi = random.random() * step_size - long_min
            driver_list[driver] = [new_lati, new_longi]
            # Simulate the interaction with server, driver only sends their ID and longi&latitude
            sendRedisLocationP(driver, new_lati, new_longi)

        for key in con_g.keys('*'):
            print(key.decode('utf-8'), end = ' ')
            print([x.decode('utf-8') for x in con_g.smembers(key)])
        print("--------------------------------------------------")








