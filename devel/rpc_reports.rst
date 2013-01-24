rpc_reports
+++++++++++
	+ This rpc module will provide details about all reports
	+ It privides calculation's for all reports and it's display
	+ it genarate Ledger,trial balance,balancesheet,profit and loss
	  and so on ...  
	+ create class ``reports`` inherit the ``XMLRPC`` class
		- import modules as needed
		
		.. code-block:: python
		
			import dbconnect
			import rpc_account
			import rpc_transaction
			import rpc_groups
			import rpc_getaccountsbyrule
			from twisted.web import xmlrpc, server
			from twisted.internet import reactor
			from datetime import datetime,time
			from modules import blankspace
			from sqlalchemy import or_ , func , and_

			class reports(xmlrpc.XMLRPC):
	
				def __init__(self):
					xmlrpc.XMLRPC.__init__(self)
	
	+ def xmlrpc_getLedger(self,queryParams,client_id):
		
		- This function give a complete ledger for given account.  
		- input: [accountname,financialStart,fromdate,todate,projectname]
		- output: Returns a grid (2 dimentional list ) with columns as 
			[Date, Particulars, Reference number, Dr, Cr , vouchercode]
		
		.. code-block:: python
		
			def xmlrpc_getLedger(self,queryParams,client_id):
				# first let's get the details of the given account regarding the
				# Balance and its Dr/Cr side.
				# note that we use the calculateBalance function which gives us
				# the B/f balance, C/F balance group type and type of the balance.
				# we will not use this if a project is specified, as there is no 
				# point in displaying opening balance.
				# if all transactions are to be searched then project 
				# queryparams[4] will be "No Project".
				queryParams = blankspace.remove_whitespaces(queryParams)
				balanceRow = self.xmlrpc_calculateBalance([queryParams[0],queryParams[1],queryParams[2],queryParams[3]],client_id)
		
				if queryParams[4] == "No Project":
					# calculateBalance will return opening balance 
					openingBalance = balanceRow[1]
				else:
					# if ledger of account which is not under any project 
					# will have opening balance 0.00
					openingBalance = 0.00
				# declare the ledgerGrid as a blank list.
				# we will fill it up through a for loop where every iteration will append a row with 5 columns.
				ledgerGrid = []
				# Let's start with 0 for Total Dr and total Cr amounts.
				totalDr = 0.00
				totalCr = 0.00
				if openingBalance != 0:
					# since we know that balance is not 0, we must decide if it is Cr or Dr balance.
					# This can be found out depending on the opening_baltype from the stored procedure calculateBalance.
					if balanceRow[5] == "Dr":
						#this makes the first row of the grid.
						#note that the total Dr is also set.  Same will happen in the next condition for Cr.
						openingdate = datetime.strptime(str(queryParams[1]),"%d-%m-%Y").strftime("%d-%m-%Y")
						ledgerGrid.append([openingdate,"Opening Balance b/f","",'%.2f'%(openingBalance),"","",""])
					if balanceRow[5] == "Cr":
						openingdate = datetime.strptime(str(queryParams[1]),"%d-%m-%Y").strftime("%d-%m-%Y")
						ledgerGrid.append([openingdate,"Opening Balance b/f","","",'%.2f'%(openingBalance),"",""])
				
				else:
					# its 0 so will be set to 0.
					totalDr= 0.00
					totalCr = 0.00
				# create the instance of transaction 
				transaction = rpc_transaction.transaction()
				# call getTransactions to get the transaction details for this account.
		
		
				transactions = transaction.xmlrpc_getTransactions([\
							queryParams[0],queryParams[2],queryParams[3],queryParams[4]],client_id)
		
				# fill up the grid with the rows for transactions.
				for transactionRow in transactions:
					ledgerRow = []
					date = str(transactionRow[2]).split(" ")
					#print type(str(transactionRow[2]).split(""))
					transactionDate = datetime.strptime(date[0],"%Y-%m-%d").strftime("%d-%m-%Y")
			
					# if the transaction had the amount at Dr side then particulars must have the names of accounts involved in Cr.
					if transactionRow[1] == "Dr":
						particulars = transaction.xmlrpc_getParticulars([transactionRow[0],"Cr"],client_id)
						#may be more than one account was involved at the other side so loop through.
						particular = []
						for particularRow in particulars:
							particular.append(particularRow)
					
						if len(particular) == 1:
							ledgerRow.append(transactionDate)
							ledgerRow.append(particular[0])
							ledgerRow.append(transactionRow[3])
							ledgerRow.append('%.2f'%(float(transactionRow[4])))
							totalDr = totalDr + float(transactionRow[4])
							ledgerRow.append("")
							ledgerRow.append(transactionRow[5])
						else:
							accountNames = ""
							refno = transactionRow[3]
							drAmount = '%.2f'%(float(transactionRow[4]))
							crAmount = ""
							narration = transactionRow[5]
							for i in range (0, len(particular)):
								if (i == len(particular)-1):
									accountNames = accountNames + particular[i]
								else:
									transactionDate = transactionDate + "\n"
									accountNames = accountNames + particular[i] + "\n"
									refno = refno + "\n"
									drAmount = drAmount + "\n"
									crAmount = crAmount + "\n"
									narration = narration + "\n"
						
							ledgerRow.append(transactionDate)
							ledgerRow.append(accountNames)
							ledgerRow.append(refno)
							ledgerRow.append(drAmount)
							ledgerRow.append(crAmount)
							ledgerRow.append(narration)
					
				
					if transactionRow[1] == "Cr":
						particulars = transaction.xmlrpc_getParticulars([transactionRow[0],"Dr"],client_id)
						particular = []
				
						for particularRow in particulars:
							particular.append(particularRow)
					
						if len(particular) == 1:
							ledgerRow.append(transactionDate)
							ledgerRow.append(particular[0])
							ledgerRow.append(transactionRow[3])
							ledgerRow.append("")
							ledgerRow.append('%.2f'%(float(transactionRow[4])))
							totalCr = totalCr + float(transactionRow[4])
							ledgerRow.append(transactionRow[5])
						else:
							accountNames = ""
							refno = transactionRow[3]
							drAmount = ""
							crAmount = '%.2f'%(float(transactionRow[4]))
							narration = transactionRow[5]
							for i in range (0, len(particular)):
								if (i == len(particular)-1):
									accountNames = accountNames + particular[i]
								else:
									transactionDate = transactionDate + "\n"
									accountNames = accountNames + particular[i] + "\n"
									refno = refno + "\n"
									drAmount = drAmount + "\n"
									crAmount = crAmount + "\n"
									narration = narration + "\n"
						
							ledgerRow.append(transactionDate)
							ledgerRow.append(accountNames)
							ledgerRow.append(refno)
							ledgerRow.append(drAmount)
							ledgerRow.append(crAmount)
							ledgerRow.append(narration)
				
					ledgerRow.append(transactionRow[0])
					ledgerGrid.append(ledgerRow)
				#the transactions have been filled up duly.
				#now for the total dRs and Crs, we have added them up nicely during the grid loop.
				ledgerGrid.append(["","Total of Transactions","",'%.2f'%(totalDr),'%.2f'%(totalCr),"",""])
				if queryParams[4] == "No Project":
					ledgerGrid.append(["","","","","","",""])
					grandTotal = 0.00
					closingdate = datetime.strptime(str(queryParams[3]),"%d-%m-%Y").strftime("%d-%m-%Y")
					if balanceRow[6] == "Dr":
					#this is a Dr balance which will be shown at Cr side.
					#Difference will be also added to Cr for final balancing.
						ledgerGrid.append([closingdate,"Closing Balance b/f","","",'%.2f'%(balanceRow[2]),"",""])
						grandTotal =float(balanceRow[4])  + float(balanceRow[2])
					if balanceRow[6] == "Cr":
					#now exactly the opposit, see the explanation in the if condition preceding this one.

						ledgerGrid.append([closingdate,"Closing Balance b/f","",'%.2f'%(balanceRow[2]),"","",""])
						grandTotal =float(balanceRow[3])  + float(balanceRow[2])
					ledgerGrid.append(["","Grand Total","",'%.2f'%(grandTotal),'%.2f'%(grandTotal),"",""])
				#we are ready with the complete ledger, so lets send it out!
				return ledgerGrid
			
	+ def xmlrpc_calculateBalance(self,queryParams,client_id):
	
		- calculate closing balance of given accounts 
		- Returns a grid of 4 columns and number of 
		  rows depending on number of accounts.
		- input:
		  [accountname,org_financial_from,report_from_date,report_to_date]
		- returns:
		  [group_name,bal_brought,curbal,total_DrBal,total_CrBal,opening_baltype,baltype]
		  
		 .. code-block:: python
		 
			def xmlrpc_calculateBalance(self,queryParams,client_id):
				# get the groupname accourding to ac
				statement = "select groupname\
					     from group_subgroup_account\
					     where accountname = '"+queryParams[0]+"'"
				result = dbconnect.engines[client_id].execute(statement).fetchone()
		
				group_name = result[0]
			
				statement = "select openingbalance\
					      from group_subgroup_account\
					      where accountname = '"+queryParams[0]+"'"
				result = dbconnect.engines[client_id].execute(statement).fetchone()
				opening_balance = result[0]
				#print "opening_balance"
				#print opening_balance
				financial_fromdate = str(datetime.strptime(str(queryParams[1]),"%d-%m-%Y"))
				report_fromdate =  str(datetime.strptime(str(queryParams[2]),"%d-%m-%Y"))
				report_todate =  str(datetime.strptime(str(queryParams[3]),"%d-%m-%Y"))
		
				if financial_fromdate == report_fromdate:
					if opening_balance == 0:
				 		bal_brought = opening_balance 
		     				opening_baltype = 0
				 		baltype = 0
			     		if (opening_balance < 0) and (group_name == 'Current Asset' \
			     							or group_name == 'Fixed Assets'\
			     							or group_name == 'Investment' \
			     							or group_name == 'Loans(Asset)' \
			     							or group_name == 'Miscellaneous Expenses(Asset)'): 
						bal_brought = abs(opening_balance) 
				 		opening_baltype = 'Cr' 
				 		baltype = 'Cr'
				 		
					if (opening_balance > 0) and (group_name == 'Current Asset' \
										or group_name =='Fixed Assets'\
										or group_name == 'Investment' \
										or group_name == 'Loans(Asset)' \
										or group_name == 'Miscellaneous Expenses(Asset)'): 
						bal_brought = opening_balance
						opening_baltype = 'Dr'
						baltype = 'Dr'
				
					if (opening_balance < 0 ) and (group_name == 'Corpus' \
										or group_name == 'Capital' \
										or group_name == 'Current Liability' \
										or group_name == 'Loans(Liability)' \
										or group_name == 'Reserves'):
						bal_brought = abs(opening_balance)
						opening_baltype = 'Dr'
						baltype = 'Dr'
	
					if (opening_balance > 0) and (group_name == 'Corpus' \
										or group_name == 'Capital' \
										or group_name == 'Current Liability'\
										or group_name == 'Loans(Liability)'\
										or group_name == 'Reserves'):
						bal_brought = opening_balance
						opening_baltype = 'Cr'
						baltype = 'Cr'
					#print baltype	
		
				else:
					statement = "select sum(amount) as dr_amount\
						from view_voucherbook \
						where account_name = '"+queryParams[0]+"'\
						and typeflag = 'Dr' \
						and reffdate >= '"+financial_fromdate+"' \
						and reffdate < '"+report_fromdate+"'\
						and flag = 1"
	
					result = dbconnect.engines[client_id].execute(statement).fetchone()
					total_dr_upto_from = result[0]
			
					statement = "select sum(amount) as cr_amount \
						from view_voucherbook \
						where account_name ='"+queryParams[0]+"'\
						and typeflag = 'Cr' \
						and reffdate >= '"+financial_fromdate+"' \
						and reffdate < '"+report_fromdate+"' \
						and flag = 1"
				
					result = dbconnect.engines[client_id].execute(statement).fetchone()
					total_cr_upto_from = result[0]
			
					if total_dr_upto_from == None: 
						total_dr_upto_from = 0
	
					if total_cr_upto_from == None:
						total_cr_upto_from = 0
	
					if opening_balance == 0:
						bal_brought = opening_balance

					if (opening_balance < 0) and (group_name == 'Current Asset'\
										or group_name == 'Fixed Assets'\
										or group_name == 'Investment' \
										or group_name == 'Loans(Asset)' \
										or group_name == 'Miscellaneous Expenses(Asset)'):
								
						total_cr_upto_from = total_cr_upto_from + abs(opening_balance)
	
					if (opening_balance > 0) and (group_name == 'Current Asset'\
										or group_name == 'Fixed Assets'\
										or group_name == 'Investment'\
										or group_name == 'Loans(Asset)'\
										or group_name == 'Miscellaneous Expenses(Asset)'):
										 
						total_dr_upto_from = total_dr_upto_from + opening_balance
	
					if (opening_balance < 0) and (group_name == 'Corpus'\
										or group_name == 'Capital'\
										or group_name == 'Current Liability'\
										or group_name == 'Loans(Liability)'\
										or group_name == 'Reserves'):
										 
						total_dr_upto_from = total_dr_upto_from + abs(opening_balance)
	
					if (opening_balance > 0) and (group_name == 'Corpus'\
										or group_name == 'Capital'\
										or group_name == 'Current Liability'\
										or group_name == 'Loans(Liability)'\
										or group_name == 'Reserves'):
										 
						total_cr_upto_from = total_cr_upto_from + opening_balance 
				
					if total_dr_upto_from > total_cr_upto_from: 
						bal_brought = total_dr_upto_from - total_cr_upto_from
						baltype = 'Dr'
						opening_baltype = 'Dr'
	
					if total_dr_upto_from < total_cr_upto_from:
						bal_brought = total_cr_upto_from - total_dr_upto_from 
						baltype = 'Cr'
						opening_baltype = 'Cr'
				
		
				statement = "select sum(amount) as dr_amount\
						from view_voucherbook\
						where typeflag = 'Dr'\
						and account_name = '"+queryParams[0]+"'\
						and reffdate >= '"+report_fromdate+"'\
						and reffdate <= '"+report_todate+"' \
						and flag = 1"
				result = dbconnect.engines[client_id].execute(statement).fetchone()
				total_DrBal = result[0]
		
				statement = "select sum(amount) as cr_amount\
						from view_voucherbook\
						where typeflag ='Cr'\
						and account_name = '"+queryParams[0]+"'\
						and reffdate >= '"+report_fromdate+"'\
						and reffdate <= '"+report_todate+"'\
						and flag = 1"
				result = dbconnect.engines[client_id].execute(statement).fetchone()
				total_CrBal = result[0]
		
				if total_CrBal == None: 
					total_CrBal = 0 
				if total_DrBal == None: 
					total_DrBal = 0 

				if baltype == 'Dr': 
					total_DrBal = total_DrBal + bal_brought 
				if baltype == 'Cr':
					total_CrBal = total_CrBal + bal_brought 

				if total_DrBal > total_CrBal: 
					curbal = total_DrBal - total_CrBal
					baltype = 'Dr'
				else:
					curbal = total_CrBal - total_DrBal
					baltype = 'Cr'

				calculate_balancelist = [group_name,bal_brought,curbal,total_DrBal,total_CrBal,opening_baltype,baltype]
				return calculate_balancelist
		
		
	+ def xmlrpc_getTrialBalance(self,queryParams,client_id):
		- gets trial balance as on the given date. 
		- returns a grid of 4 columns and number of 
		  rows depending on number of accounts.
		- this function returns a grid of 4 columns contaning 
		  trial balance.
		- number of rows in this grid will depend on the number 
		  of accounts in the database.
		- the function first makes a call to the getAllAccounts 
		  from rpc_account to get accountlist
		- then a loop runs through the list of accounts.
		  on every iteration it calls the ``calculateBalance`` and 
		  passes the account as a parameter along with the financial start
		  from_date and to_date
		- note that trial balance is always calculated from the starting 
		  of the financial year.
		- also in the for loop we see if the typeflag for the balance 
		  for given account is Dr or Cr.
		- if the balance is Dr then we put the amount in the 4th column, 
		  with the 5th column blank.
		- if the typeflag is ``credit`` then we put the amount in the 5th row, 
		  leaving the 4th as blank.
		  and vice varsa
		- input: [org_financial_from,from_date,to_date]
		- output: [serial no , accountname , groupname , debit bal , creadit bal ]
		 	and [total debit , total credit]
		 	
		.. code-block:: python
		
			def xmlrpc_getTrialBalance(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				account = rpc_account.account()
				accounts = account.xmlrpc_getAllAccountNames(client_id)
				trialBalance = []
				srno =1
				total_dr = 0.00
				total_cr = 0.00
		
				for account in accounts:
			
					closingRow = self.xmlrpc_calculateBalance([account,queryParams[0],queryParams[1],queryParams[2]],client_id)
			
					if float(closingRow[2])!= 0:
						trialRow = []
						trialRow.append(srno)
						trialRow.append(account)
						trialRow.append(closingRow[0])
						if closingRow[6] == "Cr":
							total_cr = total_cr + float(closingRow[2])
							trialRow.append("")
							trialRow.append('%.2f'%float(closingRow[2]))
						if closingRow[6] == "Dr":
							total_dr = total_dr + float(closingRow[2])
							trialRow.append('%.2f'%float(closingRow[2]))
							trialRow.append("")
						srno = srno +1
						trialBalance.append(trialRow)
				total_balances = ["","","",'%.2f'%total_dr,'%.2f'%total_cr]
				trialBalance.append(total_balances)
	
				return trialBalance
		
	+ def xmlrpc_getGrossTrialBalance(self,queryParams,client_id):
		
		- just like the getTrialBalance, this function returns
		  list of balances of all accounts.
		- instead of the current balance it provides 
		  the total Dr and total Cr for all accounts.
		- input: [serial no , accountname , groupname , debit bal , creadit bal ]
		 	and [total debit , total credit]
		 	
		.. code-block:: python
		
			def xmlrpc_getGrossTrialBalance(self,queryParams,client_id):
				account = rpc_account.account()
				accounts = account.xmlrpc_getAllAccountNames(client_id)
				trialBalance = []
				srno =1
				total_dr = 0.00
				total_cr = 0.00
				for acc in accounts:
			
					closingRow = self.xmlrpc_calculateBalance(\
								[acc,queryParams[0],queryParams[1],queryParams[2]],client_id)
			
					if float(closingRow[3]) != 0 or float(closingRow[4]) != 0:
						trialRow = []
						trialRow.append(srno)
						trialRow.append(acc)
						trialRow.append(closingRow[0])
						trialRow.append('%.2f'%float(closingRow[3]))
						trialRow.append('%.2f'%float(closingRow[4]))
						total_dr = total_dr + float(closingRow[3])
						total_cr = total_cr + float(closingRow[4])
						srno = srno +1
						trialBalance.append(trialRow)
				total_balances = ['','','','%.2f'%total_dr,'%.2f'%total_cr]
				trialBalance.append(total_balances)
				return trialBalance	
	
	+ def xmlrpc_getExtendedTrialBalance(self,queryParams,client_id):
		- gets extended trial balance as on the given date. 
		- Returns a grid of 7 columns and number of rows 
		  depending on number of accounts.
		
		.. code-block:: python
		
			def xmlrpc_getExtendedTrialBalance(self,queryParams,client_id):  
				queryParams = blankspace.remove_whitespaces(queryParams)
				account = rpc_account.account()
				accounts = account.xmlrpc_getAllAccountNames(client_id)
				trialBalance = []
				srno =1
				total_dr = 0.00
				total_cr = 0.00
				total_ExtendedCr = 0.00
				total_ExtendedDr = 0.00
				for acc in accounts:
			
					closingRow = self.xmlrpc_calculateBalance(\
								[acc,queryParams[0],queryParams[1],queryParams[2]],client_id)
			
					if float(closingRow[1]) != 0 or float(closingRow[3]) != 0 or float(closingRow[4]) != 0:
						trialRow = []
						trialRow.append(srno)
						trialRow.append(acc)
						trialRow.append(closingRow[0])
						if float(closingRow[1]) != 0 and closingRow[5] == "Dr":
							trialRow.append('%.2f'%float(closingRow[1])+"(Dr)")
							trialRow.append('%.2f'%(float(closingRow[3])- float(closingRow[1])))
							total_dr = total_dr + (float(closingRow[3]) - float(closingRow[1]))
							trialRow.append('%.2f'%float(closingRow[4]))
							total_cr = total_cr +float(closingRow[4])
						if float(closingRow[1]) != 0 and closingRow[5] == "Cr":
							trialRow.append('%.2f'%float(closingRow[1])+"(Cr)")
							trialRow.append('%.2f'%float(closingRow[3]))
							total_dr = total_dr + float(closingRow[3])
							trialRow.append('%.2f'%(float(closingRow[4])- float(closingRow[1])))
							total_cr = total_cr + (float(closingRow[4]) - float(closingRow[1]))
						if float(closingRow[1]) == 0:
							trialRow.append("")
							trialRow.append('%.2f'%float(closingRow[3]))
							total_dr = total_dr + float(closingRow[3])
							trialRow.append('%.2f'%float(closingRow[4]))
							total_cr = total_cr + float(closingRow[4])
						if closingRow[6] == "Dr":
							trialRow.append('%.2f'%float(closingRow[2]))
							trialRow.append("")
							total_ExtendedDr = total_ExtendedDr + float(closingRow[2])
						if closingRow[6] == "Cr":
							trialRow.append("")
							trialRow.append('%.2f'%float(closingRow[2]))
							total_ExtendedCr = total_ExtendedCr + float(closingRow[2]) 
						srno = srno +1
						trialBalance.append(trialRow)
				total_balances = ['','','','','%.2f'%total_dr,'%.2f'%total_cr,'%.2f'%total_ExtendedDr,'%.2f'%total_ExtendedCr]
				trialBalance.append(total_balances)
				return trialBalance
		
	+ def xmlrpc_getProjectStatementReport(self,queryParams,client_id):
		- it gives project statement report
		- input: [projectname,financial_fromdate,fromdate,todate]
		- output: list of list [serialno,accountname,groupname,totalDr,totalCr]
		
		.. code-block:: python
		
			def xmlrpc_getProjectStatementReport(self,queryParams,client_id):
				account = rpc_account.account()
				group = rpc_groups.groups()
				projectAccounts =account.xmlrpc_getAccountNamesByProjectName([str(queryParams[0])],client_id)
				totalDr = 0.00
				totalCr = 0.00
				srno = 1
				projectStatement = []
				for accountRow in projectAccounts:
			
					groupRow = group.xmlrpc_getGroupNameByAccountName([accountRow],client_id)
					accountGroup = groupRow[0]
					resultRow = self.xmlrpc_getProjectStatement(\
					[queryParams[0],accountRow,queryParams[1],queryParams[2],queryParams[3]],client_id)
			
					if(('%.2f'%float(resultRow[0])!= "0.00" )or('%.2f'%float(resultRow[1])!="0.00")):
						statementRow = [srno,accountRow,accountGroup,'%.2f'%float(resultRow[0]),'%.2f'%float(resultRow[1])]
						totalDr = totalDr + resultRow[0]
						totalCr = totalCr + resultRow[1]
						srno = srno +1
						projectStatement.append(statementRow)
				projectStatement.append(["","","",'%.2f'%float(totalDr),'%.2f'%float(totalCr)])
				return projectStatement
	
	+ def xmlrpc_getProjectStatement(self,queryParams,client_id):
	
		- it gives project statement grid .
		- input: [projectname,accountname,financial_fromdate,fromdate,todate]
		- output: [total_debit,total_credit]
		
		.. code-block:: python
		
			def xmlrpc_getProjectStatement(self,queryParams,client_id):
				financial_fromdate = str(datetime.strptime(str(queryParams[2]),"%d-%m-%Y"))
				report_fromdate =  str(datetime.strptime(str(queryParams[3]),"%d-%m-%Y"))
				report_todate =  str(datetime.strptime(str(queryParams[4]),"%d-%m-%Y"))
				transaction = rpc_transaction.transaction()
				projectcode = transaction.xmlrpc_getProjectcodeByProjectName([queryParams[0]],client_id)
				statement = "select sum(amount)\
					     		from view_voucherbook\
					     		where projectcode = '"+str(projectcode)+"'\
					     		and account_name = '"+str(queryParams[1])+"'\
					     		and reffdate >= '"+report_fromdate+"'\
							and reffdate <= '"+report_todate+"' \
					     		and typeflag = 'Dr'\
							and flag = 1"  	
				totalDr = dbconnect.engines[client_id].execute(statement).fetchone()
				total_dr = totalDr[0]
				statement = "select sum(amount)\
					     		from view_voucherbook\
					     		where projectcode = '"+str(projectcode)+"'\
					     		and account_name = '"+str(queryParams[1])+"'\
					     		and reffdate >= '"+report_fromdate+"'\
							and reffdate <= '"+report_todate+"' \
					     		and typeflag = 'Cr'\
							and flag = 1"  	
				totalCr = dbconnect.engines[client_id].execute(statement).fetchone()
				total_cr = totalCr[0]
		
				if total_dr == None:
					total_dr = 0.00
		
				if total_cr == None:
					total_cr = 0.00	
				
				return [total_dr,total_cr]
	
	+ def xmlrpc_getBalancesheet(self,queryParams,client_id):
		
		- gets trial balance as on the given date.
		- returns a grid of 4 columns and number of rows depending 
		  on number of accounts.
		- This function returns a grid of 4 columns contaning balancesheet.
		- Number of rows in this grid will depend on the number 
		  of accounts in the database.
		- input: [org_financial_from,from_date,to_date]
		
		.. code-block:: python
		
			def xmlrpc_getBalancesheet(self,queryParams,client_id):
				assetGrpCodes = [6,2,10,9,13]
				liabilitiesGrpCodes = [1,3,11,12]
				balancesheet = []
				assetSrno = 1; liabilitiesSrno = 1
				total_asset_balances = 0.00; 
				total_liabilities_balances = 0.00
				tot_capital = 0.00 
				tot_currliabilities = 0.00 
				tot_loansliabilities = 0.00
				tot_reserves = 0.00
				tot_fixedasset = 0.00
				tot_currentasset = 0.00
				tot_loansasset = 0.00
				tot_investment = 0.00
				tot_miscExpense = 0.00
				account = rpc_account.account()
				for grpCode in liabilitiesGrpCodes:
			
					accounts = account.xmlrpc_getAccountNamesByGroupCode([grpCode],client_id)
					if accounts != []:
			
						for acc in accounts:
							assetrow = []; liabilitiesrow = []
							closingRow = self.xmlrpc_calculateBalance(\
								[acc,queryParams[0],queryParams[1],queryParams[2]],client_id)
				
							if closingRow[6] == "Cr":
								closingBalanceAmount = float(closingRow[2]) 
							else:
								closingBalanceAmount = - float(closingRow[2])
							if closingBalanceAmount != 0:
								liabilitiesrow.append(liabilitiesSrno)
								liabilitiesrow.append(grpCode)
								liabilitiesrow.append(acc)
								liabilitiesrow.append('%.2f'%(closingBalanceAmount))
								if (grpCode == 1):
									tot_capital += closingBalanceAmount
								if (grpCode == 3):
									tot_currliabilities += closingBalanceAmount
								if (grpCode == 11):
									tot_loansliabilities += closingBalanceAmount
								if (grpCode == 12):
									tot_reserves += closingBalanceAmount
								total_liabilities_balances += closingBalanceAmount
								balancesheet.append(liabilitiesrow)
								liabilitiesSrno += 1
				for grpCode in assetGrpCodes:
					accounts = account.xmlrpc_getAccountNamesByGroupCode([grpCode],client_id)
					if accounts != []:
						for acc in accounts:
							assetrow = []; liabilitiesrow = []
							closingRow = self.xmlrpc_calculateBalance(\
								[acc,queryParams[0],queryParams[1],queryParams[2]],client_id)
				
							if closingRow[6] == "Dr":
								closingBalanceAmount = float(closingRow[2]) 
							else:
								closingBalanceAmount = - float(closingRow[2]) 
							if closingBalanceAmount != 0:
								assetrow.append(assetSrno)
								assetrow.append(grpCode)
								assetrow.append(acc)
								assetrow.append('%.2f'%(closingBalanceAmount))
								if (grpCode == 6):
									tot_fixedasset += closingBalanceAmount
								if (grpCode == 2):
									tot_currentasset += closingBalanceAmount
								if (grpCode == 10):
									tot_loansasset += closingBalanceAmount
								if (grpCode == 9):
									tot_investment += closingBalanceAmount
								if (grpCode == 13):
									tot_miscExpense += closingBalanceAmount
								total_asset_balances += closingBalanceAmount
								balancesheet.append(assetrow)
								assetSrno += 1
				balancesheet.append(assetSrno - int(1))
				balancesheet.append(liabilitiesSrno - int(2))
				balancesheet.append('%.2f'%(float(tot_investment)))
				balancesheet.append('%.2f'%(float(tot_loansasset)))
				balancesheet.append('%.2f'%(float(tot_currentasset)))
				balancesheet.append('%.2f'%(float(tot_fixedasset)))
				balancesheet.append('%.2f'%(float(tot_miscExpense)))
				balancesheet.append('%.2f'%(float(tot_currliabilities)))
				balancesheet.append('%.2f'%(float(tot_loansliabilities)))
				balancesheet.append('%.2f'%(float(tot_capital)))
				balancesheet.append('%.2f'%(float(tot_reserves)))
				balancesheet.append('%.2f'%(float(total_liabilities_balances)))
				balancesheet.append('%.2f'%(float(total_asset_balances)))
		
				return balancesheet
		
	+ def xmlrpc_getBalancesheetDisplay(self,queryParams,client_id):	
		
		- note that this function is not do any calculations
		  it is just for the format for front end display.
		- it will take the return grid of ``getBalancesheet``.
		- to rearrange the data in a particular manner for display. 
		- input: [org_financial_from,from_date,to_date,reportflag,orgtype,balancesheet_type]
		
		.. code-block:: python 
		
			def xmlrpc_getBalancesheetDisplay(self,queryParams,client_id):
				# flag to check balancesheet or profitloss
				reportFlag = queryParams[3]
				balancesheet_type = queryParams[5] # type of balancesheet
				orgtype = queryParams[4] # type of organisation, NGO or Profit Making
				finallist = [] # initialize empty list for final grid
				corpuslist = [] # initialize empty list for left table(Corpus or Capital)
				assetslist = [] # initialize empty list for final grid(Properties n Assets)
				LeftList = []
			
				# get the values of balacesheet to rearrange it
				trialdata = self.xmlrpc_getBalancesheet(queryParams,client_id)
		
				baltrialdata = trialdata
		
				assSrno = trialdata[len(trialdata) - int(13)] 
				liaSrno = trialdata[len(trialdata) - int(12)]
				if (assSrno > liaSrno):
					rowFlag = "liabilities"
					rows = assSrno - liaSrno
				elif (assSrno < liaSrno):
					rowFlag = "asset"
					rows = liaSrno - assSrno
				else:
					rowFlag = ""
			
				assetrowcolor = assSrno + int(4)
				liabilitiesrowcolor = liaSrno + int(4)
				tot_miscellaneous = trialdata[len(trialdata) - int(7)]
				tot_investment = trialdata[len(trialdata) - int(11)]
				tot_loansasset = trialdata[len(trialdata) - int(10)]
				tot_currentasset = trialdata[len(trialdata) - int(9)]
				tot_fixedasset = trialdata[len(trialdata) - int(8)]
				tot_capital = trialdata[len(trialdata) - int(4)]
				tot_currlia = trialdata[len(trialdata) - int(6)]
				tot_loanlia = trialdata[len(trialdata) - int(5)]
				tot_reserves = trialdata[len(trialdata) - int(3)]
				ballength = len(trialdata) - int(13)
				lialength = len(trialdata) - int(1)
				asslength = len(trialdata) - int(2)
				reportFlag = "balancesheet"
				# To get NetProfit and NetLoss from profit and loss and display in balncesheet 
				profitloss = self.xmlrpc_getProfitLossDisplay(queryParams,client_id)
		
				totalDr = trialdata[lialength]
				totalCr = trialdata[asslength]
				Flag = profitloss[0]
		
				pnlcr = float(totalCr) + float(profitloss[1])
				pnldr = float(totalDr) + float(profitloss[1])
				pnl1 = '%.2f'%float(pnlcr)
				pnl2 = '%.2f'%float(pnldr)
				Rcount = 0 # counter to count rightside(assets colo rows)
				Lcount = 0 # counter to count leftside(Capital& Liabilities row)
		
				if Flag =="netProfit":
		
					if float(totalDr) > float(pnlcr):
			
						difamount ='%.2f'%(float(totalDr) - float(pnlcr))
					else:
						difamount = '%.2f'%(float(pnlcr)-float(totalDr))
				else:
					if float(totalCr) > float(pnldr):
			
						difamount = '%.2f'%(float(totalCr) - float(pnldr))
					else:
						difamount = '%.2f'%(float(pnldr)-float(totalCr))# get the values of balacesheet to rearrange it
			
					
				if balancesheet_type == "Conventional Balance Sheet": 
			
				
					if (orgtype == "NGO"):
			
						groupname = "Corpus & Liabilities"
			
					if (orgtype == "Profit Making"):
			
						groupname = "Capital & Liabilities"
			
					corpuslist.append([groupname,"Debit","Credit","Total Amount"])	
			
					#Lcount = Lcount+1		
				
			
					if (tot_capital != "0.00"):
				
						if (orgtype == "NGO"):
			
							groupname="CORPUS"
				
						if (orgtype == "Profit Making"):
				
							groupname="CAPITAL"
				
						corpuslist.append([groupname,"","",""])
				
						Lcount = Lcount+1
						
						for i in range (0, ballength):
				
							if (baltrialdata[i][1] == 1):
					
								accountname = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									corpuslist.append(["         "+accountname,"",'%.2f'%(abs(amount)),""])
							
								else:
									corpuslist.append(["         "+accountname,'%.2f'%(amount),"",""])
							
								Lcount = Lcount+1
						
						corpuslist.append(["","","",tot_capital])
					
						Lcount = Lcount+1
					
					if (tot_reserves != "0.00"):
			
						corpuslist.append(["RESERVES","","",""])
					
						Lcount = Lcount+1
					
						for i in range (0, ballength):
				
							if (baltrialdata[i][1] == 12):
					
								accountname = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
						
								if(amount<0):
									corpuslist.append(["         "+accountname,"",'%.2f'%(abs(amount)),""])
							
								else:	
									corpuslist.append(["         "+accountname,'%.2f'%(amount),"",""])
							
								Lcount = Lcount+1
				
						corpuslist.append(["","","",tot_reserves])	
						Lcount = Lcount+1	
				
					if (tot_loanlia != "0.00"):
				
						corpuslist.append(["LOANS(Liability)","","",""])
						Lcount = Lcount+1		
				
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 11):
								accountname = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
						
								if(amount<0):
						
									corpuslist.append(["         "+accountname,"",'%.2f'%(abs(amount)),""])
								else:
						
									corpuslist.append(["         "+accountname,'%.2f'%(amount),"",""])
							
								Lcount = Lcount+1	
			
						corpuslist.append(["","","",tot_loanlia])		
						Lcount = Lcount+1
			
					if (tot_currlia != "0.00"):
					
						corpuslist.append(["CURRENT LIABILITIES","","",""])
						Lcount = Lcount+1		
				
						for i in range (0, ballength):
				
							if (baltrialdata[i][1] == 3):
					
								accountname = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
						
								if(amount < 0):
									corpuslist.append(["         "+accountname,"",'%.2f'%(abs(amount)),""])
							
								else:	
									corpuslist.append(["         "+accountname,'%.2f'%(amount),"",""])
								Lcount = Lcount+1
				
						corpuslist.append(["","","",tot_currlia])
						Lcount = Lcount+1
										
					if (Flag != "netLoss"):
				
						if (orgtype != "NGO"):
							flag = "NET PROFIT"
						else:
							flag = "NET SURPLUS"
					
					
						if (profitloss[1] != "0.00"):
							corpuslist.append(["",flag,profitloss[1],""])	
							Lcount = Lcount+1
							
					#else:
						#corpuslist.append(["","","",""])	
						#Lcount = Lcount+1	
			
					if (rowFlag == "liabilities"):
						for i in range (0, rows):
							corpuslist.append(["","","",""])	
							Lcount = Lcount+1	
			
					############# ASSETS ###################		
				
					assetslist.append(["Property & Assets","Debit","Credit","Total amount"])	
					#Rcount = Rcount+1
			
					if (tot_fixedasset != "0.00"):	
			
						assetslist.append(["FIXED ASSETS","","",""])
						Rcount = Rcount+1
					
						for i in range (0, ballength):
				
							if (baltrialdata[i][1] == 6):
					
								accountname = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
						
									assetslist.append(["         "+accountname,"",'%.2f'%(abs(amount)),""])
					
								else:	
									assetslist.append(["         "+accountname,'%.2f'%(amount),"",""])
								Rcount = Rcount+1
			
						assetslist.append(["","","",tot_fixedasset])	
						Rcount = Rcount+1
			
					if (tot_currentasset != "0.00"):
				
						groupname="CURRENT ASSETS"
						assetslist.append([groupname,"","",""])
						Rcount = Rcount+1	
				
						for i in range (0, ballength):
				
							if (baltrialdata[i][1] == 2):
					
								accountname = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
						
								if(amount < 0):
							
									assetslist.append(["         "+accountname,"",'%.2f'%(abs(amount)),""])
							
								else:	
									assetslist.append(["         "+accountname,'%.2f'%(amount),"",""])
								Rcount = Rcount+1
						
						assetslist.append(["","","",tot_currentasset])	
						Rcount = Rcount+1
				
					if (tot_loansasset != "0.00"):	
			
						groupname="LOANS(Asset)"
						assetslist.append([groupname,"","",""])
						Rcount = Rcount+1	
				
						for i in range (0, ballength):
				
							if (baltrialdata[i][1] == 10):
					
								accountname = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
						
								if(amount<0):
									assetslist.append(["         "+accountname,"",'%.2f'%(abs(amount)),""])
							
								else:	
									assetslist.append(["         "+accountname,'%.2f'%(amount),"",""])
						
								Rcount = Rcount+1
			
						assetslist.append(["","","",tot_loansasset])	
						Rcount = Rcount+1
				
					if (tot_investment != "0.00"):
				
						groupname="INVESTMENTS"
						assetslist.append([groupname,"","",""])
						Rcount = Rcount+1	
				
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 9):
					
								accountname = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
						
								if(amount<0):
									assetslist.append(["         "+accountname,"",'%.2f'%(abs(amount)),""])
							
								else:	
									assetslist.append(["         "+accountname,'%.2f'%(amount),"",""])
						
								Rcount = Rcount+1
			
						assetslist.append(["","","",tot_investment])	
						Rcount = Rcount+1
			
					if (tot_miscellaneous != "0.00"):
				
						groupname="MISCELLANEOUS EXPENSES(Asset)"
						assetslist.append([groupname,"","",""])
						Rcount = Rcount+1	
				
						for i in range (0, ballength):
				
							if (baltrialdata[i][1] == 13):
					
								accountname = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									assetslist.append(["         "+accountname,"",'%.2f'%(abs(amount)),""])
							
								else:	
									assetslist.append(["         "+accountname,'%.2f'%(amount),"",""])
						
								Rcount = Rcount+1
			
						assetslist.append(["","","",tot_miscellaneous])	
						Rcount = Rcount+1
					
					if (Flag == "netLoss"):
				
						if (orgtype != "NGO"):
							flag = "NET LOSS"
						else:
							flag = "NET DEFICIT"
				
						if (profitloss[1] != "0.00"):	
							assetslist.append(["",flag,profitloss[1],""])# net surplus or net profit
							Rcount = Rcount+1
					#else:
						#assetslist.append(["","","",""])	
						#Rcount = Rcount+1
			
					if (Lcount > Rcount):
						diff = Lcount - Rcount
					
						for i in range(0,diff):
							assetslist.append(["","","",""])
					else:
						diff = Rcount - Lcount	
		
						for i in range(0,diff):
							corpuslist.append(["","","",""])
			
					if (Flag == "netLoss"):
						if (difamount != "0.00"):
							totalCr
						else:
							totalCr
					
						corpuslist.append(["TOTAL","","",totalCr])	
					
						if (difamount != "0.00"):
							pnl2
						else:
							pnl2
					
						assetslist.append(["TOTAL","","",pnl2])	
				
					if (Flag != "netLoss"):	
						if (difamount != "0.00"):
							pnl1
						else:
							pnl1
						corpuslist.append(["TOTAL","","",pnl1])	
					
						if (difamount != "0.00"):
							totalDr
						else:
							totalDr
					
						assetslist.append(["TOTAL","","",totalDr])	
				
					finallist.append(corpuslist)
					finallist.append(assetslist)
			
					difflist = [] # initialize empty list for empty spaces
			
					difflist.append(difamount)	
					finallist.append(difflist)
			
					return finallist
		
				if balancesheet_type == "Sources and Application of Funds": 
			
					LeftList.append(["Particulars","Debit","Credit","Amount","Amount"])
					LeftList.append(["SOURCES OF FUNDS","","","",""])	
					if (tot_capital != "0.00"):			
						if (orgtype == "NGO"):
							LeftList.append(["        CORPUS","","","",""])	
					
					
						if (orgtype == "Profit Making"):
							LeftList.append(["        OWNER'S CAPITAL","","","",""])		
					
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 1):
								account = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									LeftList.append(["            "+account,"",'%.2f'%(abs(amount)),"",""])
								else:
									LeftList.append(["            "+account,'%.2f'%(amount),"","",""])
						if (orgtype == "NGO"):		
							LeftList.append(["TOTAL CORPUS","","","",tot_capital])
						else:
							LeftList.append(["TOTAL CAPITAL","","","",tot_capital])
					
					if (profitloss[1] != "0.00"):			
						LeftList.append(["        ADD: RESERVES","","","",""])				
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 12):
								account =baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									LeftList.append(["              "+account,"",'%.2f'%(abs(amount)),"",""])
								else:
									LeftList.append(["              "+account,'%.2f'%(amount),"","",""])
							
						if (Flag == "netLoss"):
					
							if (orgtype != "NGO"):
								flag ="Net Loss"
							else:
								flag="Net Deficit"
				
							LeftList.append(["              "+flag,profitloss[1],"","",""])	
					
							amount = float(tot_reserves) - float(profitloss[1])
					
							LeftList.append(["TOTAL RESERVES & SURPLUS","","","",'%.2f'%float(amount)])		
						else:
				
							if (orgtype != "NGO"):
								flag ="Net Profit"
							else:
								flag ="Net Surplus"
					
							amount =float(tot_reserves) + float(profitloss[1])
							LeftList.append(["              "+flag,profitloss[1],"","",""])	
							LeftList.append(["TOTAL RESERVES & SURPLUS","","","",'%.2f'%float(amount)])			
			
					if (tot_miscellaneous != "0.00"):		
						LeftList.append(["        LESS: MISCELLANEOUS EXPENSES(Asset)","","","",""])
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 13):
								account =baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									LeftList.append(["              "+account,"",'%.2f'%(abs(amount)),"",""])
								else:
									LeftList.append(["              "+account,'%.2f'%(amount),"","",""])
							
						LeftList.append(["TOTAL MISCELLANEOUS EXPENSES(Asset)","","",tot_miscellaneous,""])							
			
					if (Flag == "netLoss"):
						amount = float(tot_reserves) - float(profitloss[1]) - float(tot_miscellaneous)
					else:
						amount = float(tot_reserves) + float(profitloss[1]) - float(tot_miscellaneous)
			
					if (amount != 0):
						LeftList.append(["TOTAL OF OWNER'S FUNDS","","","",'%.2f'%float(amount)])
			
					if (tot_loanlia != "0.00"):	
						LeftList.append(["        BORROWED FUNDS","","","",""])
						LeftList.append(["        LOANS(Liability)","","","",""])
		
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 11):
								account = baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
						
									LeftList.append(["            "+account,"",'%.2f'%(abs(amount)),"",""])
								else:
									LeftList.append(["            "+account,'%.2f'%(amount),"","",""])
						LeftList.append(["TOTAL BORROWED FUNDS","","","",tot_loanlia])
		
					if (difamount != "0.00"):
						if (Flag == "netLoss"):
							amount= float(tot_capital) + \
								float(tot_loanlia) + \
								(float(tot_reserves) - float(profitloss[1]) - float(tot_miscellaneous))
						else:
							amount= float(tot_capital) + \
								float(tot_loanlia) + \
								(float(tot_reserves) + float(profitloss[1]) - float(tot_miscellaneous))
						LeftList.append(["TOTAL FUNDS AVAILABLE / CAPITAL EMPLOYED","","","",'%.2f'%float(amount)])
					else:
						if (Flag == "netLoss"):
							amount= float(tot_capital) + \
								float(tot_loanlia) + \
								(float(tot_reserves) - float(profitloss[1]) - float(tot_miscellaneous))
						else:
							amount= float(tot_capital) + \
								float(tot_loanlia) + \
								(float(tot_reserves) + float(profitloss[1]) - float(tot_miscellaneous))
						LeftList.append(["TOTAL FUNDS AVAILABLE / CAPITAL EMPLOYED","","","",'%.2f'%float(amount)])
					finallist.append(LeftList)
		
					###################### secound list ####################
		
					RightList = []
					RightList.append(["Particulars","Debit","Credit","Amount","Amount"])
					RightList.append(["APPLICATION OF FUNDS","","","",""])
			
					if (tot_fixedasset != "0.00"):
						RightList.append(["        FIXED ASSETS","","","",""])
		
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 6):
				
								account=baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									RightList.append(["              "+account,"",'%.2f'%(abs(amount)),"",""])
								else:
									RightList.append(["              "+account,'%.2f'%(amount),"","",""])
						RightList.append(["TOTAL FIXED ASSETS(Net)","","","",tot_fixedasset])
			
					if (tot_investment != "0.00"):
						RightList.append(["        INVESTMENT","","","",""])	
		
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 9):
								account =baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									RightList.append(["              "+account,"",'%.2f'%(abs(amount)),"",""])
								else:
									RightList.append(["              "+account,'%.2f'%(amount),"","",""])
						RightList.append(["TOTAL LONG TERM INVESTMENTS","","","",tot_investment])
			
			
					if (tot_loansasset != "0.00"):
						RightList.append(["        LOANS(Asset)","","","",""])		
		
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 10):
								account =baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									RightList.append(["              "+account,"",'%.2f'%(abs(amount)),"",""])
								else:
									RightList.append(["              "+account,'%.2f'%(amount),"","",""])
						RightList.append(["TOTAL LOANS(Asset)","","","",tot_loansasset])
			
					if (tot_currentasset != "0.00"):
						RightList.append(["        WORKING CAPITAL","","","",""])		
						RightList.append(["        CURRENT ASSETS","","","",""])	
		
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 2):
								account=baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									RightList.append(["              "+account,"",'%.2f'%(abs(amount)),"",""])
								else:
									RightList.append(["              "+account,'%.2f'%(amount),"","",""])
						RightList.append(["TOTAL CURRENT ASSETS","","",tot_currentasset,""])
			
					if (tot_currlia != "0.00"):		
						RightList.append(["        LESS:","","","",""])
						RightList.append(["        CURRENT LIABILITIES","","","",""])
			
						for i in range (0, ballength):
							if (baltrialdata[i][1] == 3):
								account=baltrialdata[i][2]
								amount = float(baltrialdata[i][3])
								if(amount<0):
									RightList.append(["              "+account,"",'%.2f'%(abs(amount)),"",""])
								else:
									RightList.append(["              "+account,'%.2f'%(amount),"","",""])
						RightList.append(["TOTAL CURRENT LIABILITIES","","",tot_currlia,""])
			
			
					amount = float(tot_currentasset) - float(tot_currlia)
					if (amount != 0):
			
						RightList.append(["NET CURRENT ASSETS OR WORKING CAPITAL","","","",'%.2f'%float(amount)])	
						RightList.append(["        CURRENT ASSETS","","","",""])	
		
			
					if (difamount != "0.00"):
						amount= float(tot_fixedasset) + \
							float(tot_investment) + \
							float(tot_loansasset) + (float(tot_currentasset) - float(tot_currlia))
						RightList.append(["TOTAL FUNDS AVAILABLE / CAPITAL EMPLOYED","","","",'%.2f'%float(amount)])
					else:
						amount= float(tot_fixedasset) + \
							float(tot_investment) + \
							float(tot_loansasset) + (float(tot_currentasset) - float(tot_currlia))
						RightList.append(["TOTAL FUNDS AVAILABLE / CAPITAL EMPLOYED","","","",'%.2f'%float(amount)])
					finallist.append(RightList)
		
					if (difamount != "0.00"):
						flag = difamount
			
					else:
						flag=""
			
					finallist.append([flag])
			
					return finallist
			
	+ def xmlrpc_getProfitLossReport(self,queryParams,client_id):
		- gets profit and loss as on the given date.
		- Returns a grid of 4 columns and number of rows 
		  depending on number of accounts.
		- This function returns a grid of 4 columns 
		  contaning profit and loss details.
		- Number of rows in this grid will depend on the number 
		  of accounts in the database.
		- For profit and loss the accounts from group ``direct income``
		  and ``indirect income`` and ``expence`` are invoke.
		- The function first makes a call to the``getAccountsByGroupCode``
		  and stores the list.
		- then a loop runs through the list of accounts.
		  on every iteration it calls the ``calculateBalance`` 
		  and passes the account as a parameter along with the 
		  financial start, Calculate_from and calculate_to.
		- Note: profit and loss is always calculated from 
		  the starting of the financial year.
		- the total of each group of accounts is calculated 
		  separately for calculation purpose.
		- input: [org_financial_from,from_date,to_date]
		- output: List of list [serial no,groupcode,accountname,amount,balancetype]
		
		.. code-block:: python
		
			def xmlrpc_getProfitLossReport(self,queryParams,client_id):
				grpCodes = [4,5,7,8]
				profitloss = []
				srno = 1
				total_dirInc_balances = 0.00; total_dirExp_balances =0.00
				total_indirInc_balances =0.00; total_indirExp_balances = 0.00
				grossProfit = 0.00 ; grossLoss = 0.00
				netProfit = 0.00 ; netLoss = 0.00
				account = rpc_account.account()
				for grpCode in grpCodes:
					accounts = account.xmlrpc_getAccountNamesByGroupCode([grpCode],client_id)
					if accounts != []:
						for acc in accounts:
							profitlossrow = []
							closingRow = self.xmlrpc_calculateBalance(\
								[acc,queryParams[0],queryParams[1],queryParams[2]],client_id)
							print "closingRow"
							print closingRow
							profitlossrow.append(srno)
							profitlossrow.append(grpCode)
							profitlossrow.append(acc)
							profitlossrow.append('%.2f'%(float(closingRow[2])))
							profitlossrow.append(str(closingRow[6]))
							srno = srno + 1
							profitloss.append(profitlossrow)
							# Direct Expense
							if grpCode == 5:
								if str(closingRow[6]) == "Dr":
									total_dirExp_balances = total_dirExp_balances + float(closingRow[2])
								else:
									total_dirInc_balances = total_dirInc_balances + float(closingRow[2])
							# Indirect Expense 	
							if grpCode == 8:
								if str(closingRow[6]) == "Dr":
									total_indirExp_balances = total_indirExp_balances + float(closingRow[2])
								else:
									total_indirInc_balances = total_indirInc_balances + float(closingRow[2])
							# Direct Income
							if grpCode == 4:
								if str(closingRow[6]) == "Cr":
									total_dirInc_balances = total_dirInc_balances + float(closingRow[2])
								else:
									total_dirExp_balances = total_dirExp_balances + float(closingRow[2])
							# Indirect Income
							if grpCode == 7:
								if str(closingRow[6]) == "Cr":
									total_indirInc_balances = total_indirInc_balances + float(closingRow[2])
								else:
									total_indirExp_balances = total_indirExp_balances + float(closingRow[2])
				
				
				profitloss.append('%.2f'%(float(total_dirInc_balances)))
				profitloss.append('%.2f'%(float(total_dirExp_balances)))
				profitloss.append('%.2f'%(float(total_indirInc_balances)))
				profitloss.append('%.2f'%(float(total_indirExp_balances)))
				if (total_dirInc_balances > total_dirExp_balances):
					grossProfit = total_dirInc_balances - total_dirExp_balances
			
					profitloss.append("grossProfit")
					# gross profit C/O
					profitloss.append('%.2f'%(float(grossProfit))) 
					# add gross profit C/o to Total amount of II 
					totalnetprofit = total_indirInc_balances + grossProfit 
					# Total Net Profit Check with the Total amount of IE 
					if(totalnetprofit > total_indirExp_balances):
						netProfit = totalnetprofit - total_indirExp_balances
						grandTotal = netProfit+total_indirExp_balances
						profitloss.append("netProfit")
						profitloss.append('%.2f'%(float(netProfit))) #
						profitloss.append('%.2f'%(float(totalnetprofit))) 
						profitloss.append('%.2f'%(float(grandTotal))) # final total amount 
						print "grossprofitGrid"
						print profitloss
					else:
						netLoss = total_indirExp_balances - totalnetprofit
						grandTotal = netLoss + totalnetprofit
						profitloss.append("netLoss")
						profitloss.append('%.2f'%(float(netLoss)))
						profitloss.append('%.2f'%(float(total_indirExp_balances)))
						profitloss.append('%.2f'%(float(grandTotal)))
						print "grossLossGrid"
						print profitloss
				else:
					grossLoss = total_dirExp_balances - total_dirInc_balances
					profitloss.append("grossLoss")
					profitloss.append('%.2f'%(float(grossLoss)))
					totalnetloss = total_indirExp_balances + grossLoss
			
					if(totalnetloss > total_indirInc_balances):
						netLoss = totalnetloss - total_indirInc_balances
						grandTotal = netLoss+totalnetloss 
						profitloss.append("netLoss")
						profitloss.append('%.2f'%(float(netLoss)))
						profitloss.append('%.2f'%(float(totalnetloss)))
						profitloss.append('%.2f'%(float(grandTotal)))
					else:
						netProfit = total_indirInc_balances - totalnetloss
						grandTotal = netProfit+total_indirInc_balances
						profitloss.append("netProfit")
						profitloss.append('%.2f'%(float(netProfit)))
						profitloss.append('%.2f'%(float(total_indirInc_balances)))
						profitloss.append('%.2f'%(float(grandTotal)))
				
				return profitloss
		
	+ def xmlrpc_getProfitLossDisplay(self,queryParams,client_id):	
		- it takes the return grid of getProfitLossReport
		- this function is just for the arrangement of grid
		  for display
		- input:[org_financial_from,from_date,to_date,reportflag,orgtype]
		
		.. code-block:: pyhton
		
			def xmlrpc_getProfitLossDisplay(self,queryParams,client_id):
				orgtype=queryParams[4]
				trialdata = self.xmlrpc_getProfitLossReport(queryParams,client_id)
				print "trialdata"
				print trialdata
				length = len(trialdata) - int(10)
				grandTotal =trialdata[len(trialdata) - int(1)]
				print "grandToatal"
				print grandTotal
				netTotal = trialdata[len(trialdata) - int(2)]
				dirincm = trialdata[len(trialdata) - int(10)]
				direxp = trialdata[len(trialdata) - int(9)]
				indirincm = trialdata[len(trialdata) - int(8)]
				indirexp = trialdata[len(trialdata) - int(7)]
				grossFlag = trialdata[len(trialdata) - int(6)]
				grossProfitloss = trialdata[len(trialdata) - int(5)]
				print "grossProfitloss"
				print grossProfitloss
				netFlag = trialdata[len(trialdata) - int(4)]
				netProfitloss = trialdata[len(trialdata) - int(3)]
				print "trialdata"
				print trialdata
				finalList =[]
				DEList=[]
				DIList=[]
				DEcount = 0
				DIcount = 0
				IEcount = 0
				IIcount = 0
				if queryParams[3] == "balancesheet":
					return [netFlag, netProfitloss]
				else:
		
					############### Direct Expense Accounts ################
					DEList.append(["","Particulars","Amount"])
					DEList.append(["","Direct Expense",""])
					for i in range (0, length):
						# groupcode 5 is Direct Expense
						if (trialdata[i][1] == 5):
							if (trialdata[i][4] == "Dr"):
								if (trialdata[i][3]!="0.00"):
							
									DEList.append(["To,",trialdata[i][2],trialdata[i][3]])
									DEcount = DEcount+1
						# groupcode 4 is Direct Income
						if (trialdata[i][1] == 4):
							if (trialdata[i][4] == "Dr"):
								if (trialdata[i][3]!="0.00"):
									DEList.append(["To,",trialdata[i][2],trialdata[i][3]])
									DEcount = DEcount + 1
							
					if (grossFlag == "grossProfit"):
				
						if (orgtype == "Profit Making"):
							flag = "Gross Profit C/O"
				
						if (orgtype == "NGO"):
							flag ="Gross Surplus C/O"
					
						if (grossProfitloss != "0.00"):	
							DEcount = DEcount+1
							DEList.append(["To,",flag,grossProfitloss])
					
			
					################### Direct Income Accounts ##############	
			
					DIList.append(["","Particulars","Amount"])
					DIList.append(["","Direct Income",""])		
					for i in range (0, length):
						# groupcode 4 is Direct Income
						if (trialdata[i][1] == 4):
							if (trialdata[i][4] == "Cr"):
								if (trialdata[i][3]!="0.00"):
									DIList.append(["By,",trialdata[i][2],trialdata[i][3]])
									DIcount = DIcount+1
						# groupcode 5 is Direct Expense			
						if (trialdata[i][1] == 5):
							if (trialdata[i][4] == "Cr"):
								if (trialdata[i][3]!="0.00"):
									DIList.append(["By,",trialdata[i][2],trialdata[i][3]])
									DIcount = DIcount+1
			
					if (grossFlag == "grossLoss"):
				
						if (orgtype == "Profit Making"):
							flag="Gross Loss C/O"
			
						if (orgtype == "NGO"):
							flag="Gross Deficit C/O"
					
						if (grossProfitloss != "0.00"):	
							DIcount = DIcount+1
							DIList.append(["By,",flag,grossProfitloss])
				
					############### for empty coloum in Direct Expense ########
	
					if (DIcount > DEcount):
						diff = DIcount - DEcount
						for i in range(0,diff):
							DEList.append(["","",""])
			
					
					###############  Total amount for direct expense  ##########
			
					if (grossFlag == "grossProfit"):
						amount = dirincm
				
					if (grossFlag == "grossLoss"):
						amount= direxp
				
					DEList.append(["","Total",amount])
			
			
				
					#######################  Indirect Expense Accounts #####################
			
					DEList.append(["","",""])
					DEList.append(["","Indirect Expense",""])	
			
				
					if (grossFlag == "grossLoss"):
				
						if (orgtype == "Profit Making"):
							flag ="Gross Loss B/F"
					
						if (orgtype == "NGO"):
							flag ="Gross Deficit B/F"
					
						if (grossProfitloss != "0.00"):	
							IEcount = IEcount+1
							DEList.append(["To,",flag,grossProfitloss])
				
					for i in range (0, length):
						# groupcode 8 for Indirect Expense
						if (trialdata[i][1] == 8):
							if (trialdata[i][4] == "Dr"):
								if (trialdata[i][3]!="0.00"):
									DEList.append(["To,",trialdata[i][2],trialdata[i][3]])
									IEcount = IEcount+1
						# groupcode 7 for Indirect Income			
						if (trialdata[i][1] == 7):
							if (trialdata[i][4] == "Dr"):
								if (trialdata[i][3]!="0.00"):
									DEList.append(["To,",trialdata[i][2],trialdata[i][3]])
									IEcount = IEcount+1
							
							
					if (grossFlag == "grossProfit" and netFlag == "netProfit"):
				
						if (orgtype == "Profit Making"):
							flag = "Net Profit"
					
						if (orgtype == "NGO"):
							flag = "Net Surplus"
					
						if (netProfitloss != "0.00"):	
							IEcount = IEcount+1
							DEList.append(["To,",flag,netProfitloss])		
				
					if (grossFlag == "grossLoss" and netFlag == "netProfit"):
				
						if (orgtype == "Profit Making"):
							flag = "Net Profit"
					
						if (orgtype == "NGO"):
							flag = "Net Surplus"
					
						if (netProfitloss != "0.00"):	
							IEcount = IEcount+1
							DEList.append(["To,",flag,netProfitloss])
					
			
					############## for empty coloum in Indirect Expense ######
			
					if (IIcount > IEcount):
						diff = IIcount - IEcount
						for i in range(0,diff):
							DEList.append(["","",""])
					
					############# Total of IE amount ##################
					if (netFlag == "netLoss"):
						amount=netTotal
			
					if (netFlag == "netProfit"):
						amount=grandTotal
				
					DEList.append(["","Total",amount])
			
					finalList.append(DEList) # Final list for entire first coloumn DE + IE
			
					############### for empty coloum in Direct Income ######
	
					if (DEcount > DIcount):
						diff = DEcount - DIcount
						for i in range(0,diff):
							DIList.append(["","",""])
	
						
					#################### Total of DI ammount ###############
			
					if (grossFlag == "grossProfit"):
						amount= dirincm
		
					if (grossFlag == "grossLoss"):
						amount= direxp
				
					DIList.append(["","Total",amount])
			
					################### Indirect Income Accounts ###########
			
					DIList.append(["","",""])
					DIList.append(["","Indirect Income",""])
				
					if (grossFlag == "grossProfit"):
				
						if (orgtype == "Profit Making"):
							flag = "Gross Profit B/F"
				
						if (orgtype == "NGO"):
							flag = "Gross Surplus B/F"
					
						if (grossProfitloss != "0.00"):	
							IIcount = IIcount+1	
							DIList.append(["By,",flag,grossProfitloss])
			
			
					for i in range (0, length):
						# groupcode 7 is for Indirect Income 
						if (trialdata[i][1] == 7):
							if (trialdata[i][4] == "Cr"):
								if (trialdata[i][3]!="0.00"):
									DIList.append(["By,",trialdata[i][2],trialdata[i][3]])
									IIcount = IIcount+1	
						# groupcode 8 is for Indirect Expense			
						if (trialdata[i][1] == 8):
				
							if (trialdata[i][4] == "Cr"):
								if (trialdata[i][3]!="0.00"):
									DIList.append(["By,",trialdata[i][2],trialdata[i][3]])
									IIcount = IIcount+1	
				
					if (grossFlag == "grossProfit" and netFlag == "netLoss"):
				
						if (orgtype == "Profit Making"):
							flag = "Net Loss"
			
						if (orgtype == "NGO"):
							flag = "Net Deficit"
					
						if (netProfitloss != "0.00"):	
							IIcount = IIcount+1	
							DIList.append(["By,",flag,netProfitloss])
				
			
			
					if (grossFlag == "grossLoss" and netFlag == "netLoss"):
				
						if (orgtype == "Profit Making"):
							flag = "Net Loss"
			
						if (orgtype == "NGO"):
							flag = "Net Deficit"
					
						if (netProfitloss != "0.00"):	
							IIcount = IIcount+1		
							DIList.append(["By,",flag,netProfitloss])
			
					############## for empty coloum in Indirect Income #############
			
					if (IEcount > IIcount):
						diff = int(IEcount - IIcount)
				
						for i in range(0,diff):
					
							DIList.append(["","",""])
		
					################ Total of II amount ###################
			
					if (netFlag == "netLoss"):
						amount= netTotal
				
					if (netFlag == "netProfit"):
						amount= grandTotal
			
					DIList.append(["","Total",amount])
					finalList.append(DIList) # Final list of 2nd coloumn DI +II
			
				return finalList
	
	+ def xmlrpc_getReconLedger(self,queryParams,client_id):
		- to get a complete ledger for given bank account.
		- information taken from view_voucherbook	
		- for getting ledger it takes the result of rpc_getLedger.
		- it expects a list of queryParams which contains
		- input: [accountname,financialStart,fromdate,todate,projectname]
		- Returns a grid (2 dimentional list ) with columns as 
		  Date, Particulars, Reference number, Dr amount, Cr amount, 
		  narration, Clearance Date and Memo.
		- Note that It will display the value of clearance date and memo 
		  for only those transactions which are cleared.
		- The last row will just contain the grand total which will 
		  be equal at credit and debit side.
		- 2nd last row contains the closing balance.
		- 3rd last row contains just the total Dr and total Cr.
		- if the closing balance (carried forward ) is debit 
		  then it will be shown at credit side and 
		  if it is credit will be shown at debit side.
	
		.. code-block:: python
		
			def xmlrpc_getReconLedger(self,queryParams,client_id):
				# create the instance of transaction 
				transaction = rpc_transaction.transaction()
				#first let's get the details of the given account regarding the 
				#Balance and its Dr/Cr side by calling getLedger function.
				#note that we use the getClearanceDate function which gives us 
				#the clearance date and memo for each account in the ledger.
				print "params========"
				print queryParams
				ledgerResult = self.xmlrpc_getLedger(queryParams,client_id)
		
				reconResult =[]
				#lets declare voucheLcounter to zero
				voucheLcounter = 0
				vouchercodeRecords= transaction.xmlrpc_getTransactions([\
							queryParams[0],queryParams[2],queryParams[3],\
							queryParams[4]],client_id)
		
				# following delete operations are done for avoiding clearance date 
				#and memo in opening balance, totaldr, totalcr and grand total rows.
		
				if ledgerResult[0][1] == "Opening Balance b/f":
					del ledgerResult[0] #opening balance row
		
				del ledgerResult[len(ledgerResult)-1] #grand total row
				del ledgerResult[len(ledgerResult)-1] #closing balance row
				del ledgerResult[len(ledgerResult)-1] #total Dr and Cr row
				del ledgerResult[len(ledgerResult)-1] # empty row
				voucherCodes = []
				for vc in vouchercodeRecords:
					voucherCodes.append(int(vc[0]))
		
				for ledgerRow in ledgerResult:
					if len(str(ledgerRow[0])) == 10:
						print "yes"
						reconRow = []
						reconRow.append(voucherCodes[voucheLcounter]) #voucher code
						reconRow.append(ledgerRow[0]) #voucher date
						reconRow.append(str(ledgerRow[1])) #particular
						reconRow.append(ledgerRow[2]) #ref no
			
						reconRow.append(ledgerRow[3]) #Dr amount
						reconRow.append(ledgerRow[4]) #Cr amount
						reconRow.append(ledgerRow[5]) #narration
			
						clearanceDates =self.xmlrpc_getClearanceDate([\
									str(ledgerRow[1]),voucherCodes[voucheLcounter]],client_id)
						if clearanceDates == []:
							reconRow.append("")
							reconRow.append("")
						else:
							for datesRow in clearanceDates:
								clrdate = str(datesRow.clearancedate).split(" ")
								clrDate = datetime.strptime(clrdate[0],"%Y-%m-%d").strftime("%d-%m-%Y")
								clrMemo = datesRow.memo
								reconRow.append(clrDate)
								reconRow.append(clrMemo)
			
						voucheLcounter = voucheLcounter + 1
						reconResult.append(reconRow)
				return reconResult
		
	+ def xmlrpc_setBankReconciliation(self,queryParams,client_id):
		- it sets the bankrecon table in database as saves 
		  transaction details of those transactions which are
		  cleared with clearance date and memo in table bankrecon
		- Also sets the reconcode(reconciliation code) for the respective 
		  transaction.
		- input: It expects a list of queryParams which contains
		  [vouchercode(datatype:integer),reffdate(datatype:timestamp),
		  accountname(datatype:varchar),dramount(datatype:numeric),
		  cramount(datatype:numeric),clearancedate(datatype:timestamp),
		  memo(datatype:text)] 
		
		.. code-block:: python
		
			def xmlrpc_setBankReconciliation(self,queryParams,client_id):
				# lets create a list containing vouchercode,reffdate,accountname. 
				for clearRow in queryParams:
					sp_params = [clearRow[0],clearRow[1],clearRow[2]]
			
					#if dr_amount is blank, append 0 as dr_amount and respective cr_amount.
					if clearRow[3] == "":
						sp_params.append(0)
						sp_params.append(clearRow[4])
					#if cr_amount is blank, append 0 as cr_amount and respective dr_amount.
					if clearRow[4] == "":
						sp_params.append(clearRow[3])
						sp_params.append(0)
					#Now, lets append respective clearance date and memo				
					sp_params.append(clearRow[5])
					sp_params.append(clearRow[6])
			
					#Finally we are ready to set the bankrecon table.
					success = self.xmlrpc_setBankRecon(sp_params,client_id)
				return success	
		
	+ def xmlrpc_setBankRecon(self,queryParams,client_id):
		- to set the cleared transaction
		- input: [vouchercode,reffdate,accountname,dramount,cramount,clearencedate,memo]
		- output : String success
		
		.. code-block:: python
		
			def xmlrpc_setBankRecon(self,queryParams,client_id):
				queryParams = blankspace.remove_whitespaces(queryParams)
				reffdate =  datetime.strptime(str(queryParams[1]),"%d-%m-%Y")
				clearencedate =  datetime.strptime(str(queryParams[5]),"%d-%m-%Y")
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				reconcode = Session.query(func.max(dbconnect.BankRecon.reconcode)).scalar()
				if reconcode == None:
					reconcode = 0
					reconcode = reconcode + 1
				else:
					reconcode = reconcode + 1
		
				result = Session.query(dbconnect.BankRecon).\
							filter(and_(dbconnect.BankRecon.accountname == queryParams[2],\
							dbconnect.BankRecon.vouchercode == queryParams[0])).\
							first()

				if result == None:
					if queryParams[3] == 0:
						# add all values in the bankrecon table
						Session.add(dbconnect.BankRecon(reconcode,queryParams[0],reffdate,queryParams[2],\
									0,queryParams[4],clearencedate,queryParams[6]))
						Session.commit()
					else:	
						# add all values in the bankrecon table
						Session.add(dbconnect.BankRecon(reconcode,queryParams[0],reffdate,queryParams[2],\
									queryParams[3],0,clearencedate,queryParams[6]))
						Session.commit()
				else:
					Session.query(dbconnect.BankRecon).\
					filter(and_(dbconnect.BankRecon.accountname == queryParams[2],\
							dbconnect.BankRecon.vouchercode == queryParams[0])).\
					delete()
					Session.commit()
					if queryParams[3] == 0:
						# add all values in the bankrecon table
						Session.add(dbconnect.BankRecon(reconcode,queryParams[0],reffdate,queryParams[2],\
									0,queryParams[4],clearencedate,queryParams[6]))
						Session.commit()
					else:
						# add all values in the bankrecon table
						Session.add(dbconnect.BankRecon(reconcode,queryParams[0],reffdate,queryParams[2],\
									queryParams[3],0,clearencedate,queryParams[6]))
						Session.commit()
					 
				Session.close()
				connection.connection.close()
				return "success"
		
	+ def xmlrpc_getClearanceDate(self,queryParams,client_id):
		- to get the Clearancedate of cleared transaction
		- input: [accountname ,vouchercode]
		- output: [clearance date , memo]
		
		.. code-block:: python
			
			def xmlrpc_getClearanceDate(self,queryParams,client_id):
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.BankRecon).\
						filter(and_(dbconnect.BankRecon.accountname==queryParams[0],\
							dbconnect.BankRecon.vouchercode==queryParams[1])).\
							all()
		
				return result
			
	+ def xmlrpc_deleteClearedRecon(self,queryParams,client_id):
		- to uncleared the cleared trasaction and
		  delete cleared entry from bankrecon table 
		- input: [accountname,vouchercode,todate]
		
		.. code-block:: python
		
			def xmlrpc_deleteClearedRecon(self,queryParams,client_id):
				clearencedate =  str(datetime.strptime(str(queryParams[2]),"%d-%m-%Y"))
				connection = dbconnect.engines[client_id].connect()
				Session = dbconnect.session(bind=connection)
				result = Session.query(dbconnect.BankRecon).\
					filter(and_(dbconnect.BankRecon.accountname==queryParams[0],\
					dbconnect.BankRecon.vouchercode==queryParams[1],\
					dbconnect.BankRecon.clearancedate < clearencedate)).delete()
		
				Session.commit()
				Session.close()
				connection.connection.close()
		
				if result == True:
					return True
				else:
					return False
			
			
	
	+ def xmlrpc_updateBankRecon(self,queryParams, flags, client_id):
		
		- returns all uncleared transactions from the starting of 
		  financial year to the end date of given period 
		  ``OR`` 
		- all uncleared transactions from the starting of 
		  financial year to the end date of given period with all cleared transactions 
		  of the given period if cleared_tran_flag is true 
		  with Bank Reconciliation Statement for the given period of time.
		- input: 
			+ list 1: [account name, financial start, fromdate and todate,projectname]
			+ list 2: [cleared_tran_flag]
		
		- this function returns a grid of 9 columns and number 
		  of rows depending on number of uncleared 
		  OR uncleared+cleared transactions in the database. 
		- After appending transactions in grid, 
		  it appends Bank Reconciliation statement.
		- A grid of 9 columns contains:
			+ [vouchercode, transaction date, accountname, reference number,dramount, cramount, narration, clearance date and memo]
		- The function first makes a call to the previous function ``getLedger`` 
		  and passes the account as a parameter along with the 
		  financial start, Calculate_from and calculate_to.
		- note that balance is always calculated from the starting of the financial year.
		- Then, on every iteration it calls following functions 
			1. ``getTransactions``: to get trnsactions from starting date of 
				financial year to the end date of given period
			2. ``getParticulars``: to get all particulars(account names) 
				for that period
			3. ``getOnlyClearedTransactions``: to filter out all uncleared 
				transactions and their details.
			4. if cleared_tran_flag is True, it calls ``getReconLedger`` function to 
			   get the list of cleared transactionsand then it compares ReconGrid 
			   with list of cleared transactions to ignore duplicate transactions
			   and finally added bank reconciliation statement.
			   
		.. code-block:: python
		
			def xmlrpc_updateBankRecon(self,queryParams, flags, client_id):
				cleared_tran_flag = flags[0]
				ReconGrid = []
				totalDbt = 0.00
				totalCdt = 0.00
				# create the instance of transaction 
				transaction = rpc_transaction.transaction()
				#now lets get the transaction details for this account.
				transactions =transaction.xmlrpc_getTransactions([\
							queryParams[0],queryParams[1],queryParams[3],queryParams[4]],client_id)
					
				# [vouchercode , voucherflag , reff_date , voucher_reference,transaction_amount,show_narration]			
				# fill up the grid with the rows for transactions.
				for transactionRow in transactions:
			
					# if the transaction had the amount at Dr side then particulars
					# must have the names of accounts involved in Cr.
					if transactionRow[1] == "Dr":
						particulars = transaction.xmlrpc_getParticulars([transactionRow[0],"Cr"],client_id)
						# [voucher_code,type_flag]
				
						ledgerRow = []
						#may be more than one account was involved at the other side so loop through.
						if len(particulars) == 1:
							for particularRow in particulars:
								cleared =transaction.xmlrpc_getOnlyClearedTransactions([\
										str(particularRow),int(transactionRow[0]),\
										queryParams[1],queryParams[3]],client_id)

								if cleared == False:
									 
									reff_date = str(transactionRow[2]).split(" ")
									reff_date= datetime.strptime(str(reff_date[0]),"%Y-%m-%d").strftime("%d-%m-%Y")
									ledgerRow.append(transactionRow[0])
									ledgerRow.append(reff_date)
									ledgerRow.append(particularRow)
									ledgerRow.append(transactionRow[3])
									ledgerRow.append('%.2f'%(float(transactionRow[4])))
									totalDbt = totalDbt + float(transactionRow[4])
									ledgerRow.append("")
									ledgerRow.append(transactionRow[5])
									ReconGrid.append(ledgerRow)
					
					if transactionRow[1] == "Cr":
						particulars = transaction.xmlrpc_getParticulars([transactionRow[0],"Dr"],client_id)
						# [voucher_code,type_flag]
						ledgerRow = []
						#may be more than one account was involved a tthe other side so loop through.
						if len(particulars) == 1:
							for particularRow in particulars:
								cleared =transaction.xmlrpc_getOnlyClearedTransactions(\
										[str(particularRow),int(transactionRow[0]),\
											queryParams[1],queryParams[3]],client_id)
					
								if cleared == False:
									reff_date = str(transactionRow[2]).split(" ")
									reff_date= datetime.strptime(str(reff_date[0]),"%Y-%m-%d").strftime("%d-%m-%Y")
									ledgerRow.append(transactionRow[0])
									ledgerRow.append(reff_date)
									ledgerRow.append(particularRow)
									ledgerRow.append(transactionRow[3])
									ledgerRow.append("")
									ledgerRow.append('%.2f'%(float(transactionRow[4])))
									ledgerRow.append(transactionRow[5])
									totalCdt = totalCdt + float(transactionRow[4])
									ReconGrid.append(ledgerRow)
		
				for row in ReconGrid:
						row.append("") #clearance date
						row.append("") #memo		
					
				# if cleared transaction flag is true then,
				if cleared_tran_flag == True:
					ReconLedger = self.xmlrpc_getReconLedger(queryParams,client_id)
			
					voucher_list = []
					for v_code in ReconLedger:
						voucher_list.append(v_code[0])
			
					for row in ReconGrid:
							if row[0] not in voucher_list : #row[0] is v_code 
								ReconLedger.append(row)
			
					#arrange rows order by date
					ReconGrid = ReconLedger
				ReconGrid = sorted(ReconGrid,key=lambda x: datetime.strptime(str(x[1]),"%d-%m-%Y"))
		
				# lets add recon statement		
				ReconGrid.append(["","","","Total",'%.2f'%(totalDbt),'%.2f'%(totalCdt),"","",""])
				#lets start making Reconcilition Statement,
				ReconGrid.append(["","RECONCILIATION STATEMENT","","","","AMOUNT"])
				#get the ledger Grid result,
				ledgerResult = self.xmlrpc_getLedger(queryParams,client_id)
		
				BankBal = 0.00
				closingBal = 0.00		
				midTotal = 0.00
		
				#lets get the closing row for closing balance
				closingBalRow = ledgerResult[len(ledgerResult)-2]
				#total of Dr and Cr
				TotalDrCrRow = ledgerResult[len(ledgerResult)-4]
		
				# if opening balance is debit then add opening balance to 
				# total debit amount else to total credit amount
				if ledgerResult[0][2] =="":
					openingBalRow = ledgerResult[0]
					if openingBalRow[3] != "":
						TotalDrCrRow[3] = float(TotalDrCrRow[3]) + float(openingBalRow[3])
					else:
						TotalDrCrRow[4] = float(TotalDrCrRow[4]) + float(openingBalRow[4])
		
				balancedate = str(queryParams[2])
		
				ClosingBalance = float(TotalDrCrRow[3]) - float(TotalDrCrRow[4])
		
				if ClosingBalance == 0:
					ReconGrid.append([balancedate,"Balance as per our book on "+balancedate,"","","",closingBalRow[3]])
					closingBal = float(closingBalRow[3])
				else:
					if closingBalRow[3] != "":
						ReconGrid.append([balancedate,"Balance as per our book (Credit) on "+balancedate,"","","",closingBalRow[3]])
						closingBal = float(closingBalRow[3])
			
					if closingBalRow[4] != "":
						ReconGrid.append([balancedate,"Balance as per our book (Debit) on "+balancedate,"","","",closingBalRow[4]])
						closingBal = float(closingBalRow[4])
			
		
		
				if  ClosingBalance >= 0:
					if totalCdt != 0:
						ReconGrid.append(["","Add: Cheques issued but not presented","","","","+ "+'%.2f'%(totalCdt)])
					else:
						ReconGrid.append(["","Add: Cheques issued but not presented","","","",'%.2f'%(totalCdt)])
					midTotal = closingBal + totalCdt
					ReconGrid.append(["","","","","",""+'%.2f'%(midTotal)])
					if totalDbt != 0:
						ReconGrid.append(["","Less: Cheques deposited but not cleared","","","","- "+'%.2f'%(totalDbt)])
					else:
						ReconGrid.append(["","Less: Cheques deposited but not cleared","","","",'%.2f'%(totalDbt)])
					BankBal = midTotal - totalDbt
			
			
				if  ClosingBalance < 0:
					if totalCdt != 0:
						ReconGrid.append(["","Add: Cheques issued but not presented","","","","+ "+'%.2f'%(totalCdt)])
					else:
						ReconGrid.append(["","Add: Cheques issued but not presented","","","",'%.2f'%(totalCdt)])
					midTotal = totalCdt - closingBal
					ReconGrid.append(["","","","","",""+'%.2f'%(abs(midTotal))])
					if totalDbt != 0:
						ReconGrid.append(["","Less: Cheques deposited but not cleared","","","","- "+'%.2f'%(totalDbt)])
					else:
						ReconGrid.append(["","Less: Cheques deposited but not cleared","","","",'%.2f'%(totalDbt)])
					BankBal = midTotal - totalDbt

				if BankBal < 0:
					ReconGrid.append(["","Balance as per Bank (Debit)","","","",'%.2f'%(abs(BankBal))])

				if BankBal > 0:
					ReconGrid.append(["","Balance as per Bank (Credit)","","","",'%.2f'%(abs(BankBal))])
			
				if BankBal == 0:
					ReconGrid.append(["","Balance as per Bank","","","",'%.2f'%(abs(BankBal))])
	
				return ReconGrid	
		
	def xmlrpc_getCashFlow(self,queryParams,client_id):
	
		- The function will return a grid with 4 columns.
		- first 2 columns will have the account name and its sum of
		  received amount, while next 2 columns will have the same 
		- for amount paid.first we make a call to get CashFlowAccounts 
		- for the list of accounts falling under Bank or Cash subgroups.
		- Then a loop will run through the list and get the list of 
		  payment and receipts as mentioned above.
		- every row will contain a pair of as below
		  account:amount for payment and receipt each.
		- input: financial_from ,start_date and end_date
		
		.. code-block:: python
		
			def xmlrpc_getCashFlow(self,queryParams,client_id):
				# declare the cashFlowGrid, rlist, plist as a blank list.
				# we will fill up cashFlowGrid by appending rlist and plist.
				# rlist will contain the cashflow of received accounts.
				# plist will contain the cashflow of paid accounts.
				cashFlowGrid = []
				rlist = []
				plist = []
				account = rpc_account.account()
				getjournal = rpc_getaccountsbyrule.getaccountsbyrule()
				rlist.append(["Account Name","Amount(Inflow)"])
				#rlist.append(["Opening Balance",""])
				#Let's start with 0 for totalreceivedamount and totalpaid amounts.
				totalreceivedamount = 0.00
				totalpaidamount = 0.00
				#first let's get the list of all accounts coming under cash or 
				#bank subgroup and their respective opening balance.
				cashBankAccounts=account.xmlrpc_getCashFlowOpening(client_id)
				#fill up the rlist with the rows for cashFlowAccounts.
				#also maintaining a list of cash and bank accounts will facilitate 
				#the loop for getting actual cash flow.
				cbAccounts = []
		
				for acc in cashBankAccounts:
					openingRow = []
					#openingRow.append("ob")
					openingRow.append(acc[0])# accountname for opening balance
					cbAccounts.append(acc[0])# accountname for closing balance
		
				cfAccountsRows = getjournal.xmlrpc_getJournalAccounts(client_id)
				# now we will run a nested loop for getting cash flow for all non-cash/bank accounts
				# the outer loop will run through the list of all the cfAccounts 
				# and check for any transactions on them involving bank or 
				# cash based accounts for which we have a list of cbAccounts
				# needless to say this process will happen once for recieved and one for paid transactions.
				for acc in cfAccountsRows:
					receivedAmount = 0.00
					for cb in cbAccounts:
				
						# check how much amount Debit in Cash & bank Accounts(received)
						receivedRow = account.xmlrpc_getCashFlowReceivedAccounts([\
							str(acc),str(cb),queryParams[1],queryParams[2]],client_id)
				
						if receivedRow != None:
							receivedAmount = receivedAmount + float(str(receivedRow[0]))
					if receivedAmount != 0:
						rlist.append([acc,'%.2f'% receivedAmount])	
						totalreceivedamount = totalreceivedamount + float(receivedAmount)
				
				
				plist.append(["Account name","Amount(Outflow)"])		
				for acc in cfAccountsRows:
					paidAmount = 0.00
					for cb in cbAccounts:
				
						# check how much amount Credit  in Cash & bank Accounts(Paid)
						paidRow =account.xmlrpc_getCashFlowPaidAccounts([\
							str(acc),str(cb),queryParams[1],queryParams[2]],client_id)
						if paidRow!= None:
							paidAmount = paidAmount + float(str(paidRow[0]))  
					if paidAmount != 0:
						plist.append([acc,'%.2f'% paidAmount])
				
						totalpaidamount = totalpaidamount + float(paidAmount)
		
				# fill up the plist with the rows for cashFlowAccounts only if paidRow is not none.
				# now sum up the totalpaid amounts.
				# Now lets equate the row of rlist and plist.
				rlength = len(rlist)
				plength = len(plist)
				# if length of rlist is greater than plist then append the blank lists 
				# times of difference in rlist and plist into the plist or vice versa.
				if rlength > plength:
					diflength = rlength - plength
					for d in range(0,diflength):
						plist.append(["",""])
				if rlength < plength:
					diflength = plength - rlength
					for d in range(0,diflength):
						rlist.append(["",""])
				#now append the total receivedamount and total paidamount in respective lists i.e. rlist and plist
				rlist.append(["Total",'%.2f'% totalreceivedamount])
				plist.append(["Total",'%.2f'% totalpaidamount])
				Netlist =[]
				if totalreceivedamount > totalpaidamount:
					diflength = totalreceivedamount - totalpaidamount
					Netlist.append('%.2f'%diflength)
				
				else:
					diflength = totalpaidamount - totalreceivedamount
					Netlist.append("(-)"+'%.2f'%diflength)
				#now append rlist and plist to cashFlowGrid
				cashFlowGrid.append(rlist)
				cashFlowGrid.append(plist)
				cashFlowGrid.append(Netlist)
				
				return cashFlowGrid
		
