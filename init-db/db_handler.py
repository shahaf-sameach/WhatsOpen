from csv_handler import CSVHandler
import logging
import sys
from database import *
from settings import *

class DBHandler :

  def __init__(self) :
    self.logger = logging.getLogger(self.__class__.__name__)

  def get_team(self , team_name) :
    data = []
    with db_session :
      team_data = select(p for p in Team if p.HomeTeam == team_name or p.AwayTeam == team_name)[:]
      for t in team_data :
        data.append([t.Date , t.HomeTeam , t.AwayTeam , t.FTHG , t.FTAG , t.League])

    if len(data) >= 1 :
      return data

    raise Exception("No data for team %s in DB" % team_name)


  def insert_data(self , data) :
    try :
      with db_session :
        Team(**data)
    except pony.orm.core.CommitException as e:
      self.logger.error(str(e))
      return 0

    return 1

  def get_all_data(self) :
    data = list()
    with db_session :
      all_teams = select(p for p in Team)[:]
      for t in all_teams :
        data.append([t.Date , t.HomeTeam , t.AwayTeam , t.FTHG , t.FTAG , t.League])

      return data

  def get_all_teams_names(self) :
    data = set()
    with db_session :
      names = select((p.HomeTeam , p.AwayTeam) for p in Team)
      for name in names :
        data.add(name[0])
        data.add(name[1])

    return data

  def get_league_teams(self , league = None , team = None) :
    data = []
    with db_session :
      if league == None :
        league = get(p.League for p in Team if p.HomeTeam == team or p.AwayTeam == team)

      team_data = select(p for p in Team if p.League == league)[:]
      for t in team_data :
        data.append([t.Date , t.HomeTeam , t.AwayTeam , t.FTHG , t.FTAG , t.League])

    if len(data) >= 1 :
      return data

    raise Exception("No data for league %s in DB" % league)

  def get_league_teams_names(self , league) :
    data = set()
    with db_session :
      names = select((p.HomeTeam , p.AwayTeam) for p in Team if p.League == league)
      for name in names :
        data.add(name[0])
        data.add(name[1])

    return data

  def get_team_league(self , team) :
    with db_session :
      return get(p.League for p in Team if p.HomeTeam == team or p.AwayTeam == team)

  def get_all_leagues(self) :
    with db_session:
      return select(t.League for t in Team)[:]

  def get_avg_goals(self, team) :
    with db_session :
      try :
        avg_goals_out = (avg(t.FTHG for t in Team if t.HomeTeam == team) + avg(t.FTAG for t in Team if t.AwayTeam == team))/2
        avg_goals_in = (avg(t.FTHG for t in Team if t.AwayTeam == team) + avg(t.FTAG for t in Team if t.HomeTeam == team))/2
        return (avg_goals_in, avg_goals_out)
      except Exception, e:
        print "errorr team = %s" % team
        raise e

  def get_team_goals(self, team) :
    with db_session :
      team_goals_scored = select(t.FTHG for t in Team if t.HomeTeam == team)[:] + \
                          select(t.FTAG for t in Team if t.AwayTeam == team)[:]
      team_goals_received = select(t.FTHG for t in Team if t.AwayTeam == team)[:]\
                            + select(t.FTAG for t in Team if t.HomeTeam == team)[:]
      return (team_goals_scored, team_goals_received)

  def get_query(self , team) :
    data = list()
    with db_session :
      txt = select((p.Querytext) for p in Query if p.Team == team)
      for word in txt :
          data = word.split()
    return data


if __name__ == "__main__" :
  db = DBHandler()
  # league = sys.argv[1]

  # teams = db.get_league_teams_names(league)

  # for team in sorted(teams):
  #   print team





