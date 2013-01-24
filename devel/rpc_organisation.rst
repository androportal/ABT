.. toctree::
   :numbered:

rpc_organisation
++++++++++++++++
	+ This rpc file provide the information about the organisation
	+ it performs the task like save,edit,update organisation information
	+ also perform some task related to project and preferences
	+ create class ``organisation`` inherit the ``XMLRPC`` class
	
		- import the database connector
		- import the twisted modules for executing rpc calls and also to implement the server
    		- reactor from the twisted library starts the server with a published object and 
    		  listens on a given port.
		- inherit the class from XMLRPC to make it publishable as an rpc service.
		- import rpc_transaction module to create its instance and functions
		.. code-block:: python
		
			import dbconnect
			from twisted.web import xmlrpc, server
			from modules import blankspace
			from twisted.internet import reactor
			import rpc_transaction
			
	 		class organisation(xmlrpc.XMLRPC):
				def __init__(self):
					xmlrpc.XMLRPC.__init__(self)
		
	+ def xmlrpc_setPreferences(self,queryParams,client_id):
		
		- function for update flags for project manually created account code and voucher
		  reference number i/p parameters: Flag No(datatype:integer) , FlagName
		- if flag no is ``2`` then will update accountcode flag value as either
		  ``manually`` or ``automatic`` (default) 
		- if flag no is ``1`` then will update refeno flag value as either 
		  ``mandatory`` or ``optional`` (datatype:text) 
		- o/p parameter : True
		.. code-block:: python
		
			def xmlrpc_setPreferences(self,queryParams,client_id):
		
				queryParams = blankspace.remove_whitespaces(queryParams)
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				Session.query(dbconnect.Flags).\
					filter(dbconnect.Flags.flagno == queryParams[0]).\
					update({'flagname':queryParams[1]})
				Session.commit()
				Session.close()
				connection.connection.close()
				return True
	
	def xmlrpc_getPreferences(self,queryParams,client_id):
		
		- finding the appropriate preferences for accountcode
		  for given flag no 
		- if flag no is ``2`` then will return
		  accountcode flag value.
		- if flag no is ``1`` then will return refeno flag value
		- Input: [flagno]
		- Output: It returns flagname depnd on flagno

		.. code-block:: python
		
			def xmlrpc_getPreferences(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Flags).\
						filter(dbconnect.Flags.flagno == queryParams[0]).\
						first()
		
				if result == []:
					return result
				else:
					return result.flagname
		
				Session.close()
				connection.connection.close()
		
	+ def xmlrpc_setProjects(self,queryParams,client_id):
		
		- function for set projects for a particular
		  organisation 
		- this will use to create the projects for organisation.
		- Input: [projectname(datatype:text)]
		- Output: Returns boolean True if projectname added
		.. code-block:: python
		
			def xmlrpc_setProjects(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				Session.add_all([dbconnect.Projects(None,queryParams[0])])
				Session.commit()
				return True
		
	+ def xmlrpc_getAllProjects(self,client_id):
		
		- function for get list of all projectnames for a
		  particular organisation 
		- output: if list is blank then return empty list
			  else returns list of list projectcaode(datatype:Integer) 
			  and projectname(datatype:text)
		.. code-block:: python
		
			def xmlrpc_getAllProjects(self,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Projects).order_by(dbconnect.Projects.projectname).all()
				Session.close()
				connection.connection.close()
				if result == []:
					return result
				else:
					projects = []
					for i in range(0,len(result)):
						projects.append([result[i].projectcode, result[i].projectname])
					return projects
		
	+ def xmlrpc_deleteProject(self, queryParams, client_id):
		
		- function for deleting project name
		- Input: [projectname(datatype:String)]
		- Output: returns 1 String , when project is deleted
	
		.. code-block:: python
		
			def xmlrpc_deleteProject(self, queryParams, client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Projects).\
				      	 	filter(dbconnect.Projects.projectname == queryParams[0]).\
				      		delete()
				Session.commit()
				Session.close()
				connection.connection.close()	
				return "1"
		
	
	+ def xmlrpc_hasProjectTransactions(self, queryParams, client_id):
		
		- function to find out whether the given projectname 
	          has any transactions or not
		- it will take projectname as a first parameter and
		  then getprojetcode to delete project
		- Input: queryParams[projectname(datatype:String)]
		- Output: It returns strig "1" when transaction with projectname
		  is present else return "0"
		   
		.. code-block:: python
		
			def xmlrpc_hasProjectTransactions(self, queryParams, client_id):
		
				transaction = rpc_transaction.transaction()
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				projectcode = transaction.xmlrpc_getProjectcodeByProjectName([queryParams[0]],client_id)
				statement = "select count(vouchercode) as vouchercodeCount\
					     		from view_voucherbook\
					     		where projectcode ='"+str(projectcode)+"'"
		
				result = dbconnect.engines[client_id].execute(statement).fetchone()
				Session.close()
				connection.connection.close()
				if result[0] == 0:
					return 0
				if result[0]  > 0:
					return 1
		
		
	+ def xmlrpc_deleteProjectName(self,queryParams,client_id):
		
		- function for deleting project. 
	        - for this we have used ``hasProjectTransactions`` 
		  & ``deleteProject`` rpc functions. 
		- with the help of ``hasProjectTransactions`` we are able to 
		  find out whether the given project has any transactions or not. 
		- ``deleteProject`` delete that particular projectname which has no transaction
		- Input: queryParams[projectname(datatype:String)]
		- Output: if hasTransaction is "0" then it returns string "project deleted"
		  else return "has transaction".
		
		.. code-block:: python
		
			def xmlrpc_deleteProjectName(self,queryParams,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				hasProjectTransactions= self.xmlrpc_hasProjectTransactions([str(queryParams[0])],client_id)
				Session.close()
				connection.connection.close()
				if(str(hasProjectTransactions) == "0"):
				    self.xmlrpc_deleteProject([str(queryParams[0])],client_id)
				    return "project deleted"
		
				elif(str(hasProjectTransactions) == "1"):
				    return "has transaction"
			    
	+ def xmlrpc_editProject(self, queryParams, client_id):
		
		- function for edit projectname
		- it will alter projectname ans update it.
		- input: [projectcode,projectname] 	
		- output: Return string ``updated successfully``
		.. code-block:: python
		
			def xmlrpc_editProject(self, queryParams, client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				transaction = rpc_transaction.transaction()
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Projects).\
					filter(dbconnect.Projects.projectcode == queryParams[0]).\
					update({'projectname': queryParams[1]})
		
				Session.commit()
				Session.close()
				connection.connection.close()
				return "upadted successfully"	
			
	+ def xmlrpc_setOrganisation(self,queryParams,client_id):
		
		- function for add organisation details in database
		- input: if orgtype is ``NGO`` then	
			[orgname,orgtype,orgcountry,orgstate,orgcity,orgaddr,orgpincode,
			orgtelno, orgfax, orgwebsite, orgemail, orgpan, "", "",
			orgregno, orgregdate, orgfcrano, orgfcradate]
			
			else:
			[orgname,orgtype,orgcountry,orgstate,orgcity,orgaddr,orgpincode,
			orgtelno, orgfax, orgwebsite, orgemail, orgpan,orgmvat,orgstax,
			"", "", "", ""]
		- output: Returns boolean True if added successfully else False
		.. code-block:: python

			def xmlrpc_setOrganisation(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				Session.add_all([\
						dbconnect.Organisation(\
							queryParams[0],queryParams[1],queryParams[2],queryParams[3],\
							queryParams[4],queryParams[5],queryParams[6],queryParams[7],\
							queryParams[8],queryParams[9],queryParams[10],queryParams[11],\
							queryParams[12],queryParams[13],queryParams[14],\
							queryParams[15],queryParams[16],queryParams[17])\
						])
		
				Session.commit()
				Session.close()
				connection.connection.close()
				return True 
	
	+ def xmlrpc_getorgTypeByname(self, queryParams, client_id):   
		
		- function for get Organisation Type for provided organisation
		- querys the Organisation table and sees if an orgname
		  similar to one provided as a parameter.
		- if it exists then it will return orgtype related orgname
		- input: [orgname(datatype:string)]
		- output:  orgtype if orgname match else eturn false string
		
		.. code-block:: python
		
			def xmlrpc_getorgTypeByname(self, queryParams, client_id):
			
				print  queryParams[0]
				queryParams = blankspace.remove_whitespaces(queryParams)
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Organisation).\
				   		 filter(dbconnect.Organisation.orgname == queryParams[0]).\
				       		 first()
				Session.close()
				connection.connection.close()
		
				if result == None:
				    return "0"   
				else:
				    return result.orgtype
		    
	+ def xmlrpc_getOrganisation(self,client_id):
	
		- function to get all the details of organisation from database
		- input: client_id
		- output: it will return list of organisation details
		
		.. code-block:: python
		
			def xmlrpc_getOrganisation(self,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Organisation).all()
				Session.close()
				connection.connection.close()
				if result == []:
					return result
				else:
					orgdetail_list = []
					for l in result:
						orgdetail_list.append([\
								l.orgcode,l.orgtype,l.orgname,l.orgaddr,\
								l.orgcity,l.orgpincode,l.orgstate,l.orgcountry,\
								l.orgtelno,l.orgfax,l.orgwebsite,l.orgemail,\
								l.orgpan,l.orgmvat,l.orgstax,l.orgregno,\
								l.orgregdate,l.orgfcrano,l.orgfcradate\
								])
					return orgdetail_list
			
	+ def xmlrpc_updateOrg(self,queryParams,client_id):
		
		- updating the orgdetails after edit organisation
		- input: [orgcode,orgaddress,orgcountry,orgstate,orgcity,orgpincode,orgtelno,orgfax,orgemail,
			orgwebsite,orgmvat,orgstax,orgregno,orgregdate,orgfcrano,orgfcradate,orgpan],client_id
		- output: It will returns String "upadted successfully"
		.. code-block:: python
		
			def xmlrpc_updateOrg(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Organisation).\
					filter(dbconnect.Organisation.orgcode == queryParams[0]).\
						update({'orgaddr': queryParams[1],'orgcountry':queryParams[2],'orgstate':queryParams[3],\
						'orgcity': queryParams[4],'orgpincode':queryParams[5],'orgtelno':queryParams[6],\
						'orgfax':queryParams[7],'orgemail':queryParams[8],'orgwebsite':queryParams[9],\
						'orgmvat':queryParams[10],'orgstax':queryParams[11],'orgregno':queryParams[12],\
						'orgregdate':queryParams[13],'orgfcrano':queryParams[14],'orgfcradate':queryParams[15],\
						'orgpan':queryParams[16]})
		
				Session.commit()
				Session.close()
				connection.connection.close()
				return "upadted successfully"




