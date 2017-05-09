import os


MYSQL = {'type' : 'mysql', 'host' : 'localhost' , 'user' : 'root' , 'db' : 'whatsopen'}

try :
	import local_settings
except ImportError:
	pass

