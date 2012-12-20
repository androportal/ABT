=================
Aakash Accounting
=================

**Aakash Accounting** is a portable accounting platform on Android
intended for Accountants and Students. `Aakash
<http://www.iitb.ac.in/aakash2/index.jsp>`_ is a low cost
computing device/tablet for students, the project is initiated and
funded by MHRD, Govt. of India. Aakash already runs Android 4.0 with
many educational apps developed at IIT Bombay. Please refer to
`androportal <https://github.com/androportal/>`_ for some of the
apps. Aakash Accounting provides an easy to use interface specially
for students/newbies who have just started accounting. We have tried
to make the user's experience simple and elegant. This initial
version covers basic account management, creating vouchers and
reports.


It was initially derived from `GNUkhata <gnukhata.org>`_, a free
accounting software which is mostly based on Python framework.

Some features included:
  #. Managing Organizations
  #. Maintaining books of accounts
  #. Recording, cloning and editing transactions
  #. Recording of transactions under particular project
  #. Generating reports such as Ledger Accounts, Trial Balance(Net, Gross, Extended), Profit and Loss Account, Project Statement, Cash Flow and Balance Sheet
  #. Bank Reconciliation


gkAakashCore - a backend to gkAakash
------------------------------------

The entire backend has Python code base. `gkAakashCore
<https://github.com/androportal/gkAakashCore>`_ provides a backend to
**gkAakash**. On Aakash tablet, it runs in a ``chroot``
environment(your device need to be rooted for this!). More information
can be found `here
<https://github.com/androportal/gkAakashCore/blob/master/README.rst>`_


Usage 
------

This `branch` contains an Android(4.0.3, API-15) code for the User Interface
of GNUkhata. 

User's can clone this repo by typing
::

   git clone -b ics https://github.com/androportal/gkAakash.git


if you want to clone code for Android(2.2, API-8), type
::

   git clone -b froyo https://github.com/androportal/gkAakash.git


*note: this branch is obsolete*

if you want to checkout all branches then type,
::

   git checkout -b ics remotes/origin/ics
   git checkout -b docs remotes/origin/docs
   git checkout -b froyo remotes/origin/froyo   

Importing gkAakash as an `eclipse <http://www.eclipse.org/>`_ project
---------------------------------------------------------------------
`Note`: we have used Eclipse version 3.7.2(Indigo)
 
- For setting up Android SDK and AVD, please visit this `link
  <http://developer.android.com/sdk/installing/index.html>`_
- You need to configure `ADT
  <http://developer.android.com/tools/sdk/eclipse-adt.html>`_ plugin
  on eclipse to work on Android code. Please visit `Installing ADT
  <http://developer.android.com/sdk/installing/installing-adt.html>`_
  for detail installation instructions.
  
 
After cloning the require branch, start eclipse

- go-to ``File`` menu -> ``Import``
- from the ``Import`` dialog box, select ``Android``
- from ``Android`` section, select ``Existing Android Code Into
  Workspace`` and click ``Next`` button.
- you will be taken to ``Import Projects`` dialog box, click ``Browse``
  button and select the cloned repository

Important note
--------------

- to test gkAakash on tablet
- go to
  ``gkAakash/src/com/gkaakash/coreconection/CoreConnection.java`` and
  change the url from ``"http://10.0.2.2:7081"`` to
  ``"http://127.0.0.1:7081"``

Documentation
-------------

More documentation in raw `sphinx <http://sphinx.pocoo.org/>`_ format
can be found at 

::

   git clone -b docs https://github.com/androportal/gkAakash.git

please read the ``README.rst`` on how to generate html docs


License
-------

GNU GPL Version 3, 29 June 2007.

Please refer this `link <http://www.gnu.org/licenses/gpl-3.0.txt>`_
for detailed description.

All rights belong to the National Mission on
Education through ICT, MHRD, Government of India.
