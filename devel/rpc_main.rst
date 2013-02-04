.. toctree::
   :numbered:
   
rpc_main.py
+++++++++++
	+ In ``ABTcore/abtserver`` folder you can see ``rpc_main.py`` file.
	+ This is the main file which implement the server and publish the object to given port and execute the xmlrpc functions.
	+ We have used twisted module to implement server and executing xmlrpc calls.also to publish the object and make it to listen on the given port through reactor and start the service by running the reactor.
		
		- import the xmlrpc and server for implementing server
		- import ElementTree for creating and parsing ``xml`` file 
		- used sqlite3 as Database server and import dbapi2 driver for connection
		- used sqlAlchemy(ORM) and bind it with sqlite3 for query
	   	- ``modules`` is another package in ``ABTcore`` for removing blankspaces from words
	   	- import all rpc file`s to publish it's object with ``abt`` object.
	   	
		.. code-block:: python
		
			#!/usr/bin/python
			from twisted.web import xmlrpc, server
			from twisted.internet import reactor
			from sqlalchemy.orm import sessionmaker,scoped_session
			from xml.etree import ElementTree as et
			from sqlite3 import dbapi2 as sqlite
			from sqlalchemy.orm import join
			from decimal import *
			from modules import blankspace
			import datetime
			import os,sys
			import getopt
			import dbconnect
			import rpc_organisation
			import rpc_groups
			import rpc_account
			import rpc_transaction
			import rpc_data
			import rpc_user
			import rpc_reports
			import rpc_getaccountsbyrule
			
	
	   	- ``rpc_main`` is a class file 
	   	- create class ``abt`` and initialize it __init__(self):
	   	- class ``abt`` is derived class of ``xmlrpc.XMLRPC`` base class.
	   	- all the def to be accessed by the client must have the xmlrpc_prefix. 
	   	- The client however will not use the prefix to call the functions.
	   	- ``abt`` class contains ``xmlrpc`` def that we will discuss later.
	   	
		.. code-block:: python
			  
		   	from twisted.web import xmlrpc, server
		   	
			class abt(xmlrpc.XMLRPC): 

				def __init__(self):
					xmlrpc.XMLRPC.__init__(self)
		
		
		- As we have imported all the nested XML-RPC resource,so that create one handler ``abt`` calls another if a method with a given prefix is called.
		
		.. code-block:: python
		
			# create the instance of class abt and it's subHandler`s

			abt = abt()

			groups=rpc_groups.groups()
			abt.putSubHandler('groups',groups)

			account=rpc_account.account()
			abt.putSubHandler('account',account)

			organisation = rpc_organisation.organisation()
			abt.putSubHandler('organisation',organisation)

			transaction=rpc_transaction.transaction()
			abt.putSubHandler('transaction',transaction)

			data=rpc_data.data()
			abt.putSubHandler('data',data)

			reports=rpc_reports.reports()
			abt.putSubHandler('reports',reports)

			user=rpc_user.user()
			abt.putSubHandler('user',user)

			getaccountsbyrule=rpc_getaccountsbyrule.getaccountsbyrule()
			abt.putSubHandler('getaccountsbyrule',getaccountsbyrule)
			
		- and publish that handelr instance ``abt`` to server .
		- let`s see ``def runabt()`` which is outside ``class abt():``.
	
		.. code-block:: python
		
			def runabt():
		
				#publish the object and make it to listen on the given port through reactor
				reactor.listenTCP(7081, server.Site(abt))
				
				#start the service by running the reactor.
				reactor.run()
				
		- the below code from ``def runabt()`` is for ``daemonise given object``
		
		.. code-block:: python
		
			#the code to daemonise published instance.
		 	# Daemonizing abt
			# Accept commandline arguments
			# A workaround for debugging
			def usage():
				print "Usage: %s [-d|--debug] [-h|--help]\n" % (sys.argv[0])
				print "\t-d (--debug)\tStart server in debug mode. Do not fork a daemon."
				print "\t-d (--help)\tShow this help"

			try:
				opts, args = getopt.getopt(sys.argv[1:], "hd", ["help","debug"])
			except getopt.GetoptError:
				usage()
				os._exit(2)

			debug = 0
			for opt, arg in opts:
				if opt in ("-h", "--help"):
					usage()
					os.exit(0)
				elif opt in ("-d", "--debug"):
					debug = 1

			# Do not fork if we are debug mode
			if debug == 0:
				try:
					pid = os.fork()
				except OSError, e:
					raise Exception, "Could not fork a daemon: %s" % (e.strerror)

				if pid != 0:
					os._exit(0)

				# Prevent it from being orphaned
				os.setsid()
	
				# Change working directory to root
				os.chdir("/")

				# Change umask
				os.umask(0)

				# All prints should be replaced with logging, preferrably into syslog
				# The standard I/O file descriptors are redirected to /dev/null by default.
				if (hasattr(os, "devnull")):
					REDIRECT_TO = os.devnull
				else:
					REDIRECT_TO = "/dev/null"

				# Redirect the standard I/O file descriptors to the specified file.  Since
				# the daemon has no controlling terminal, most daemons redirect stdin,
				# stdout, and stderr to /dev/null.  This is done to prevent side-effects
				# from reads and writes to the standard I/O file descriptors.

				# This call to open is guaranteed to return the lowest file descriptor,
				# which will be 0 (stdin), since it was closed above.
				os.open(REDIRECT_TO, os.O_RDWR)	# standard input (0)

				# Duplicate standard input to standard output and standard error.
				os.dup2(0, 1)			# standard output (1)
				os.dup2(0, 2)			# standard error (2)
			 
	+ Now,first of we will see where this ``runabt()`` called.
		
	+ In ``ABTcore`` you can find ``abtstart`` python file.
			
 		- this is an executable file.
		- this is calling function calls runabt() from ``rpc_main.py``and start the ``ABTcore``. 
		
	+ In ``abtstart`` you are able to see system command's , it's basically written to maintain databse files loacally.
		
		- import rpc_main
		- provided ``places.db`` database file in ``ABTcore/src`` which maintains data about ``state`` and ``cities``.
		- copy ``places.db`` database file in /opt/abt/.
		- create folder ``abt`` to maintain all files need to work ABT.
		- create ``db`` directory to host all the database files , to keep all record of organisation.
		
	+ All the database files of organisations will be there at ``/opt/abt/db``.
		
		.. code-block:: python
		
			#!/usr/bin/python
			import os, sys, signal 
			from time import sleep
			from subprocess import Popen, PIPE
			import abtserver.rpc_main

			if __name__ == "__main__":
	
				try:
					if os.path.exists("/opt/abt") == False: # check for abt directory , if false then 
						try:
							print "creating directory"
							os.system("mkdir -p /opt/abt/db") # create abt directory and db to host all databases
							os.system("cp ./src/places.db /opt/abt/") # copy places.db from /src to abt
				
						except:
							print "can't create directory somthing is wrong"

					else:
					 	if os.path.exists("/opt/abt/db") == False: # check for abt/db directory , if false then
							try:
								print "creating directory"
								os.system("mkdir /opt/abt/db") # create db directory in abt
								os.system("cp ./src/places.db /opt/abt/") # copy places.db from /src to abt
							except:
								print "can't create directory somthing is wrong"
						else:
							print "db already exist"
				
						print "abt already exist"
					#calling runabt() from rpc_main.py which is in abtserver
					abtserver.rpc_main.runabt()
		
				except:
					print "inside exception"
	

	+ let's see def in class ``abt``.
	
	+ def xmlrpc_getOrganisationNames(self):
	
		- function is used to return the list of
		  organsation names found in abt.xml located at /opt/abt/
		- we have imported ``ElementTree`` for parsing xml file.
		
		.. code-block:: python
				
			def xmlrpc_getOrganisationNames(self):
				# calling the function getOrgList for getting list of organisation nodes.
				orgs = dbconnect.getOrgList() 
				# initialising an empty list for organisation names
				orgnames = [] 
				for org in orgs:
					# find orgname tag in abt.xml file
					orgname= org.find("orgname")
					# append the distinct orgname 
					if orgname.text not in orgnames:
						orgnames.append(orgname.text)
				return orgnames
			
			
	+ def xmlrpc_getFinancialYear(self,arg_orgName):
		- This function will return a list of financial
		  years for the given organisation.  
				
		- Input: [organisationname]
	
		- calls ``getOrgList()`` def from ``dbconnect`` file
		
		- returns financialyear list for paritcular organisation
			in the format "dd-mm-yyyy"
		
		.. code-block:: python
		
			def xmlrpc_getFinancialYear(self,arg_orgName):
				#get the list of organisations from the /opt/abt/abt.xml file.
				#we will call the getOrgList function to get the nodes.
				orgs = dbconnect.getOrgList()
				#Initialising an empty list to be filled with financial years 
				financialyearlist = []
				for org in orgs:
					orgname = org.find("orgname")
					if orgname.text == arg_orgName:
						financialyear_from = org.find("financial_year_from")
						financialyear_to = org.find("financial_year_to")
						from_and_to = [financialyear_from.text, financialyear_to.text]
						financialyearlist.append(from_and_to)
		
				return financialyearlist
		
	+ def xmlrpc_getConnection(self,queryParams):
		
		- This function is used to return the client_id for sqlite 
		  engine found in ``dbconnect.py``
			 
		- Input: [organisation name]	
		
		- Output: Returns the client_id integer
		
		.. code-block:: python
			
			def xmlrpc_getConnection(self,queryParams):
				self.client_id = dbconnect.getConnection(queryParams)
				return self.client_id	
		
	+ def xmlrpc_closeConnection(self,client_id):
		
		- This function is used close the connetion 
		  with sqlite for client_id
			 
		- Input: client_id	
		
		+ Output: Returns boolean True if conncetion closed
		
		.. code-block:: python	
		
			def xmlrpc_closeConnection(self,client_id):
				dbconnect.engines[client_id].dispose()
				del dbconnect.engines[client_id]
				return True		
		
		
	+ def xmlrpc_Deploy(self,queryParams):
		
		- The function will generate the database name 
		  based on the organisation name and time stap.
		 
		- provided the name of the database is a combination of, 
			first character of organisation name,
			time stap as "dd-mm-yyyy-hh-MM-ss-ms" 
			
		- An entry will be made in the xml file 
		  for the currosponding organisation.
		  
		- This function deploys a database instance for
		  an organisation for a given financial year.
		  
		- It will call to getConnection and establish the connection 
		  for created database
		
		- also create the metadata(tables) given in ``dbconnect`` for that organisation
		  using sqlAlchemy.
		
		- create the ``Views`` for the particular organisation.
		 
		- It add manually ``groupnames`` ad ``subgroups`` to it's corresponding class
		  tables ``Groups`` and ``subGroups``
		   	
		- Input: [organisation name,From date,to
			date,organisation type]
			
		- Output: Returns boolean True and client_id
		
		.. code-block:: python	
		
			def xmlrpc_Deploy(self,queryParams):
				queryParams = blankspace.remove_whitespaces(queryParams)
			
				abtconf = et.parse("/opt/abt/abt.xml")
				abtroot = abtconf.getroot()	
				org = et.SubElement(abtroot,"organisation") #creating an organisation tag
				org_name = et.SubElement(org,"orgname")
			
				# assigning client queryparams values to variables
				name_of_org = queryParams[0] # name of organisation
				db_from_date = queryParams[1]# from date
				db_to_date = queryParams[2] # to date
				organisationType = queryParams[3] # organisation type
				org_name.text = name_of_org #assigning orgnisation name value in orgname tag text of abt.xml
				financial_year_from = et.SubElement(org,"financial_year_from") #creating a new tag for financial year from-to	
				financial_year_from.text = db_from_date
				financial_year_to = et.SubElement(org,"financial_year_to")
				financial_year_to.text = db_to_date
				dbname = et.SubElement(org,"dbname") 
		
				#creating database name for organisation		
				org_db_name = name_of_org[0:1]
				time = datetime.datetime.now()
				str_time = str(time.microsecond)	
				new_microsecond = str_time[0:2]		
				result_dbname = org_db_name + str(time.year) + str(time.month) + str(time.day) + str(time.hour)\
				 		+ str(time.minute) + str(time.second) + new_microsecond
			
				dbname.text = result_dbname #assigning created database name value in dbname tag text of abt.xml
		
				abtconf.write("/opt/abt/abt.xml")
			
				# getting client_id for the given orgnisation and from and to date
				self.client_id = dbconnect.getConnection([name_of_org,db_from_date,db_to_date])
		
				try:
					metadata = dbconnect.Base.metadata
					metadata.create_all(dbconnect.engines[self.client_id])
				except:
					print "cannot create metadata"
			
				Session = scoped_session(sessionmaker(bind=dbconnect.engines[self.client_id]))
			
				dbconnect.engines[self.client_id].execute(\
					"create view view_account as \
					select groups.groupname, account.accountcode, account.accountname, account.subgroupcode\
					from groups, account where groups.groupcode = account.groupcode\
					order by groupname;")
			
				dbconnect.engines[self.client_id].execute(\
					"create view view_voucherbook as \
					select voucher_master.vouchercode,voucher_master.flag,voucher_master.reference,\
					voucher_master.voucherdate,voucher_master.reffdate,voucher_master.vouchertype,account.accountname\
					as account_name,voucher_details.typeflag,voucher_details.amount,\
					voucher_master.narration,voucher_master.projectcode\
					from voucher_master,voucher_details,account as account \
					where voucher_master.vouchercode = voucher_details.vouchercode \
					and voucher_details.accountcode = account.accountcode;")
				dbconnect.engines[self.client_id].execute(\
					"create view view_group_subgroup as \
					select groups.groupcode, groups.groupname,subgroups.subgroupcode,subgroups.subgroupname\
					from groups, subgroups where groups.groupcode = subgroups.groupcode \
					order by groupname;")
				dbconnect.engines[self.client_id].execute(\
					"create view group_subgroup_account as select groups.groupname,\
					subgroups.subgroupname,account.accountcode,account.accountname,account.openingbalance,\
					account.balance\
					from groups join account on (groups.groupcode = account.groupcode)\
					left outer join subgroups\
					on (account.subgroupcode = subgroups.subgroupcode) order by groupname;")
		
				if (organisationType == "Profit Making"):

					Session.add_all([\
						dbconnect.Groups('Capital',''),\
						dbconnect.Groups('Current Asset',''),\
						dbconnect.Groups('Current Liability',''),\
						dbconnect.Groups('Direct Income','Income refers to consumption\
				opportunity gained by an entity within a specified time frame.'),\
						dbconnect.Groups('Direct Expense','This are the expenses to be incurred for\
				operating the buisness.'),\
						dbconnect.Groups('Fixed Assets',''),\
						dbconnect.Groups('Indirect Income','Income refers to consumption opportunity\
				gained by an entity within a specified time frame.'),\
						dbconnect.Groups('Indirect Expense','This are the expenses to be incurred\
				for operating the buisness.'),\
						dbconnect.Groups('Investment',''),\
						dbconnect.Groups('Loans(Asset)',''),\
						dbconnect.Groups('Loans(Liability)',''),\
						dbconnect.Groups('Reserves',''),\
						dbconnect.Groups('Miscellaneous Expenses(Asset)','')\
					])
					Session.commit()
		
				else:
					Session.add_all([\
						dbconnect.Groups('Corpus',''),\
						dbconnect.Groups('Current Asset',''),\
						dbconnect.Groups('Current Liability',''),\
						dbconnect.Groups('Direct Income','Income refers to consumption\
				opportunity gained by an entity within a specified time frame.'),\
						dbconnect.Groups('Direct Expense','This are the \
				expenses to be incurred for operating the buisness.'),\
						dbconnect.Groups('Fixed Assets',''),\
						dbconnect.Groups('Indirect Income','Income refers to consumption \
				opportunity gained by an entity within a specified time frame.'),\
						dbconnect.Groups('Indirect Expense','This are the \
				expenses to be incurred for operating the buisness.'),\
						dbconnect.Groups('Investment',''),\
						dbconnect.Groups('Loans(Asset)',''),\
						dbconnect.Groups('Loans(Liability)',''),\
						dbconnect.Groups('Reserves',''),\
						dbconnect.Groups('Miscellaneous Expenses(Asset)','')\
					])
					Session.commit()
		
				Session.add_all([\
					dbconnect.subGroups('2','Bank'),\
					dbconnect.subGroups('2','Cash'),\
					dbconnect.subGroups('2','Inventory'),\
					dbconnect.subGroups('2','Loans & Advance'),\
					dbconnect.subGroups('2','Sundry Debtors'),\
					dbconnect.subGroups('3','Provisions'),
					dbconnect.subGroups('3','Sundry Creditors for Expense'),\
					dbconnect.subGroups('3','Sundry Creditors for Purchase'),\
					dbconnect.subGroups('6','Building'),\
					dbconnect.subGroups('6','Furniture'),\
					dbconnect.subGroups('6','Land'),\
					dbconnect.subGroups('6','Plant & Machinery'),\
					dbconnect.subGroups('9','Investment in Shares & Debentures'),\
					dbconnect.subGroups('9','Investment in Bank Deposits'),\
					dbconnect.subGroups('11','Secured'),\
					dbconnect.subGroups('11','Unsecured')\
				])
		
				Session.commit()

				Session.add_all([\
					dbconnect.Flags(None,'mandatory'),\
					dbconnect.Flags(None,'automatic')\
				])
				Session.commit()

				Session.close()
				connection.close()
				return True,self.client_id
				
	+ code for creating the ``abt.xml`` file is present in ``dbconnect``.
	+ Now, abt.xml will be look like as below
		  
		- For example:
		 
		.. code-block:: xml
		
		        <abt>
	                        <organisation>
		                        <orgname>Iit</orgname>
		                        <financial_year_from>01-04-2013</financial_year_from>
		                        <financial_year_to>31-03-2014</financial_year_to>
		                        <dbname>I20131151665878</dbname>
	                        </organisation>
	                </abt>
	        
		- to open ``abt.xml`` file cd to ``/opt/abt/`` and run below command on terminal

		  .. code-block:: bash

				  sudo gedit abt.xml
		        
		- you can open it using other editor like vim , emac etc.

