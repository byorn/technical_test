Technical Test for: https://gist.github.com/codingricky/2913880
--------------------------------------------------------------------

Prerequisites
-----------------------------
Install Java 8, and Gradle

How To Execute the Test Cases
------------------------------
gradle build
gradle test

/build/reports/tests/test/index.html will output the test results.


Addressing Pricing Rules Flexibility
--------------------------------------------------
+ The idea is that a PricingRule can be added to the PricingRulesBO Business Object Class
  (Thinking of scalability, where PricingRule can be stored in a database)

+ Pricing Rules Logic are separated into a separate package. If business rules changes, the developer can be concerned only within the pricing rules package.

