rpc_account.py
++++++++++++++
	+ This rpc module do all the functionality related to account like
	  create , edit delete and information about balances .
	+ as the above reate the class Account inherits XMLRPC class.
	
	+ def xmlrpc_setAccount(self, queryParams, client_id):
		- it call ``getGroupCodeByGroupName()`` from ``rpc_groups`` 
		  pass param groupname will return groupcode
		- Adds an account in the account table, under a selected group and optionally a subgroup.
		- depending on the preference choosen by the user.
		- This function insert a row in the account table.
		- connection made with engine of sessions using client_id.
		- add query will be execute.
		- Input: queryParams[groupname,subgroupname,newsubgroupname,accountname,
		         accountcodetype,openingbalance,currentBalance,suggestedcode]
		- Output: Returns String "success"
		
		.. code-block:: python
		
			def xmlrpc_setAccount(self, queryParams, client_id):
		
				group = rpc_groups.groups()
		
				queryParams = blankspace.remove_whitespaces(queryParams)
				sp_params = [queryParams[0], queryParams[3]] # create sp_params list contain  groupname , accountname 
				if queryParams[2] == "": # check for the new-subgroupname if blank then 

					if queryParams[1] == "No Sub-Group": # check for the subgroupname if "No Sub-Group" 
				
						sp_params.append("null")  # append null to sp_params list as 3rd parameter else 
					else:
						sp_params.append(queryParams[1]) # else append subgroupname sp_params list as 3rd parameter
				
				if queryParams[1] == "Create New Sub-Group" : # subgroupname is "Create New Sub-Group" then
			
					sp_params.append(queryParams[2]) # append new-subgroupname to sp_params list as 4rd parameter
				if queryParams[0] == "Direct Income" or queryParams[0] == "Direct Expense"\
				   or queryParams[0] == "Indirect Income" or queryParams[0] == "Indirect Expense": # check for groupname
				   
					sp_params.append(0) # if above groupname then append 0 as opening balance 
				else:
					sp_params.append(queryParams[5]) 
			
				now = datetime.today() # sqlite take datetime or date object for TIMESTAMP
				#date = now.strftime("%Y-%m-%d %H:%M:%S")
				sp_params.append(now) # append the current date of system while setting account
				sp_params.append(sp_params[3]) # append accountname
		
				if queryParams[7] == "": # chech for suggested account code
		
					sp_params.append("null") # if blank then append "null"
				else:
					sp_params.append(queryParams[7]) # else append suggestedcode
		
				# execute here
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				# call getGroupCodeByGroupName() pass param groupname will return groupcode
				group_code = group.xmlrpc_getGroupCodeByGroupName([sp_params[0]], client_id); 
				# check for accountcode if null
		
		 		if sp_params[6] == 'null': # then
		 			
		 			result = Session.query(dbconnect.Account.accountcode).\
				      	 		order_by(dbconnect.Account.accountcode).\
				      			all()
				      	accountcode = []
					if result == []:
						maxcode = []
					else:
						for row in result:
							accountcode.append(int(row.accountcode))
							maxcode = accountcode
		 			
		 			if maxcode == []:
						maxcode = 0
						sp_params[6] = int(maxcode) + 1;
					else:
						maxcode = max(maxcode)
						sp_params[6] = int(maxcode) + 1;
				
				# check for new-subgropname if null	
				if sp_params[2] == 'null': # then 
					# add all values in the account table
					Session.add(dbconnect.Account(\
								sp_params[6],group_code[0],"",sp_params[1],sp_params[3],sp_params[4],sp_params[5]))
					Session.commit()
				else:
					# if new-subgroupname is present then call getSubGroupCodeBySubGroupName pass params new-subgroupname
					# it will return subgroupcode or False
			
					subgroup_code =  group.xmlrpc_getSubGroupCodeBySubGroupName([sp_params[2]], client_id)
					# check for subgroupcode if False 
			
					if subgroup_code == [] : # then 
						# call setSubGroup pass params groupname , new-subgroupname , client-id
		   				group.xmlrpc_setSubGroup([sp_params[0],sp_params[2]],client_id); 
		   				# call getSubGroupCodeBySubGroupName pass params new-subgroupname return subgroupcode
		   				subgroup_code =  group.xmlrpc_getSubGroupCodeBySubGroupName([sp_params[2]], client_id); 
		   			# add all the values in the account table 
		   			Session.add(dbconnect.Account(\
		   						sp_params[6],group_code[0],subgroup_code[0],sp_params[1],\
		   						sp_params[3],sp_params[4],sp_params[5]))
		   				
		   			Session.commit()
					Session.close()
					connection.connection.close()
					
				return "success"			
				
		- to get more information on sqlAlchemy and Querying please see `Link <http://docs.sqlalchemy.org/en/rel_0_8/orm/tutorial.html>`_.		
				
	+ This xmlrpc function will use ti edit accountname and opening balances 
	  and also update the current opening balance.
	+ def xmlrpc_editAccount(self, queryParams, client_id):
	
		- Modifies an account based on account code.  
		- alters account name and opening balance.
		- This function will edit an account and change 
		  either account name, oepning balance or both.
		- the account is fetched internally by the software 
		  on the basis of account code, even if it was 
		  searched by client using account name. 
		- If the function is successful,it will return the newly 
		  updated current balance.If the groupname sent in the queryParams
		  is direct or indirect income, or direct or indirect expence, 
		  then the oepning balance is sent as 0.
		- Input: [accountname, accountcode, groupname and new_opening_balance]
		- Output: [Current_balance]
		
		.. code-block:: python	
		
			def xmlrpc_editAccount(self, queryParams, client_id):
		
				queryParams = blankspace.remove_whitespaces(queryParams)
				spQueryParams = [queryParams[0], queryParams[1]]
				if queryParams[2] == "Direct Income" or \
					queryParams[2] == "Indirect Income" \
					or queryParams[2] == "Direct Expense" \
					or queryParams[2] == "Indirect Expense":
			
					print "sending openingbalance as 0"
					spQueryParams.append(0)
				else: 
					spQueryParams.append(float(queryParams[3]))
			
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Account).\
					filter(dbconnect.Account.accountcode == spQueryParams[1]).first()
				resultParams = [float(result.openingbalance),float(result.balance)]
				if resultParams[0] == spQueryParams[2]:
		
					result = Session.query(dbconnect.Account).\
						filter(dbconnect.Account.accountcode == spQueryParams[1]).\
						update({'accountname': spQueryParams[0]})
				else:
					final_balance = (spQueryParams[2] - resultParams[0]) + resultParams[1]; 
					result = Session.query(dbconnect.Account).\
						filter(dbconnect.Account.accountcode == spQueryParams[1]).\
						update({'accountname': spQueryParams[0],'openingbalance': spQueryParams[2],'balance': final_balance})
			
				Session.commit()
				Session.close()
				connection.connection.close()
		
				return final_balance

								
	+ def xmlrpc_getCrOpeningBalance(self, client_id):
	
		- This function calculates the total credit opening balance 
		  for all accounts with ``Cr`` opening balance.  
		- groups who has ``Cr`` opening balances are ``Corpus`` ``Capital``
		   ``Current Liability`` ``Loans(Liability)`` ``Reserves``.
		- when adding an account we tend to know what is the total of 
		  all debit and credit opening balances.
		- This function calculates the total for all accounts with Cr as 
		  opening balance.
		- function executes ``statement`` for the expected result as float.
		- refer rpc_main.py for the said ``group_subgroup_account`` view.
		- it takes no arguement and returns a float value.
		- Output: Total amount credit balances
				
		.. code-block:: python
			
			def xmlrpc_getCrOpeningBalance(self, client_id):
		
				statement = "select sum(openingbalance) as totalcrbal \
					from group_subgroup_account \
					where groupname \
					in ('Corpus','Capital','Current Liability','Loans(Liability)','Reserves')"
		
				res = dbconnect.engines[client_id].execute(statement).fetchone()
		
				if res.totalcrbal == None:
					return '%.2f'%(0.00)
				else:
					return '%.2f'%(res.totalcrbal)			
				
		
	+ def xmlrpc_getDrOpeningBalance(self, client_id):
		
		- This function calculates the total debit opening balance 
		  for all accounts with Dr opening balance. 
		- groups who has Dr opening balances are ``Current Asset`` 
		  ``Fixed Assets`` ``Investment`` ``Loans(Asset)`` 
		  ``Miscellaneous Expenses(Asset)``.
		- Functions takes no arguement and returns a float value.
	        - function executes the `stmt` for the expected result as float.
		- refer rpc_main.py for the said group_subgroup_account view
		- Output: Total amount credit balances
		
		.. code-block:: python
				
			def xmlrpc_getDrOpeningBalance(self, client_id):
				stmt = "select sum(openingbalance) as totaldrbal\
					from group_subgroup_account\
					where groupname \
					in ('Current Asset','Fixed Assets','Investment','Loans(Asset)','Miscellaneous Expenses(Asset)')"
				res=dbconnect.engines[client_id].execute(stmt).fetchone()
		
				if res.totaldrbal == None:
					return '%.2f'%(0.00)
				else:
					return '%.2f'%(res.totaldrbal)
						
	+ def xmlrpc_getSuggestedCode(self,queryParams,client_id):
	
		- To get code on the basis of provided 3 characters at list queryParams[0] 
		- function takes the 2 characters of selected group and first character of account.
		- The 2 characters of the selected group are determined in the front end.
		- The first character of the entered account is then appended to the former.
		
			+ For example,
				- an account SBI in group Current Asset will send CAS 
				  as the 3 characters as queryParams[0]
				- The function then executes a stored procedure getSuggestedCode 
				  and checks if an account exists with a code starting with 
				  the given 3 characters.
				- if an account did exist then the given 3 characters will be postfixed 
				  with total count of existing similar account codes + 100.
				- If no such account is found then 100 will be concatinated to the first 3 chars.
				  for example if no account exists with an account code starting with CAS, 
			          then the suggested code will be CAS100.
			        - Next time an account with 3 chars as CAS is entered, then it will be CAS101.
				
		- Input: first two charector of groupname and first charecto of the accountname
			returns a string containing the suggested code.
			
		.. code-block:: python
		
			def xmlrpc_getSuggestedCode(self,queryParams,client_id):
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				queryParams = blankspace.remove_whitespaces(queryParams)
				SuggestedAccountCode = Session.query(func.count(dbconnect.Account.accountcode)).\
				filter(dbconnect.Account.accountcode.like(str(queryParams[0])+'%')).scalar()
		
				if SuggestedAccountCode == 0:
					return str(queryParams[0] + "100")
				else:
					SuggestedAccount = SuggestedAccountCode + 100 
					return str(queryParams[0]) + str(SuggestedAccount)
			
				Session.commit()
				Session.close()
				connection.connection.close()
				
	+ def xmlrpc_getAccountCodeByAccountName(self, queryParams, client_id):
	
		- Function for get an accountcode for given accountname.
		- queryParams which is a list containing one element, 
		  accountname as string.
		- Output: returns accountcode if it exist for given accountname
			else returns empty list
	
		.. code-block:: python
		
			def xmlrpc_getAccountCodeByAccountName(self, queryParams, client_id):	
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Account.accountcode).\
				      	 	filter(dbconnect.Account.accountname == queryParams[0]).\
				      		first()
				Session.commit()
				Session.close()
				connection.connection.close()
		
				if result == None:
					return []
				else:
					return result[0]
	
	+ def xmlrpc_getAllAccountNames(self, client_id):
	
		- Function to get the list of all accountnames 
		  in the database.
		- Output: returns the list of all acountnames.
			else returns empty list 
		
		.. code-block:: python	
		
			def xmlrpc_getAllAccountNames(self, client_id):
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Account.accountname).\
				      	 		order_by(dbconnect.Account.accountname).\
				      		all()
				Session.commit()
				Session.close()
				connection.connection.close()
			
				accountnames = []
				if result == None:
					return []
				else: 
					for row in result:
						accountnames.append(row.accountname)
		
				return accountnames
	
	+ def xmlrpc_getAllAccountCodes(self,client_id):
	
		- It will return list of all accountcodes present in the account table.
		- Output: returns the list of all accountcode else it returns empty list
		.. code-block:: python
		
			def xmlrpc_getAllAccountCodes(self,client_id):
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Account.accountcode).\
				      	 		order_by(dbconnect.Account.accountcode).\
				      		all()
				Session.commit()
				Session.close()
				connection.connection.close()
				accountcodes = []
				if result == None:
					return []
				else:
					for row in result:
						accountcodes.append(row.accountcode)
		
					return accountcodes
					
	+ def xmlrpc_getAllBankAccounts(self,client_id):
	
		- To get all accountnames which is under ``Bank`` subgroup.
		- it will query the ``group_subgroup_account`` view from ``rpc_main``.
		- Returns list of accountnames
		
		.. code-block:: python
		
			def xmlrpc_getAllBankAccounts(self,client_id):
		
				statement = "select accountname\
					     		from group_subgroup_account\
					     		where subgroupname ='Bank'\
							order by accountname"  
				getallbankaccounts = dbconnect.engines[client_id].execute(statement).fetchall()
		
				if getallbankaccounts == []:
					return []
				else:
					bankaccount = []
					for row in getallbankaccounts:
						bankaccount.append(row[0])
					return bankaccount
					 
	+ def xmlrpc_getCashFlowOpening(self,client_id):				
	
		- it query the ``group_subgroup_account`` view from ``rpc_main``
		- to get all accountnames which is in under ``Bank`` and ``Cash`` subgroup
		- Returns list of [accountname,openingbalance]
		
		.. code-block:: python
			
			def xmlrpc_getCashFlowOpening(self,client_id):
		
				statement = "select accountname,openingbalance\
						from group_subgroup_account\
						where subgroupname \
						in ('Bank','Cash') order by accountname"
				
				result = dbconnect.engines[client_id].execute(statement).fetchall()
				cashflow = []
				for row in result:
					cashflow.append([row[0],row[1]])
		
				return cashflow 
	
	+ def xmlrpc_getCashFlowReceivedAccounts(self,queryParams,client_id):
	
		- to get sum of amount for those transaction in which only ``Cash`` 
		  and ``Bank`` accountname(with Dr) are involve in startdate and todate
		- input: cfaccountname,cbaccountname,startdate,enddate
		- output parameters: cfamount
		
		.. code-block:: python
			
			def xmlrpc_getCashFlowReceivedAccounts(self,queryParams,client_id):
		
				financial_fromdate = str(datetime.strptime(str(queryParams[2]),"%d-%m-%Y"))
				financial_enddate =  str(datetime.strptime(str(queryParams[3]),"%d-%m-%Y"))
				statement = "select sum(amount)\
						from view_voucherbook\
						where account_name = '"+queryParams[0]+"' \
						and vouchercode \
						in(select vouchercode from view_voucherbook \
						where typeflag = 'Dr' \
						and account_name = '"+queryParams[1]+"'\
						and reffdate >= '"+financial_fromdate+"'\
						and reffdate <= '"+financial_enddate+"'\
						and flag = 1)\
						group by account_name"
				result = dbconnect.engines[client_id].execute(statement).fetchone()
				CashFlowReceived = []
				if result == None:
					return result
				else:
					for row in result:
						CashFlowReceived.append(row)
					return CashFlowReceived 			
	
	+ def xmlrpc_getCashFlowPaidAccounts(self,queryParams,client_id):
	
		- to get sum of amount for those transaction in which only cash and bank
		  accountname(with Cr) are involve in startdate and todate
		- input: cfaccountname,cbaccountname,startdate,enddate
		- cfaccountname will be except ``cash`` and ``bank`` accounts.
		- cbaccountname will be ``cash`` and ``bank`` accounts. 
		- output: cfamount
		
		.. code-block:: python
		
			def xmlrpc_getCashFlowPaidAccounts(self,queryParams,client_id):
		
				financial_fromdate = str(datetime.strptime(str(queryParams[2]),"%d-%m-%Y"))
				financial_enddate =  str(datetime.strptime(str(queryParams[3]),"%d-%m-%Y"))
				statement = "select sum(amount)\
						from view_voucherbook\
						where account_name ='"+queryParams[0]+"'\
						and vouchercode \
						in(select vouchercode from view_voucherbook \
						where typeflag = 'Cr' \
						and account_name = '"+queryParams[1]+"'\
						and reffdate >= '"+financial_fromdate+"' \
						and reffdate <= '"+financial_enddate+"'\
						and flag = 1)\
						group by account_name"
				result = dbconnect.engines[client_id].execute(statement).fetchone()
				print "cash flow paid"
				print result
				getCashFlowPaid = []
				if result == None:
					return result
				else:
					for row in result:
						getCashFlowPaid.append(row)
			
					return getCashFlowPaid 
		
	+ def xmlrpc_accountExists(self, queryParams, client_id):
	
		- function for finding if an account already exists 
		  with the supplied name. 	
		- queryParams which is a list containing one element, 
		  accountname as string.
		- querys the account table and sees if an account 
		  name similar to one provided as a parameter exists.
		- We can ensure that no duplicate account is ever entered because 
		  if a similar account exists. 
		- like the one in queryparams[0] then we won't allow another 
		  entry with same name.
		- output: 1 if account name exists and 0 if not.
	
		.. code-block:: python
			
			def xmlrpc_accountExists(self, queryParams, client_id):
		
				queryParams = blankspace.remove_whitespaces(queryParams)
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(func.count(dbconnect.Account.accountname)).\
				      filter((func.lower(dbconnect.Account.accountname)) == queryParams[0].lower()).\
				      scalar()
				      
				Session.commit()
				Session.close()
				connection.connection.close()
		
				if result == 0:
					return "0"
				else:
					return "1"
					
	+ def xmlrpc_accountCodeExists(self, queryParams, client_id):
	
		- Function for finding if an accountcode already 
		  exists with the supplied code.
		- Input: accountode(datatype:string)
		- Output: return "1" if accountcode exists and "0" if not.
		
		.. code-block:: python
				
			def xmlrpc_accountCodeExists(self, queryParams, client_id):
		
				queryParams = blankspace.remove_whitespaces(queryParams)
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(func.count(dbconnect.Account.accountcode)).\
				      filter((func.lower(dbconnect.Account.accountcode)) == queryParams[0].lower()).\
				      scalar()
				Session.close()
				connection.connection.close()
		
				if result == 0:
					return "0"
				else:
					return "1"
			
	+ def xmlrpc_getAccount(self, queryParams, client_id):
	
		- Searches and returns account details.  
		- Search is based on either accountcode or account name.
		- it query to ``group_subgroup_account`` view from ``rpc_main``	
		- input: 
			* searchFlag as integer (1 means search by account code and 2 means account name )
			* searchValue as text (value depends on the searchFlag)
		- output: list of below values
			* groupname
			* subgroupname (if any)
			* accountcode
			* accountname
			* openingbalance
		
		.. code-block:: python
		
			def xmlrpc_getAccount(self, queryParams, client_id):
		
				if queryParams[0] == 1:
					statement = "select groupname,subgroupname,accountcode,accountname,openingbalance\
					     		from group_subgroup_account\
					     		where accountcode = '"+queryParams[1]+"'"
				else:	     	
					statement = "select groupname,subgroupname,accountcode,accountname,openingbalance\
					     		from group_subgroup_account\
					     		where accountname = '"+queryParams[1]+"'"	
				result = dbconnect.engines[client_id].execute(statement).fetchone()
		
				if result[1] == None:
					return [result[2], result[0],"No subgroup", result[3], result[4]]
				else:
					return [result[2], result[0], result[1], result[3], result[4]]
			
	+ def xmlrpc_getAccountNamesByGroupCode(self,queryParams,client_id):
	
		- to get accountname accourding to given groupcode.
		- it query to the ``Account`` tables.
		- Input: [groupcode]
		- Output: it will return list of accountname else return empty list
		
		.. code-block:: python
		
			def xmlrpc_getAccountNamesByGroupCode(self,queryParams,client_id):
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)	
				result = Session.query(dbconnect.Account.accountname).\
						filter(dbconnect.Account.groupcode == queryParams[0]).\
						order_by(dbconnect.Account.accountname).all()
				Session.commit()
				Session.close()
				connection.connection.close()
		
				accountnames = []
				if result == []:
					return result
				else:
					for account in result:
						accountnames.append(str(account[0]))
			
					return accountnames
					
	+ def xmlrpc_getAccountNamesByProjectName(self,queryParams,client_id):
	
		- call ``getProjectcoeByProjectName`` to get projectcode
		- using projectcode we will get accountnames used in transaction 
		  for given projectname. 
		- function will return list of accountnames for particular projectname
		- Input: [projectname]
		- output: list of accountnames
		
		.. code-block:: python
		
			def xmlrpc_getAccountNamesByProjectName(self,queryParams,client_id):
		
				transaction = rpc_transaction.transaction()
				projectcode = transaction.xmlrpc_getProjectcodeByProjectName(queryParams,client_id)
				statement = "select distinct(account_name)\
				     		from view_voucherbook\
				     		where projectcode = '"+str(projectcode)+"'\
						and flag = 1\
						order by account_name"  
				result = dbconnect.engines[client_id].execute(statement).fetchall()
				accountname = []
				for Row in result:
					accountname.append(Row[0])
				return accountname       
	
	+ def xmlrpc_deleteAccount(self, queryParams, client_id):
	
		- function for deleting accountname row
		- Input: accountname as string.
		- Output: returns 1 when account is deleted
		
		.. code-block:: python
		
			def xmlrpc_deleteAccount(self, queryParams, client_id):
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Account).\
				      	 	filter(dbconnect.Account.accountname == queryParams[0]).\
				      		delete()
				Session.commit()
				Session.close()
				connection.connection.close()	
				return "1"
		
	+ def xmlrpc_hasOpeningBalance(self, queryParams, client_id):
	
		- function to find out whether the given account 
		  has opening balance
		- Input: accountname(datatype:string)
		- Output: if opening balance of accountname is 
		  0 then return "0" else return "1"
			
		.. code-block:: python
		
			def xmlrpc_hasOpeningBalance(self, queryParams, client_id):
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Account.openingbalance).\
			      	 		filter(dbconnect.Account.accountname == queryParams[0]).\
			      			scalar()
				Session.close()
				connection.connection.close()
				if result == 0:
					return "0"
				else:	
					return "1"
			
	+ def xmlrpc_hasTransactions(self, queryParams, client_id):
	
		- function to find out whether the given account 
		  has any transactions or not
		- Input: accountname as string.
		- returns: if there is any voucher entry of that accountname 
		  return 1 or else return 0
		
		.. code-block:: python
		
			def xmlrpc_hasTransactions(self, queryParams, client_id):
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
		
				statement = "select count(vouchercode) as vouchercodeCount\
					     		from view_voucherbook\
					     		where account_name ='"+str(queryParams[0])+"'"
		
				result = dbconnect.engines[client_id].execute(statement).fetchone()
				Session.close()
				connection.connection.close()
				if result[0] == 0:
					return 0
				if result[0]  > 0:
					return 1
		
	+ def xmlrpc_deleteAccountNameMaster(self,queryParams,client_id):
			
		- function for deleting accounts.
		- for this we have used ``hasOpeningBalance`` ``hasTransactions`` 
		  & ``deleteAccount`` rpc functions. 
		- with the help of ``hasTransactions`` we are able to find out whether 
		  the given account has any transactions or not. 
		- it tells that if there is any voucher entry of that accountname 
		  return 1 or else return 0
		- The function ``hasOpeningBalance`` returns ``1`` if opening balance 
		  for that account exists or else returns ``0``
		- third function ``deleteAccount`` deletes that particular accountname
		- Input: [accountname,flag] or [accountcode,flag]
		- Output:  if hasOpenibalance is 0 and hasTransaction is 0
			returns string "account deleted"
			if hasOpenibalance is 1 and hasTransaction is 1
			returns string "has both opening balance and trasaction" 
			
		.. code-block:: python
		
			def xmlrpc_deleteAccountNameMaster(self,queryParams,client_id):
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				#if flag is 1, that means first element is account name
				if queryParams[1] == 1:
					accName = str(queryParams[0])
		    			hasOpeningBalance = self.xmlrpc_hasOpeningBalance([accName],client_id)
		    			hasTransactions = self.xmlrpc_hasTransactions([accName],client_id)
		    		else:
		    			#if flag is 2, that means first element is account code,
		    			#we have to get accountname by accountcode
		    			
		    			accName = self.xmlrpc_getAccountNameByAccountCode([str(queryParams[0])],client_id)
		    			hasOpeningBalance = self.xmlrpc_hasOpeningBalance([accName],client_id)
		    			hasTransactions = self.xmlrpc_hasTransactions([accName],client_id)
				Session.close()
				connection.connection.close()
			
				if(str(hasOpeningBalance) == "0" and str(hasTransactions) == "0"):
				    self.xmlrpc_deleteAccount([accName],client_id)
				    return "account deleted"
				elif(str(hasOpeningBalance) == "1" and str(hasTransactions) == "1"):
				    return "has both opening balance and trasaction"
				elif(str(hasOpeningBalance) == "1"):
				    return "has opening balance"
				elif(str(hasTransactions) == "1"):
				    return "has transaction"
		
	+ def xmlrpc_getAccountNameByAccountCode(self, queryParams, client_id):
	
		- function to get accountname provided the accountcode
		- querys the account table and sees if an acountcode
		  similar to one provided as a parameter.
		- if it exists then it will return accountname
		- Input: [accountcode]
		- Output: Return accountname if present else empty list
			
		.. code-block:: python
		
			def xmlrpc_getAccountNameByAccountCode(self, queryParams, client_id):	
		
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.Account.accountname).\
					  	 	filter(dbconnect.Account.accountcode == queryParams[0]).\
							first()
					
				Session.close()
				connection.connection.close()
				if result == None:
					return []
				else:
					return result[0]

