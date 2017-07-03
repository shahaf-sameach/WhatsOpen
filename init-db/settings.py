import os

#Database settings
MYSQL = {'type' : 'mysql', 'host' : 'localhost' , 'user' : 'root' , 'db' : 'whatsopen'}


#Google Maps api settings
API_URL = 'https://maps.googleapis.com/maps/api/place'
API_KEY = 'AIzaSyBoZvKz08_259aboWBJsDdX0QoTNO5QN6U'


#Geo Locations settings
Ashdod = [31.801447, 34.643497]
Bat_Yam = [32.017136, 34.745441]
Tel_Aviv = [32.109333, 34.855499]
Haifa = [32.794044, 34.989571]
Herzliya = [32.166313, 34.843311]
Rehovot = [31.894756, 34.809322]
Jerusalem = [31.771959, 35.217018]

cities = [Ashdod, Bat_Yam, Tel_Aviv, Haifa, Herzliya, Rehovot, Jerusalem]

try :
	import local_settings
except ImportError:
	pass

