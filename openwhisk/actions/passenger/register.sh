wsk action -i create passenger_register --docker junoth/register-service passenger_register.py --web true
wsk api -i create /passengers /register post passenger_register --response-type http