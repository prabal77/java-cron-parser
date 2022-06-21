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
Version 1.0 binary can be found in <i>bin/</i><br> 
jacoco-test coverage reports can be found under <i>jacoco-report</i> <br>
javadocs coverage reports can be found under <i>javadoc</i> <br>
</b>
</p>

## How to run
<code>$java -jar bin/cron.jar "$your input$" </code><br>
Input should be in format: <br/>
"<i>$minute</i> <i>$hour</i> <i>$day_of_month</i> <i>$month</i> <i>$day_of_week</i> <i>$command_to_execute</i>"
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
<br><code>deliveroo-cron$: ./gradlew clean build jacocoTestReport javadoc</code> </r><br>
<br><i>jacocoTestReport and javadoc targets are optional</i></br>
<br>
The build artifacts are present under cron/build/ folder
</br>

## Parsing logic used
<b><i>Also mentioned in the javadoc</i></b>

<br>Input string is split along "," into multiple input fragments.
<br>Followed by parsing each fragment and returning a merged list of valid values extracted from those input fragments (distinct values).
<p>
    <br>Each input fragment is parsed based on whether it supports numeric only value (minute, hour and day of month) or alphanumeric (month and day of week).
    <br>Uses Regular expressions (refer RegexExpressions) to match and extract parts from input fragments.
    <br>"Word" inputs e.g. jan, feb, sun, mon (case in-sensitive) are converted into equivalent integers (refer NameToIntegerMap) and treated as integer values.
    <br>Supports various combinations e.g.
    <ul>
        <li>single integer. e.g. 12, 1</li>
        <li>single word. e.g. jan, feb, sun, MON</li>
        <li>integer range e.g. 2-10, 40-59</li>
        <li>word range. e.g. jan-oct, mon-fri</li>
        <li>alphanumeric range. e.g. jan-5, 2-thu</li>
        <li>step functions with alphanumeric step value field and all above mentioned combinations in range field. e.g. '*'/4, 2-5/jan, jan-oct/3, feb/oct</li>
    </ul>
