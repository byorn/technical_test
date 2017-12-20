Technical Test for: https://gist.github.com/codingricky/2913880
--------------------------------------------------------------------

Build And Run Test Cases
------------------------------
1) Prerequisite: Java 8, and Gradle Install
2) Checkout the Project source <br/>
     + c:\technical_test>gradle build <br/>
     + c:\technical_test>gradle test  <br/>
     
     
3) Test output can be found in: <br/>
/build/reports/tests/test/index.html will output the test results.


Addressing Pricing Rules Flexibility
--------------------------------------------------
+ The idea is that a PricingRule can be added to the PricingRulesBO Business Object Class
  (Thinking of scalability, where PricingRule can be stored in a database)
  
+ The PricingRule object is defined to hold the various logics of the pricing rules. The PricingRulesBO class uses these objects to calculate the totals of the checked out items.

+ Pricing Rules Logic are separated into a separate package. If business rules changes, the developer can be concerned only within the pricing rules package.


Please note:
----------------------------------------------------
My Solution focuses on the Checkout Total value calculation only. As this is what is questioned in the assignment.

It does not focus on the checked out items breakdown display - The Bill.

The reason I state this is because: For the mentioned scenario:

"we will bundle in a free VGA adapter free of charge with every MacBook Pro sold"

When a Macbook Pro is scanned, bundling the free VGA adapter may not require the VGA Adapter to be scanned as well.
<ol>
<li> If the VGA Adapter was scanned, it will be free</li>
<li>If the VGA Adapter was not scanned - Total not effected, and checkout items will not be affected</li>
<li> If an additional VGA Adapter is scanned..eg. 1 MBP and 2 VGA, then the additional VGA will be charged.</li>
</ol>

Pricing Rule Defined
-------------------------------------------------------
There are two types of PricingRules
1) EXCEEDS_THRESHOLD
2) FREE_ITEM

EXCEEDS_THRESHOLD - When the total price  of an individual scanned item exceeds the configured pricing rule threshold amount, then apply the discount
to <br/>
<ul>
        <li> To the Full item price total </li>
       <li> To each individual item's price</li>
</ul>


FREE_ITEM - When The scanned item belongs to this pricing rule the discount  (i,e. should be the full price of the item) will be applied  if another item (configured) is existing


Futher Improvemants
-------------------------------------------------------------
The PricingRule class can be further simplified, with inheritance and using Strategy Design Pattern (using interfaces)