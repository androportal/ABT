.. toctree::
   :numbered:
   
rpc_transactions
++++++++++++++++
	+ This rpc module will proved details about all transactions
	+ It will save , edit , clone , delete and some other operations.
	+ create class ``transactions`` inherit the ``XMLRPC`` class
	
		- import the database connector
		- import the twisted modules for executing rpc calls and also to implement the server
    		- reactor from the twisted library starts the server with a published object and 
    		  listens on a given port.
		- inherit the class from XMLRPC to make it publishable as an rpc service.
		- import rpc_account module to create its instance and functions
		
		.. code-block:: python
		
			import dbconnect
			from twisted.web import xmlrpc, server
			from twisted.internet import reactor
			import rpc_account
			from datetime import datetime, time
			from sqlalchemy import func , and_ , or_
			from modules import blankspace

			class transaction(xmlrpc.XMLRPC):
				def __init__(self):

					xmlrpc.XMLRPC.__init__(self)
		
	+ def xmlrpc_setTransaction(self,queryParams_master,queryParams_details,client_id):
		
		- This function is used to create a new voucher. 
		- adds a new voucher in the database given its reference number 
		  and transaction details (dr and cr), along with narration and the date.
		- the entire transaction is recorded in terms of Dr and Cr and the respected amounts.
		- the function call 3 funtions from same file rpc_transation.py
		  ``xmlrpc_getProjectcodeByProjectName`` ``xmlrpc_setVoucherMaster``
		  ``xmlrpc_setVoucherDetails``
		- and call ``xmlrpc_getAccountCodeByAccountName`` from rpc_account.py 
		  to get accountcode by accountname
		- input: queryParams_master list will contain:
		  reference number,transaction date ,voucher type,project name,narration
		  queryParams_details list will contain:
		  DrCr flag,AccountName,the amount for the respective account.
		- function returns "success".
		
		.. code-block:: python
		
			def xmlrpc_setTransaction(self,queryParams_master,queryParams_details,client_id):
				queryParams_master = blankspace.remove_whitespaces(queryParams_master)
				print "queryParams_master"
				print queryParams_master
				projectcode = self.xmlrpc_getProjectcodeByProjectName([queryParams_master[3]],client_id)
		
				params_master = [queryParams_master[0],queryParams_master[1],queryParams_master[2],projectcode,queryParams_master[4]]
				print "params master"
				print params_master
				vouchercode = self.xmlrpc_setVoucherMaster(params_master,client_id)
		
				print "query for masters is successful and voucher code is " + str(vouchercode)
				for detailRow in queryParams_details:
					queryParams_details = blankspace.remove_whitespaces(detailRow)
					account = rpc_account.account();
					accountcode = account.xmlrpc_getAccountCodeByAccountName([detailRow[1]],client_id);
					params_details = [vouchercode,str(detailRow[0]),str(accountcode),float(detailRow[2])]
					self.xmlrpc_setVoucherDetails(params_details,client_id)
				return 1
	
	+ def xmlrpc_setVoucherMaster(self,queryParams,client_id):
		
		- adds a new voucher in the database given its reference number 
		  and transaction details (dr and cr), along with narration and the date.
		- This function is used to create a new voucher.  
		- The entire transaction is recorded in terms of Dr and Cr and the respected amounts.
		- queryParams list will contain :
		  reference Number,the actual transaction date,voucher type
		  project name , Narration
		- return vouchercode
		
		.. code-block:: python
		
			def xmlrpc_setVoucherMaster(self,queryParams,client_id):
				
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				VoucherCode = Session.query(func.count(dbconnect.VoucherMaster.vouchercode)).scalar()
				if VoucherCode == None:
					VoucherCode = 0
					VoucherCode = VoucherCode + 1
				else:
					VoucherCode = VoucherCode + 1
				
				system_date = datetime.today() # sqlite take datetime or date object for TIMESTAMP
				reffdate =  datetime.strptime(str(queryParams[1]),"%d-%m-%Y")
				# add all values in the account table
				
				Session.add(dbconnect.VoucherMaster(\
					VoucherCode,queryParams[0],system_date,reffdate,queryParams[2],1,queryParams[3],queryParams[4]))
			
				Session.commit()
				Session.close()
				connection.connection.close()
		
				return VoucherCode	
		
	+ def xmlrpc_getProjectcodeByProjectName(self,queryParams,client_id):
		- function to get projectcode acouding to projectname
		- input: It will take only one input projectname
		- output: it will return projectcode if projectname match
			else returns 0
			
		.. code-block:: python
		
			def xmlrpc_getProjectcodeByProjectName(self,queryParams,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Projects.projectcode).\
				      filter(dbconnect.Projects.projectname == queryParams[0]).first()
				Session.close()
				connection.connection.close()
				print result
				if result == None:
					return 0
				else:
					projectCode = result[0]
					return projectCode 
			
	+ def xmlrpc_getProjectNameByProjectCode(self,queryParams,client_id):
		- function to get projectname acouding to projectcode
		- input: It will take only one input projectcode
		- output: it will return projectname if projectcode match
			else returns None String
			
		.. code-block:: python
			
			def xmlrpc_getProjectNameByProjectCode(self,queryParams,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Projects.projectname).\
				      filter(dbconnect.Projects.projectcode == queryParams[0]).first()
				Session.close()
				connection.connection.close()
				if result == None:
					return result
				else:
					projectname = result[0]
					return projectname
			
	+ def xmlrpc_setVoucherDetails(self,queryParams,client_id):
	
		- it set voucher details which will be use in setTransaction
		- input: Dr or Cr flag,AccountName,the amount for the respective account.
		- output: it returns "success " as string 
		
		.. code-block:: python
		
			def xmlrpc_setVoucherDetails(self,queryParams,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				Session.add(dbconnect.VoucherDetails(\
					queryParams[0],queryParams[1],queryParams[2],queryParams[3]))
			
				Session.commit()
				Session.close()
				connection.connection.close()
				return "success"
		
	+ def xmlrpc_getTransactions(self,queryParams,client_id):

		- get voucher details from the database given input parameters
		- it will chech for Project exist or not 
		- if 'No Project' then 
		  	+ it will query to 'view_voucherbook' view in (rpc.main)
			+ gives the details of transactions which is under 'No Project'
		  else 
			+ it will query to 'view_voucherbook' view in (rpc.main)
			+ gives the details of transactions which is under given project name. 
		
		- it will call ``xmlrpc_getProjectcodeByProjectName`` from same file 
		  ``rpc_transation.py`` to get projectcode for given projectname
		- input: [accountname,from_date,to_date,projectname]
		- output: [vouchercode , voucherflag , reff_date , voucher_reference,
			transaction_amount,show_narration]
			
		.. code-block:: python
		
			def xmlrpc_getTransactions(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				from_date = str(datetime.strptime(str(queryParams[1]),"%d-%m-%Y"))
				to_date = str(datetime.strptime(str(queryParams[2]),"%d-%m-%Y"))
				if queryParams[3] == 'No Project':
			
					print "In No Project"
					statement = "select vouchercode,typeflag,reffdate,reference,amount,narration\
					     		from view_voucherbook\
					     		where account_name = '"+queryParams[0]+"'\
					     		and reffdate >= '"+from_date+"'\
							and reffdate <= '"+to_date+"'\
							and flag == 1\
							order by reffdate"
					result = dbconnect.engines[client_id].execute(statement).fetchall()
				else:
					project_code = self.xmlrpc_getProjectcodeByProjectName([str(queryParams[3])],client_id)
					print "main params"
					print str(project_code)
					print queryParams[0]
					print queryParams[1]
					print queryParams[2]
					statement = "select vouchercode, typeflag ,reffdate,reference,amount,narration\
							from view_voucherbook\
							where account_name = '"+queryParams[0]+"'\
							and projectcode = '"+str(project_code)+"'\
							and reffdate >= '"+from_date +"'\
							and reffdate <= '"+to_date+"'\
							and flag == 1\
							order by reffdate"
					result = dbconnect.engines[client_id].execute(statement).fetchall()
			
				transactionlist = []
				for row in result:
	
					transactionlist.append([row[0],row[1],row[2],row[3],'%.2f'%(row[4]),row[5]])
		
		
				return transactionlist
		
	+ def xmlrpc_getParticulars(self,queryParams,client_id):
		
		- to get list of Particulars from the database given input parameters
		- it will retrive acount name list from view_voucherbook
		  accounts which is involved in transactions 
		- if it is involve then 
			+ it will query to 'view_voucherbook' view in (rpc.main)
			+ and gives the list of account names
		  else 
			+ it will query to 'view_voucherbook' view in (rpc.main)
			+ and gives the empty list
			
		input: [voucher_code,type_flag]
		output: [accountnames]
		
		.. code-block:: python
		
			def xmlrpc_getParticulars(self,queryParams,client_id):
				statement = "select account_name\
				     		from view_voucherbook\
				     		where vouchercode = '"+str(queryParams[0])+"'\
				     		and typeflag ='"+queryParams[1]+"' \
				     		and flag = 1\
						order by account_name"
				result = dbconnect.engines[client_id].execute(statement).fetchall()
				accountnames = []
				for row in result:
					accountnames.append(row.account_name)
			
				return accountnames
		
	+ def xmlrpc_searchVoucher(self,queryParams,client_id):
		
		- The function is used to get the list of vouchers on the basis 
		  of either reference number (which can be duplicate),or date range,
		  or some words from narration.
		- This means one or more vouchers could be by the same reference 
		  number or within a given date range.
		- The list thus returned contains all details of a given voucher 
		  except its exact transactions, i.e the records from voucher_master.
		- The function calls 3 definations fron the same class file 
		  ``xmlrpc_searchVouchers``  ``xmlrpc_getVoucherAmount``
		  ``xmlrpc_getVoucherDetails``
		- returns one or more vouchers given the reference number 
		  or date range (which ever specified)takes one parameter queryParams 
		  as list.
		- input: [searchFlag , refeence_no , from_date , to_date , narration ]
  		- searchFlag integer (1 implies serch by reference,2 as search by date range and 
  		  3 as search by narration.
		- returns a 2 dimensional list containing one or more records from voucher_master
		- output:[vouchercode , refeence_no , reffdate,vouchertype,dramount ,cramount ,
  		  totalamount , narration ]
  		
		.. code-block:: python
		
			def xmlrpc_searchVoucher(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				vouchers = self.xmlrpc_searchVouchers(queryParams,client_id)
				voucherView = []
				for voucherRow in vouchers:
			
					amtRow = self.xmlrpc_getVoucherAmount([voucherRow[0]],client_id)
					voucherAccounts = self.xmlrpc_getVoucherDetails([voucherRow[0]],client_id)
					drAccount = ""
					crAccount = ""
					drCounter = 1
					crCounter = 1
					for va in voucherAccounts:
						if va[1] == "Dr" and drCounter == 2:
							drAccount = va[0] + "+"
						if va[1] == "Dr" and drCounter == 1:
							drAccount = va[0]
							drCounter = drCounter +1
						if va[1] == "Cr" and crCounter == 2:
							crAccount = va[0] + "+"
						if va[1] == "Cr" and crCounter == 1:
							crAccount = va[0]
							crCounter = crCounter +1
				
					totalAmount = '%.2f'%(amtRow)
			
					voucherView.append([voucherRow[0],voucherRow[1],voucherRow[2],voucherRow[3],\
					drAccount,crAccount,totalAmount,voucherRow[4]])
		
				return voucherView	
		
	+ def xmlrpc_searchVouchers(self,queryParams,client_id):
		
		- This function will be usefull in the searchVouchers 
		  to get Complete details or information about transaction
		- input: [searchFlag,ref_no,from_date, to_date ,narration]
  		- output: [vouchercode , refeence_no , reffdate,vouchertype,narration ]
  		
  		.. code-block:: python
  		
  			def xmlrpc_searchVouchers(self,queryParams,client_id):
		 		connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				from_date = datetime.strptime(str(queryParams[2]),"%d-%m-%Y")
				to_date = datetime.strptime(str(queryParams[3]),"%d-%m-%Y")
				if queryParams[0] == 1: 
					result = Session.query(dbconnect.VoucherMaster).\
								filter(and_(dbconnect.VoucherMaster.reference == queryParams[1],\
								dbconnect.VoucherMaster.flag == 1)).\
					      	 		order_by(dbconnect.VoucherMaster.reffdate).all()
					print "search voucher by reference no"
			
				if queryParams[0] == 2:	
			
					result = Session.query(dbconnect.VoucherMaster).\
								filter(and_(dbconnect.VoucherMaster.reffdate >= from_date,\
								dbconnect.VoucherMaster.reffdate <= to_date,\
								dbconnect.VoucherMaster.flag == 1)).\
					      	 		order_by(dbconnect.VoucherMaster.reffdate).all()
					print "search by date "
					 
				if queryParams[0] == 3:	
					result = Session.query(dbconnect.VoucherMaster).\
					filter(and_(dbconnect.VoucherMaster.flag == 1,\
					(or_(dbconnect.VoucherMaster.narration.like(str(queryParams[4])+'%'),\
					dbconnect.VoucherMaster.narration.like('%'+str(queryParams[4])+'%'),\
					dbconnect.VoucherMaster.narration.like('%'+str(queryParams[4])))))).\
					      	 		order_by(dbconnect.VoucherMaster.reffdate).all()
					      		
		
				if result == []:
					return result
				else:
					voucherlists = []
		
					for row in result:
						reffdate = str(row.reffdate).split(" ")
						ref_date = datetime.strptime(reffdate[0],"%Y-%m-%d").strftime("%d-%m-%Y")
						voucherlists.append([row.vouchercode,row.reference,ref_date,row.vouchertype,row.narration])
					
					return voucherlists 
			
	+ def xmlrpc_getVoucherAmount(self,queryParams,client_id):
		
		- to get amount of particular transaction
		- input: [voucher_code]
		- output: [totalamount]
		
		.. code-block:: python
		
			def xmlrpc_getVoucherAmount(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				statement = "select sum(amount) as totalamount\
					     		from view_voucherbook\
					     		where vouchercode = '"+str(queryParams[0])+"'\
					     		and typeflag ='Cr'"
					     		
				result = dbconnect.engines[client_id].execute(statement).fetchone()
		
				if result[0] == None:
					return []
				else:
					return result[0]

	+ def xmlrpc_getVoucherDetails(self,queryParams,client_id):
	
		- gets the transaction related details given a vouchercode.
		- input: [voucher_code]
		- returns  2 dimentional list containing rows with 3 columns.
		- output: [accountname,typeflag,amount]
		
		.. code-block:: python
		
			def xmlrpc_getVoucherDetails(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				statement = "select account_name,typeflag,amount\
					     		from view_voucherbook\
					     		where vouchercode = '"+str(queryParams[0])+"'\
					     		and flag = 1 "
					     		
				result = dbconnect.engines[client_id].execute(statement).fetchall()
		
				voucherdetails = []
				if result == None:
					return []
				else:
					for row in result:
						voucherdetails.append([row[0],row[1],'%.2f'%float(row[2])])
					
				return voucherdetails
		
		
		
	+ def xmlrpc_getVoucherMaster(self,queryParams,client_id):
		
		- This function is used along with ``getVoucherDetails`` 
		  to searchVoucher (get complete voucher) useful while editing or cloning.
		- The function takes one parameter which is a list containing vouchercode.
		- returns a record from the voucher master 
		  containing single row data for a given transaction.
		- This function call defination ``xmlrpc_getProjectNameByProjectCode``
		  which is in the same file ``rpc_transaction`` to get project name 
		- Returns list containing data from voucher_master.
		- input: [voucher_code]
		- output:[reference,reffdate,vouchertype,projectname,narrartion]
		
		.. code-block:: python
		
			def xmlrpc_getVoucherMaster(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
	
				result = Session.query(dbconnect.VoucherMaster).\
							filter(and_(dbconnect.VoucherMaster.vouchercode == str(queryParams[0]),\
							dbconnect.VoucherMaster.flag == 1)).\
				      	 		order_by(dbconnect.VoucherMaster.reffdate).first()
		
				if result == None:
					return []
				else :
					reffdate = str(result.reffdate).split(" ")
					ref_date = datetime.strptime(reffdate[0],"%Y-%m-%d").strftime("%d-%m-%Y")
					voucherRow = [result.reference,ref_date,result.vouchertype,result.narration,result.projectcode]
					projectName = self.xmlrpc_getProjectNameByProjectCode([int(voucherRow[4])],client_id)	
					if projectName == None:
						projectName = "No Project"
					voucherMaster = [voucherRow[0],voucherRow[1],voucherRow[2],voucherRow[3],projectName]
			
					return voucherMaster	
				
	+ def xmlrpc_deleteVoucher(self,queryParams,client_id):
		
		- This function will not completely delete voucherdetails
		  but it will set the flag 0 instead 1
		- so it will be like disabled for search voucher
		- input: [vouchercode]
		
		.. code-block:: python
		
			def xmlrpc_deleteVoucher(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				try:
					connection = dbconnect.engines[client_id].connect()
					Session = dbconnect.session(bind=connection)
					Session.query(dbconnect.VoucherMaster).\
						filter(dbconnect.VoucherMaster.vouchercode == queryParams[0]).\
						update({'flag':0})
					Session.commit()
					Session.close()
					connection.connection.close()
					return True
				except:
					return False			
				
				
	+ def xmlrpc_editVoucher(self,queryParams_master,queryParams_details,client_id):
		
		- this function is used to create a edit voucher.  
		- adds a new voucher in the database given its reference number 
		  and transaction details (dr and cr), along with narration and the date.
		- The entire transaction is recorded in terms of Dr and Cr and the respected amounts.
		- The function call four funtions from same file rpc_transation.py
		  ``xmlrpc_getProjectcodeByProjectName`` ``xmlrpc_editVoucherMaster``
		  ``xmlrpc_deleteVoucherDetails`` ``xmlrpc_editVoucherDetails``.
		- ``queryParams_master`` list will contain :
                  [vouchercode,reffdate,project name,Narration]
		- ``queryParams_details`` list will contain :
		  [AccountName,dr amount,cr amount]
		- returns "success".
		
		.. code-block:: python
		
			def xmlrpc_editVoucher(self,queryParams_master,queryParams_details,client_id):
				projectCode = self.xmlrpc_getProjectcodeByProjectName([queryParams_master[2]],client_id)
		
				if projectCode == None:
					projectCode = 0 
				del queryParams_master[2]
				queryParams_master.insert(2,projectCode)
				editParams=[queryParams_master[0],queryParams_master[1],queryParams_master[2],queryParams_master[3]]
				successRow = self.xmlrpc_editVoucherMaster(editParams,client_id)
				if successRow == "success":
					delete = self.xmlrpc_deleteVoucherDetails([queryParams_master[0]],client_id)
			
					for detailRow in queryParams_details:
						sp_details = []
						sp_details.append(queryParams_master[0])
						sp_details.append(detailRow[0])
						if float(detailRow[2]) == 0:
							sp_details.append("Dr")
							sp_details.append(float(detailRow[1]))
						if float(detailRow[1]) == 0:
							sp_details.append("Cr")
							sp_details.append((detailRow[2]))
				
						result = self.xmlrpc_editVoucherDetails(sp_details,client_id)
				return result			
				
				
	+ def xmlrpc_editVoucherMaster(self,queryParams,client_id):	
		- it update vouchermaster table depend on given vouchercode
		- input: [vouchercode,reffdate,projectcode,narration]
		- output: String "success"
		
		.. code-block:: python
		
			def xmlrpc_editVoucherMaster(self,queryParams,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				reff_date = datetime.strptime(str(queryParams[1]),"%d-%m-%Y")
				result = Session.query(dbconnect.VoucherMaster ).\
						filter(dbconnect.VoucherMaster.vouchercode == queryParams[0]).\
						update({'reffdate': reff_date,'projectcode': queryParams[2],'narration':  queryParams[3]})
				Session.commit()
				Session.close()
				connection.connection.close()
				return "success"
			
	+ def xmlrpc_editVoucherDetails(self,queryParams,client_id):
		- this add the accountcode,typeflag and amount 
		- input: [vouchercode,accountname,amount,narration]
		- output: String "success"
		
		.. code-block:: python
		
			def xmlrpc_editVoucherDetails(self,queryParams,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Account.accountcode).\
						filter(dbconnect.Account.accountname == queryParams[1]).\
						first()
				account_code = result.accountcode
				Session.add(dbconnect.VoucherDetails(\
						vouchercode = queryParams[0],\
						accountcode = account_code,\
						typeflag = queryParams[2],\
						amount = queryParams[3]\
				))
				Session.commit()
				Session.close()
				connection.connection.close()
				return "success"
			
	+ def xmlrpc_deleteVoucherDetails(self,queryParams,client_id):
		- it will delete voucher depend on given voucher code
		- input: [vouchercode]
		- output: String "deleted"
		
		.. code-block:: python
		
			def xmlrpc_deleteVoucherDetails(self,queryParams,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				Session.query(dbconnect.VoucherDetails).\
				filter(dbconnect.VoucherDetails.vouchercode==queryParams[0]).\
				delete()
				Session.commit()
				Session.close()
				connection.connection.close()
				return "deleted"
		
	+ def xmlrpc_getOnlyClearedTransactions(self,queryParams,client_id):
		 
		- This function will check for cleared transactions
		- input : [accountname,vouchercode,financialstart,todate]
		- output: if transaction is exist in bankrecon table 
		          it will return boolean True else False
		          
		.. code-block:: python
		
			def xmlrpc_getOnlyClearedTransactions(self,queryParams,client_id):
				from_date = str(datetime.strptime(str(queryParams[2]),"%d-%m-%Y"))
				to_date = str(datetime.strptime(str(queryParams[3]),"%d-%m-%Y"))
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.BankRecon).filter(and_(dbconnect.BankRecon.accountname==queryParams[0],\
						dbconnect.BankRecon.vouchercode==queryParams[1],\
						dbconnect.BankRecon.clearancedate >= from_date,\
						dbconnect.BankRecon.clearancedate <= to_date)).\
						first()
				Session.close()
				connection.connection.close()
				if result != None:
					return True
				else:
					return False
				
	+ def xmlrpc_getLastReference(self,queryParams,client_id):
		
		- To get last reference number for respective vouchertype
		- input: [vouchertype]
		- output: [reffno]
		
		.. code-block:: python
		
			def xmlrpc_getLastReference(self,queryParams,client_id):
				statement = "select count(vouchercode)\
						from view_voucherbook\
						where vouchertype = '"+queryParams[0]+"'"
				maxcode = dbconnect.engines[client_id].execute(statement).fetchone()
		
				if maxcode[0] > 0:
					statement = "select reference\
							from view_voucherbook\
							where vouchercode = (\
										select max(vouchercode)from view_voucherbook \
										where vouchertype = '"+queryParams[0]+"'\
										and flag = 1)"
					reffno = dbconnect.engines[client_id].execute(statement).fetchone()
					reffno = reffno.reference
				else :
					reffno = str(maxcode[0]+1)
					
				return reffno
		
	+ def xmlrpc_getLastReffDate(self,queryParams,client_id):
		
		- To get last reference date for respective vouchertype
		- input:[financial_start,voucher_type]
		- output parameters:[reference_date]
	
		.. code-block:: python
		
			def xmlrpc_getLastReffDate(self,queryParams,client_id):

				statement = "select count(vouchercode)\
						from view_voucherbook\
						where vouchertype = '"+queryParams[1]+"'"
				maxcode = dbconnect.engines[client_id].execute(statement).fetchone()
				reff_date = datetime.strptime(str(queryParams[0]),"%d-%m-%Y")
				if maxcode[0] > 0:
					statement = "select reffdate\
							from view_voucherbook\
							where vouchercode = (\
										select max(vouchercode)from view_voucherbook \
										where vouchertype = '"+queryParams[1]+"'\
										and flag = 1)"
					reff_date= dbconnect.engines[client_id].execute(statement).fetchone()
					reff_date = str(reff_date.reffdate).split(" ")
					reff_date = datetime.strptime(reff_date[0],"%Y-%m-%d").strftime("%d-%m-%Y")
			
				else :
					reff_date = str(queryParams[0])
			
				return reff_date
		
	
