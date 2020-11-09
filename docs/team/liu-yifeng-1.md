# LIU YIFENG - Project Portfolio Page

## Overview
Our product, **_25HoursADay_** is a scheduling application catered for NUS students. 
It is optimised for use via the Command Line Interface (CLI) and it serves as an efficient one stop application for our users to manage their time. 

## Summary of Contributions
### Code contributed:
For code contribution, please visit this [website](https://nus-cs2113-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=liu-yifeng-1&sort=groupTitle&sortWithin=title&since=2020-09-27&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=LIU-YiFeng-1&tabRepo=AY2021S1-CS2113T-T12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other) 
for more details.

### Enhancements to new features:
* Added ability to print all tasks and all events separately. (`print events` and `print tasks`)
 ([\#10](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/10))
  * What it does: It allows the user to have an easy overview of all the events and tasks stored in the program.
  * Justification: It allows the user to retrieve the index number for a particular event or task quickly. So that the user can 
                    perform other operations (such as deletion, mark as done, adding additional information and viewing additional information etc) easily.
  * Highlights: It displays all tasks and events with additional information if there is any.
  
 * Integrated a third party library (fastjson) to the project and added the ability to verify the module code entered by the user.
  ([\#126](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/126),
  [\#246](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/246),
  [\#270](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/270)
  )
   * What it does: It checks the validity of the module code entered by the user against the NUSMod API.
   If valid, program will proceed as per normal. Else, it will prompt the user with an error message.
   * Justification: As a task scheduling application, it is crucial for the application to provide some sanity check to ensure the 
   user entered the correct information.
   * Highlights: This feature is functional with or without the Internet.
  
<br/>

### Enhancement to existing features:
* Shorten and Standardised the user command input format to make the code more readable.
  ([\#56](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/56))
* Enabled school events (such as Lecture, Tutorial and Lab session) to be added recursively.
  ([\#79](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/79))
* Update welcome Logo and Chat box to suit the team name.
  ([\#122](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/122))
* Enabled warning message for over-due deadline items.
  ([\#240](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/240),
  [\#288](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/288)
  )
* Wrote Junit tests to increase coverage.
  ([\#124](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/124))
  
### Contributions to the DG:
* Added the Setting Up & Getting started section.
  ([\#148](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/148),
  [\#149](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/149)
)
* Added the DevOps guide, Non-functional Requirements, Product Scope and User Stories.
* Added implementation and sequence diagram for `print events` and `print tasks`.
  ([\#164](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/149))
* Added implementation sequence diagram for ModuleChecker.
  ([\#185](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/185),
  [\#284](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/284)
  )

### Contributions to the UG:
* Drafted UG based on what we had for CS2101 and documented the summary of command section.
  ([\#107](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/107))
* Updated the UG with expected output screenshots.
  ([\#172](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/172),
  [\#175](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/175),
  [\#177](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/177),
  [\#230](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/231),
  [\#231](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/231),
  [\#296](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/296)
  )

### Contributions to team-based tasks:
* Reviewed others' PR and provided feedback.
  ([\#20](https://github.com/AY2021S1-CS2113T-T12-2/tp/pull/20))
* Updated both the user and developer guides that are not specific to my features and enhancements.

### Contributions beyond the project team:
* Reported bugs in other team's project during mock PE.
  ([\#1](https://github.com/LIU-YiFeng-1/ped/issues/1),
  [\#2](https://github.com/LIU-YiFeng-1/ped/issues/2),
  [\#3](https://github.com/LIU-YiFeng-1/ped/issues/3),
  [\#4](https://github.com/LIU-YiFeng-1/ped/issues/4),
  [\#5](https://github.com/LIU-YiFeng-1/ped/issues/5),
  [\#6](https://github.com/LIU-YiFeng-1/ped/issues/6),
  [\#7](https://github.com/LIU-YiFeng-1/ped/issues/7)
  )
* Shared useful external library on the module Forum.
([\#107](https://github.com/nus-cs2113-AY2021S1/forum/issues/107))
