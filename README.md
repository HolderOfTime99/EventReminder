# Event Reminder

A daily news and event reminder that sends a email with queried information. 

### Prerequisites

Program requires installation of Java 11 and Python3 with libraries that can be installed with:

```
pip install xlrd
pip install simplejson
```



### Getting Started

Begin by cloning the repo:

```
git clone https://github.com/HolderOfTime99/EventReminder.git
```

Navigate to Events.xlsx and News.xlsx in the ExcelSheets folder. Each sheet in the excel files corresponds with one 
query with the name of the parameter in the first column and value in the second. Edit the given files for 
desired queries. 

List of parameters can be found at:
* [Eventful](https://api.eventful.com/docs/events/search)
* [News](https://newsapi.org/docs/endpoints/top-headlines)

Next, to automate the script to run in the background of your computer:

#### Mac Users:

Edit [local.eventreminder.automator.plist](local.eventreminder.automator.plist) by replacing:
 
 * /PATH/TO/autoemailer.sh
 * /PATH/TO/WORKING_DIRECTORY/
 
 Additionally, values under StartCalendarInterval can be changed to select time that the script runs.
 By default, queries are ran at 5AM.
 
 Lastly, move the file to LaunchDaemons and schedule the task:
 
```
mv local.eventreminder.automator.plist /Library/LaunchDaemons/
launchctl load -w /Library/LaunchDaemons/local.eventreminder.automator.plist
```

### Windows Users:

Edit [scheduleEventReminderWindows.ps1](scheduleEventReminderWindows.ps1) by replacing:

* PATH\TO\automateEventReminderWindows.ps1
* PATH\TO\WORKING_DIRECTORY\
* DESIRED_RUN_TIME

Note: DESIRED_RUN_TIME must be replaced with valid DateTime format such as:
```
$trigger = New-ScheduledTaskTrigger -Daily -At 8:30am
```

Finally, run this script in a PowerShell session from the working directory to schedule the task:

```
.\scheduleEventReminderWindows.ps1
```
## Creators

**Sam Berensohn**

- <https://github.com/sberen>

**Zizhen Song**

- <https://github.com/HolderOfTime99>

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

