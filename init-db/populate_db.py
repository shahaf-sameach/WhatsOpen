from pony.orm import db_session
from database import *
from api import nearbysearch, place_details
from settings import *
import sys

if __name__ == "__main__" :
  # find new places near the cities
  with db_session:
    for city in cities:
      response = nearbysearch(*city)
      for res in response['results']:
        lat = res['geometry']['location']['lat']
        lng = res['geometry']['location']['lng']
        place_id = res['place_id']
        p = Place.get(place_id=place_id)
        if p is None:
          p = Place(place_id = place_id, lat=lat, lng=lng)

  # find new places near existing places
  with db_session:
    places = select(p for p in Place if p.nearby_done == False)[:]
    print "working on %s places" %len(places)
    for indx, place in enumerate(places):
      print "working on %s out of %s" %(indx, len(places))
      response = nearbysearch(place.lat, place.lng)
      for res in response['results']:
        lat = res['geometry']['location']['lat']
        lng = res['geometry']['location']['lng']
        place_id = res['place_id']
        p = Place.get(place_id=place_id)
        if p is None:
          p = Place(place_id = place_id, lat=lat, lng=lng)

      place.set(nearby_done=True)


  # sys.exit()

  # get data for all the places 
  with db_session:
    places = select(p for p in Place if p.done == False)[:]

    print "working on %s places..." %len(places)

    for indx, p in enumerate(places):
      print "working on %s out of %s" %(indx, len(places))
      response = place_details(p.place_id)
      res = response['result']
      city = None
      for address in res['address_components']:
        if 'locality' in address['types']:
          city = City.get(name = address['short_name'])
          if city is None:
            city = City(name = address['short_name'])

      addrss = res['formatted_address']
      lat = res['geometry']['location']['lat']
      lng = res['geometry']['location']['lng']
      place_id = res['place_id']
      name = res['name']
      url = res['url']

      b = Bussiness(name=name, lat=lat, lng=lng, place_id=place_id, address=addrss, city=city, url=url)
      for category in res['types']:
        c = Category.get(name=category)
        if c is None:
          c = Category(name=category)
        
        bc = Bussiness_Category(bussiness=b, category=c)

      
      if 'opening_hours' in res.keys():
        periods = res['opening_hours']['periods']
        try :
          if len(periods) == 1 and periods[0]['open']['day'] == 0 and periods[0]['open']['time'] == '0000':
            for day in range(7):
              data = {'bussiness': b, 'time_open': '0001', 'time_close': '2359'}
              if day == 0:
                try :
                  d = Sunday(**data)
                except :
                  d = Sunday.get(bussiness=b)
                  d.set(**data)
              elif day == 1:
                try :
                  d = Monday(**data)
                except :
                  d = Monday.get(bussiness=b)
                  d.set(**data)
              elif day == 2:
                try :
                  d = Tuesday(**data)
                except :
                  d = Tuesday.get(bussiness=b)
                  d.set(**data)
              elif day == 3:
                try :
                  d = Wednesday(**data)
                except :
                  d = Wednesday.get(bussiness=b)
                  d.set(**data)
              elif day == 4:
                try :
                  d = Thursday(**data)
                except :
                  d = Thursday.get(bussiness=b)
                  d.set(**data)
              elif day == 5:
                try :
                  d = Friday(**data)
                except :
                  d = Friday.get(bussiness=b)
                  d.set(**data)
              elif day == 6:
                try :
                  d = Saturday(**data)
                except :
                  d = Saturday.get(bussiness=b)
                  d.set(**data)
              else :
                print "invalid day when inserting opening hours for bussiness %s, data: %s" %(p.place_id, time_data)

          else :
            for time_data in periods:
              day = time_data['close']['day']
              data = {'bussiness': b, 'time_open': time_data['open']['time'], 'time_close': time_data['close']['time']}
              
              if day == 0:
                try :
                  d = Sunday(**data)
                except :
                  d = Sunday.get(bussiness=b)
                  d.set(**data)
              elif day == 1:
                try :
                  d = Monday(**data)
                except :
                  d = Monday.get(bussiness=b)
                  d.set(**data)
              elif day == 2:
                try :
                  d = Tuesday(**data)
                except :
                  d = Tuesday.get(bussiness=b)
                  d.set(**data)
              elif day == 3:
                try :
                  d = Wednesday(**data)
                except :
                  d = Wednesday.get(bussiness=b)
                  d.set(**data)
              elif day == 4:
                try :
                  d = Thursday(**data)
                except :
                  d = Thursday.get(bussiness=b)
                  d.set(**data)
              elif day == 5:
                try :
                  d = Friday(**data)
                except :
                  d = Friday.get(bussiness=b)
                  d.set(**data)
              elif day == 6:
                try :
                  d = Saturday(**data)
                except :
                  d = Saturday.get(bussiness=b)
                  d.set(**data)
              else :
                print "invalid day when inserting opening hours for bussiness %s, data: %s" %(p.place_id, time_data)
        except Exception as e:
          print "error when inserting opening hours for bussiness %s, data: %s" %(p.place_id, time_data)
          print str(e)
          print "-"*20

      p.set(done=True)



        

