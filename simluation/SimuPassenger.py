import random
import time
import geohash
import redis

lati_min = 42.3471319
lati_max = 42.7071319
long_min = -71.1147236
long_max = -71.4747236
step_size = 0.36
sleeptime = 2
con_p = redis.Redis(host='127.0.0.1', port=6379, db=0)
con_d = redis.Redis(host='127.0.0.1', port=6379, db=1)
con_g = redis.Redis(host='127.0.0.1', port=6379, db=2)


def sendRedisLocationP(id, latitude, longitude):
    temp_geocode = geohash.encode(latitude, longitude, 5)
    if con_p.exists(id):
       # prev_geocode = str(con_p.lindex(id, 2))
        prev_geocode = con_p.lindex(id, 2)
        if prev_geocode.decode('utf-8') != temp_geocode:
            con_g.srem(prev_geocode, id)
            '''       
            print(prev_geocode)
            print(temp_geocode)
            print(con_g.sismember(prev_geocode,id))
           '''
        con_p.delete(id)
    con_p.rpush(id, latitude, longitude, temp_geocode)
    con_p.expire(id, 20)
    con_g.sadd(temp_geocode, id)
    con_p.expire(temp_geocode, 20)



if __name__ == '__main__':
    passenger_list = {}
    for i in range(200):
        temp_id = 'P'
        temp_id += str(random.randint(0,9)) + str(random.randint(0,9))
        temp_id += random.choice('qwertyuiopasdfghjklzxcvbnm')
        passenger_list[temp_id] = [0,0]


    while True:
        time.sleep(sleeptime)
        for passenger in passenger_list:
            new_lati = random.random() * step_size + lati_min
            new_longi = random.random() * step_size - long_min
            passenger_list[passenger] = [new_lati, new_longi]
            sendRedisLocationP(passenger, new_lati, new_longi)

        for key in con_g.keys('*'):
            print(key.decode('utf-8'), end = ' ')
            print([x.decode('utf-8') for x in con_g.smembers(key)])
        print("--------------------------------------------------")








