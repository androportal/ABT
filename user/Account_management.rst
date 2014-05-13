.. toctree::
   :numbered:

Account management
==================
* In this tutorial, you will be able to ``Create Account, Edit
  Account, Delete Account``.

* Go to ``create account`` which is the first menu by default.

Save account code preference
----------------------------

* When you enter this page for the first time, the alert-box appears
  on the screen.  the alert prompts a dialog to set account code type
  i.e. manual or automated.
  
.. image:: images/account_code_pref.png
   :align: center	
   :height: 200pt
   :width: 350pt
  
* For manual account code, check the check-box. Tap on Confirm
  button. Remember, this is only one time activity.

How to start?
-------------

* We can start with ABT by creating minimum of two accounts. An
  organization can have any number of accounts.

How to create an account?
-------------------------

* Every account falls under a Group and optionally a Sub-Group. 

* All the Group names are available in Group name drop-down.

* If organization type is NGO, it will include Corpus in the list, or
  else Capital.
  
	+------------------------------+-------------------------------------+
	| Group	                       |    Sub-Group                        |
	+==============================+=====================================+
	|Capital/Corpus                |  - No subgroup                      |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+             
	|Current Asset                 |  - No subgroup                      |
	+		               +  - Bank                             +
	|                              |  - Cash                             |
	+                              +  - Inventory                        +
	|                              |  - Loan and Advance                 |
	+		               +  - Sundry Debtors                   +
	| 		               |  - Create new subgroup              |
	+------------------------------+-------------------------------------+
	|Current Liability             |  - No subgroup                      |
	+		               +  - Provision                        +
	|                              |  - Sundry Creditors for Expense     |
	+                              +  - Sundry Creditors for Purchase    +
	|                              |  - Loan abd Advance                 |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+
	|Direct Income                 |  - No subgroup                      |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+  
	|Direct Expense                |  - No subgroup                      |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+  
	|Fixed Assest                  |  - No subgroup                      |
	+		               +  - Building                         +
	|                              |  - Furniture                        |
	+                              +  - Land                             +
	|                              |  - Plant and Machinery              |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+
	|Indirect Income               |  - No subgroup                      |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+
	|Indirect Expense              |  - No subgroup                      |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+
	|Investment                    |  - No subgroup                      |
	+		               +  - Investment in Share & Debentures +
	|                              |  - Investment in Bank Deposits      |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+
	|Loans(Asset)                  |  - No subgroup                      |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+
	|Loans(Liability)              |  - No subgroup                      |
	+                              +  - Secured                          +
	|                              |  - Unsecured                        |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+
	|Reserves                      |  - No subgroup                      |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+
	|Miscellaneous Expense(Asset)  |  - No subgroup                      |
	+                              +  - Create new subgroup              +
	|                              |                                     |
	+------------------------------+-------------------------------------+
	
* The accounts will have Credit opening balance under groups:
  Corpus,Current Liability, Loans(Liability) & Reserves.
	
* The accounts will have Debit opening balance under groups: Current
  Asset, Fixed Assets, Loans (Asset), Investment, Miscellaneous
  Expenses(Asset)

* Accounts under groups: Direct Expense, Direct Income, Indirect
  Expense, Indirect Income will have zero opening balance.

* Depending upon the group, select required subgroup. We can add new
  subgroup if required.

* ABT suggests an account code, we can edit the suggested code or we
  can even leave it as it is.

* You need to enter opening balance if you are setting up a new
  business or its your first accounting package. Enter the
  Debit/Credit Opening Balance. The default value is 0.00. You can
  accept this or enter any positive or negative value.
	
* Last field displays the difference between debit and credit opening
  balances.
  
.. image:: images/create_account.png
   :align: center	
   :height: 200pt
   :width: 350pt
  
* After filling all the necessary data, save the details. 

* Total debit opening balance and Total credit opening balance depend
  on what type of opening balance you have entered.
	
* Note that Total debit opening balance, Total credit opening balance
  & Difference in opening balances are read-only fields.

* ``Finish`` button will take to the Master menu page. 
 
Example
-------
* Before passing any transaction we are now going to create an
  account (Cash on hand and Bank account).

* Go to Create account - Group name is **current asset**

* Subgroup is **cash**

* Account name could be **Cash on hand**

* Account code is an auto generation code which has to be specified at
  the time of creating an organization or user can give the account
  code.
  
* Opening balance can be mentioned during the time of create
  account. If you are setting up a new business and is your first
  accounting package you need to enter opening balance. If you are
  closing a previous book-keeping system, the closing position (Trial
  Balance) will be the Opening Balance.

* Likewise, for creating ``bank`` account group name is again
  ``current asset``, sub group is ``bank`` and the bank name would be
  the name of the bank.  An organization can have any number of
  banks. Finally, the bank balance will show the consolidated amount
  in the balance sheet.
	
  
Search/Edit account
-------------------
* Now what if we typed the name incorrectly? What do we do? Well,
  don't worry at all! We can edit as well as delete the accounts.
	
* When you tap on Edit Account tab, It displays the ``list`` of
  account names along with their opening balance. We can edit them as
  well as delete them.

.. image:: images/search_account.png
   :align: center
   :height: 200pt
   :width: 350pt

* If ``manual account code`` was checked, The account can be searched
  by either Account Name or Account Code. There is no provision of
  this dropdown if Manual Account Code option was not checked. Only
  Search by Account Name would have been visible.
	
* You can edit/delete only those accounts which are not under any
  transaction.

* Tap on Account name from the search results. 

* A prompt box appears which asks whether we want to Edit Account or
  Delete Account.
	
* Only Account name and Opening balance are editable. 

.. image:: images/edit_account.png
   :align: center	
   :height: 200pt
   :width: 350pt	
	  
* Save the changes.
	  
* **Note**: Opening balance field is not editable, if account comes
  under the following groups: ``Direct Income``, ``Direct Expense``,
  ``Indirect Income`` and ``Indirect Expense``. Opening balance can be
  ``0.00`` or negative values to change the debit and credit settings
  for eg. ``-100.00``.
	
* Press **Delete account** option to delete the account. Accounts
  under transaction cannot be deleted.
