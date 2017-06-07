import requests

API_URL = 'https://maps.googleapis.com/maps/api/place'

def nearbysearch(lat_pos, long_pos, radius=1000):
  location = "%s,%s" %(lat_pos,long_pos)
  payload = {'location': location, 'radius': radius, 'key': 'AIzaSyBoZvKz08_259aboWBJsDdX0QoTNO5QN6U'}
  url = "%s/nearbysearch/json" % API_URL
  r = requests.get(url, params=payload)
  return r.json()

def place_details(place_id):
  payload = {'placeid' : place_id, 'key' : 'AIzaSyBoZvKz08_259aboWBJsDdX0QoTNO5QN6U'}
  url = "%s/details/json" % API_URL
  r = requests.get(url, params=payload)
  return r.json()




if __name__ == '__main__':
  #print nearbysearch(-33.8670, 151.1957)
  print place_details('ChIJ-yNYuDiuEmsRHJEvZbrghP8')
    