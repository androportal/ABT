dbconnect.py
++++++++++++
	
	+ This file is used to create all the database tables using sqlalchemy
	
		- import sqlAlchemy to create tables 
		- import ElemnentTree to parse the xml file.
		- import declarative base class from sqlalchemy for mapping a classes.
		- creating an empty list of engines.
		- engine in this analogy is a connection maintained as a session.
		- so every time a new client connects to the rpc server,
		- a new engine is appended to the list and the index returned as the id.
        
		.. code-block:: python
		
			engines = []
			session = sessionmaker()
			from sqlalchemy import create_engine, func, select, literal_column
			from sqlalchemy import orm
			from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey, select, Text, DECIMAL, Enum
			from sqlalchemy.ext.declarative import declarative_base
			from sqlalchemy.orm import sessionmaker, scoped_session
			from sqlalchemy.types import Numeric, TIMESTAMP, Enum 
			from xml.etree import ElementTree as et
			import os
			import datetime, time
			from time import strftime
			from sqlalchemy import *
			from types import * 
			from sqlite3 import dbapi2 as sqlite	
		
	+ def getOrgList()
	
		- This function create opens the configuration file abt.xml (path : opt/abt)
		- used xml.etree to cerate xml file and parsing it .
		- Write root node `<abt> </abt>`.
		- if any organisation present then and gets the list of all organisations registered on the server.
		
		.. code-block:: python
	
			def getOrgList():

		            if os.path.exists("/opt/abt/abt.xml") == False:
		                print "file not found trying to create one."
		                try:
		                    os.system("touch /opt/abt/abt.xml")
		                    print "file created "
		                    os.system("chmod 722 /opt/abt/abt.xml")
		                    print "permissions granted "
		                except:
		                    print "the software is finding trouble creating file."
		                    return False
		                try:
		                    abtconf = open("/opt/abt/abt.xml", "a")
		                    abtconf.write("<abt>\n")
		                    abtconf.write("</abt>")
		                    abtconf.close()
		                except:
		                    print "we can't write to the file, sorry!"
		                    return False
		            #opening the abt.xml file by parsing it into a tree.	
		            abtconf = et.parse("/opt/abt/abt.xml")
		            #now since the file is opened we will get the root element.  
		            abtroot = abtconf.getroot()
		            #we will now extract the list of children (organisations) into a variable named orgs. 
		            orgs = abtroot.getchildren() 
		            return orgs
                            

	+ def getConnection(queryParams):
		- The getConnection function will actually establish connection and 
		- return the id of the latest engine added to the list.
		- first check if the file exists in the given path.
		- if this is the first time we are running the server 
		  then we need to create the ``abt.xml`` file.
	       
		.. code-block:: python
		
		       def getConnection(queryParams):
	   
			    dbname = "" #the dbname variable will hold the final database name for the given organisation. 
			    orgs = getOrgList() #we will use org as an iterator and go through the list of all the orgs.
			    
			    for org in orgs:
				orgname = org.find("orgname")
				financialyear_from = org.find("financial_year_from")
				financialyear_to = org.find("financial_year_to")
				print orgname.text,queryParams[0],financialyear_from.text,queryParams[1],financialyear_to.text,queryParams[2]
				if orgname.text == queryParams[0] and financialyear_from.text == queryParams[1] and financialyear_to.text == queryParams[2]:
				    #print "we r in if"
				    dbname = org.find("dbname")
				    database = dbname.text
				    
				else:
				    print "orgnisationname and financial year not match"
			    global engines #the engine has to be a global variable so that it is accessed throughout the module.
			    stmt = 'sqlite:////opt/abt/db/' + database
			    engine = create_engine(stmt, echo=False) #now we will create an engine instance to connect to the given database.
			    engines.append(engine)  #add the newly created engine instance to the list of engines.
			    return engines.index(engine) #returning the connection number for this engine.

	+ Mapping classes into tables using Declarative Class:
	
		- Classes mapped using the Declarative system are defined in terms of a base class which maintains a catalog of classes and tables relative to that base - this is known as the declarative base class.
	 
		- Our application will usually have just one instance of this base in a commonly imported module. We create the base class using the declarative_base() function, as follows:

		.. code-block:: python

			Base = declarative_base()
		
			class Account(Base):
			    __tablename__ = "account"
			    accountcode = Column(String(40), primary_key=True)
			    groupcode = Column(Integer, ForeignKey("groups.groupcode"), nullable=True)
			    subgroupcode = Column(Integer, ForeignKey("subgroups.subgroupcode"), nullable=True) 
			    accountname = Column(Text, nullable=False)
			    openingbalance = Column(Numeric(13, 2))
			    openingdate = Column(TIMESTAMP)
			    balance = Column(Numeric(13, 2))
	   

			    def __init__(self, accountcode, groupcode, subgroupcode, 
			    accountname, openingbalance, openingdate, balance):
				self.accountcode = accountcode
				self.groupcode = groupcode
				self.subgroupcode = subgroupcode
				self.accountname = accountname
				self.openingbalance = openingbalance
				self.openingdate = openingdate
				self.balance = balance
		

			account_table = Account.__table__

			orm.compile_mappers()	
		
		- same way go for another tables.	
		- For more info go to `Link <http://docs.sqlalchemy.org/en/rel_0_8/orm/tutorial.html#declare-a-mapping>`_.	
		
