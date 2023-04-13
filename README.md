# Assignment 3

## Build Steps

### Gradle

Gradle does not need to be installed on your computer. It will run slow the first time.

In the root directory run:<br>
Linux/MacOS: `./gradlew run` <br>
Windows: `.\gradlew.bat run`

### VS Code

If gradle is not working for some reason then you can run the project in vscode with the recommended java extensions installed. The user input steps are the same.

## Problem 1 Summary

### Strategy

I implemented the lock free linked list from the textbook for the problem. The can be accessed by multiple threads concurrently which solves the problem that the servants were having with a standard linked list. Each servant (thread) inserts presents into the list and then removes them after creating a thank you card. I kept track of the the thank you card count using an AtomicInteger which is shared between all of the servents. There is a 30% chance that the Minotaur will ask one of the servants to check if a certain item is in the list. After 500,000 thank you cards have been written, the program stops.

## Problem 2

I did not complete it
