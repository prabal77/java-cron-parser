# deliveroo-cron

## Objective: 
Custom cron parser, which satisfies the problem statement

>Write a command line application or script which parses a cron string and expands each field
>to show the times at which it will run.
>You should only consider the standard cron format with five time fields (minute, hour, day of
>month, month, and day of week) plus a command, and you do not need to handle the special
>time strings such as "@yearly".<br>
>The input will be on a single line.<br>
>The cron string will be passed to your application as a single argument.<br>
>~$ your-program ＂*/15 0 1,15 * 1-5 /usr/bin/find＂<br>
>The output should be formatted as a table with the field name taking the first 14 columns and
>the times as a space-separated list following it.

Application handles only the standard cron syntax format, as defined here https://www.ibm.com/docs/en/db2oc?topic=task-unix-cron-format <br>
Can also use https://crontab.guru/ to validate the cron expression.<br>

<p>
<b>
Version 1.0 can be found in bin/<br> 
jacoco-test coverage reports can be found under <br>
</b>
</p>

## How to run
<code>$java -jar bin/app.jar "$your input$" </code><br>
Input should be in format: <br/>
"<b>$minute</b> <b>$hour</b> <b>$day_of_month</b> <b>$month</b> <b>$day_of_week</b> <b>$command_to_execute</b>"
<br/>e.g. */15 0 1,15 * 1-5 /usr/bin/find

## Output
```bash
$ java -jar deliveroo-cron-parser.jar "0 1-3 1-3,15 * tue-fri/tue /usr/bin/find"
  minute        0
  hour          0 1 2 3
  day of month  1 2 3 15
  month         1 2 3 4 5 6 7 8 9 10 11 12
  day of week   2 4
  command       /usr/bin/find
```

## How to build
Application uses gradle to build. gradle wrapper is already added to the repo
<code>./gradlew clean build</code> <br>
The build artifacts are present under app/build/ folder
