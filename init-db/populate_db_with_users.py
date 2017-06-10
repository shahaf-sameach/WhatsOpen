from pony.orm import db_session
from database import *
from api import nearbysearch, place_details
import random
from loremipsum import get_sentences

if __name__ == "__main__" :
  with db_session:
    users = select(u for u in User)[:]
    businesses = select(b for b in Bussiness)[:]
    for user in users:
      for business in businesses:
        review = Review(rank=random.randint(1, 5), description=get_sentences(1)[0], user=user, bussiness=business)

