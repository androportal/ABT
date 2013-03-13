.. toctree::
   :numbered:

======================
Transaction management
======================
* This chapter provides guidence to ``record/search/edit/clone`` transaction.

Record Transaction
------------------
To record transaction in ``ABT`` follow the below step:

* Go to ``Master menu`` > ``Create Voucher`` and select the mode of transaction from the list of voucher types.

* ``Mode`` of transaction can be Contra, Journal, Payment, Receipt, Debit Note, Credit Note,Sales, 
  Sales Return, Purchase, Purchase Return.
	
.. image:: images/voucher_type.png
   :name: ABT main page
   :align: center
   :height: 200pt
   :width: 350pt
 
* See ``Create Voucher`` tab as shown in below fig. for recording transactions.

.. image:: images/create_voucher.png
   :name: ABT main page
   :align: center
   :height: 200pt
   :width: 350pt

There should be atleast ``two`` accounts for recording transactions. To get started with,

* Select account ``Type(Dr/Cr)``, ``Account name`` from the dropdown and enter the ``Amount`` in rupees
  and repeat the same until amount gets tallied.
  
* **Note**: ABT populates drop down of account name by voucher ``rule``. 
  For example, if transaction type is contra, it filters account names that 
  comes under contra and fill them in the drop down.
     
* Press ``Voucher date`` to set the date of transaction.

* **Note**: ABT sets financial date as Voucher date, if no transaction is recorded before. 
  If any transaction is previously recorded,it sets the voucher date of the previous transaction 
  of the respective voucher type. If the date is changed, it updates the bydefault date or previous 
  date with the new date. 

* Press ``Project name`` to select the project name from list of projects for recording transaction 
  under that particular project, otherwise select ``No project``.

* Enter ``Voucher reference No.`` or edit the last reference No. .

* Enter ``Narration`` if any(optional).

* Use ``plus`` button for adding new row.

* Press ``Save`` to save transaction and ``Reset`` to clear all fields.

* After saving transaction it resets all the fields automatically.

* ``Home`` button shown below , present on title bar will take to the ``Master menu`` page.

.. image:: images/home.png
   :name: ABT main page
   :align: center
 
Search/Edit/Clone/Delete Transaction
------------------------------------

* To ``Edit/Clone/Delete`` transaction select ``Search voucher`` tab.

* Its displays ``all`` transactions for the financial year as shown in fig..

.. image:: images/search_voucher.png
   :name: ABT main page
   :align: center
   :height: 200pt
   :width: 350pt

* Press ``Search`` button (see fig ) to search transaction by ``Voucher reference No.,Date and Narration`` 
  and press ``View`` to view transactions.


.. image:: images/search_voucher_by.png
	   :name: ABT main page
	   :align: center
	   :height: 200pt
   	   :width: 350pt

* Click table ``row`` to Edit/Clone/Delete the transactions.

* In **Edit voucher**, except ``Reference No.``, all other fields are editable. Press ``Save`` to save the changes. 

* **Clone voucher** duplicates information on an existing transaction, to create a new one without having to enter all the fields. There is an option to ``keep or change`` the existing field values. Press ``Save`` to save the transaction. 

* **Delete voucher**: Press ``Delete`` to delete the transaction.


