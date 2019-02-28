wsk action -i create driver_register --docker junoth/register-service driver_register.py --web true
wsk api -i create /drivers post driver_register --response-type http