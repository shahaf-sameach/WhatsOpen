from pony.orm import *
from settings import *

db = Database(MYSQL['type'], host=MYSQL['host'], user=MYSQL['user'], db=MYSQL['db'])

class Place(db.Entity):
  place_id = PrimaryKey(str)
  lat = Required(float)
  lng = Required(float)
  done = Required(bool, default=False)
  nearby_done = Required(bool, default=False)

class City(db.Entity):
  id = PrimaryKey(int, auto=True)
  name = Required(str, unique=True)
  bussinesses = Set('Bussiness')

class Category(db.Entity):
  id = PrimaryKey(int, auto=True)
  name = Required(str, unique=True)
  bussiness_categories = Set('Bussiness_Category')

class Bussiness(db.Entity):
  id = PrimaryKey(int, auto=True)
  place_id = Required(str, unique=True)
  name = Required(str)
  lat = Required(float)
  lng = Required(float)
  address = Optional(str)
  description = Optional(str)
  url = Optional(str)
  city = Required(City)
  bussiness_categories = Set('Bussiness_Category')
  sundayes = Set('Sunday')
  mondayes = Set('Monday')
  tuesdayes = Set('Tuesday')
  wednesdayes = Set('Wednesday')
  thursdayes = Set('Thursday')
  fridayes = Set('Friday')
  saturdayes = Set('Saturday')

class Bussiness_Category(db.Entity):
  category = Required(Category)
  bussiness = Required(Bussiness)
  PrimaryKey(category, bussiness)

class Sunday(db.Entity):
  bussiness = PrimaryKey(Bussiness)
  time_open = Optional(str, default='1000')
  time_close = Optional(str, default='1800')

class Monday(db.Entity):
  bussiness = PrimaryKey(Bussiness)
  time_open = Optional(str, default='1000')
  time_close = Optional(str, default='1800')

class Tuesday(db.Entity):
  bussiness = PrimaryKey(Bussiness)
  time_open = Optional(str, default='1000')
  time_close = Optional(str, default='1800')

class Wednesday(db.Entity):
  bussiness = PrimaryKey(Bussiness)
  time_open = Optional(str, default='1000')
  time_close = Optional(str, default='1800')

class Thursday(db.Entity):
  bussiness = PrimaryKey(Bussiness)
  time_open = Optional(str, default='1000')
  time_close = Optional(str, default='1800')

class Friday(db.Entity):
  bussiness = PrimaryKey(Bussiness)
  time_open = Optional(str, default='1000')
  time_close = Optional(str, default='1800')

class Saturday(db.Entity):
  bussiness = PrimaryKey(Bussiness)
  time_open = Optional(str, default='1000')
  time_close = Optional(str, default='1800')


db.generate_mapping(create_tables=True)