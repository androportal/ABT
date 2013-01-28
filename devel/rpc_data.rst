rpc_data.py
+++++++++++

	+ This file gives the information about states and cities present in the ``places.db``.
	+ you can locate ``places.db`` in ABTcore/src before running ABTcore but if you have ABTcore
	  running om your machine then locate it at ``/opt/abt/``
	
	
		- import the database connector (database binding)
		- import the twisted modules for executing rpc calls and also to implement the server
		- reactor from the twisted library starts the server with a published object and listens on a given port.
		- import blankspace class to remove spaces in words.
		- note that all the functions to be accessed by the client must have the xmlrpc_ prefix.
		- the client however will not use the prefix to call the functions. 
		
		.. code-block:: python
		
			from sqlite3 import dbapi2 as sqlite 
			from twisted.web import xmlrpc, server 
			from twisted.internet import reactor
			from modules import blankspace 

	+ class ``data`` has two XML-RPC functions.
	
        	- inherit the class from XMLRPC to make it publishable as an rpc service.

		.. code-block:: python
		
			class data(xmlrpc.XMLRPC):

				def __init__(self):
					xmlrpc.XMLRPC.__init__(self)
									
	+ Let's see functions present in the data class
	
	+ def xmlrpc_getStateNames(self):
	
		- This is function to get state list present in the "/opt/abt/places.db"
		- Output: return list of all the distinct states present in "/opt/abt/places.db"
		.. code-block:: python
		
			def xmlrpc_getStateNames(self):
		
				try:
					conn = sqlite.connect("/opt/abt/places.db")
					cur = conn.cursor()
					result = cur.execute("select distinct state from state_city order by state")
					rows = result.fetchall()
					states = []
					for row in rows:
						states.append(list(row))
					conn.close()
					return states
				except:
					return []	
				
				
	+ def xmlrpc_getCityNames(self,queryParams):
	
		- This is function to get city list present in the "/opt/abt/places.db"
		  with its respective state name
		- Input: [statename]
		- Output: return list of all the city names
			present in "/opt/abt/places.db"
		
		.. code-block:: python
		
			def xmlrpc_getCityNames(self,queryParams):
			
				try:
					conn = sqlite.connect("/opt/abt/places.db")
					cur = conn.cursor()
					result =  cur.execute("select city from state_city where state = '%s'"%str(queryParams[0]))
					rows = result.fetchall()
					cities = []
					for row in rows:
						cities.append(row[0])
					citieslist = blankspace.remove_whitespaces(cities)
					return citieslist
				except:
					return []
			
