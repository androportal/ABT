====
TODO 
====

if any list is empty
add toast for unimplemented method
diff in dr/cr in trial bal
ledger with narrations(checkbox)
1st create acc back button

* hint for dr cr amount instead of 0.00 and autocomplete  second row amount

preference layout
validate project in preferences page before addding new project
validate financial year
add project in menu page
detete org
search voucher
spaces

* capitalize all names

validate voucher for empty acc list
reset for multiple dr cr rows
ledger for multiple drcr accounts in transaction
voucher on from=to date are not showing
acc tab grapic
fix floating heading for ledger
trim funtion for all name fields

* logout button on each page

org name and fin year on each page
stretch rows in table
on tab change page should be updated(account and voucher page)
total account count in search account
Loading getall voucher and account list need to be fixed on tab change
If no changes are made alert need to be added
Spinner width need to fixed
For edit voucher,reference edittext should be non-editable
save org details creates problem to get org type if org details are
not saved(skiped)

* total amount in bold and floating

===========================================================
when directly clicked on credit note transaction, it shows all bank
and cash accounts in Dr and Cr drop down and sometimes force quit
soln: in voucherMenu:getAccountByRule, compare vochertype by
“equalsIgnoreCase” instead of just “equals”
===========================================================
for multiple dr cr, recongrid is incorrect in bank recon

-    needed to add preticularRow in [ ] in update bank recon
-    title for trial balance types and  balance sheet type
-    when account name is edited, it is affecting bankrecon table

proper format to save accountnames in db
bug while cloning voucher
date wise bank recon
