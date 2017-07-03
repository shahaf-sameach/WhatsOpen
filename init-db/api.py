import requests
from settings import *

def nearbysearch(lat_pos, long_pos, radius=1000):
  location = "%s,%s" %(lat_pos,long_pos)
  payload = {'location': location, 'radius': radius, 'key': API_KEY}
  url = "%s/nearbysearch/json" % API_URL
  r = requests.get(url, params=payload)
  return r.json()

def place_details(place_id):
  payload = {'placeid' : place_id, 'key' : API_KEY}
  url = "%s/details/json" % API_URL
  r = requests.get(url, params=payload)
  return r.json()

  