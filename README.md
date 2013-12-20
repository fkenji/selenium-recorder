Selenium-recorder
===

This is a simple prototype of a groovlet-based project that provides basic controls for video recording of JUnit-based integration tests on a single selenium-node.

The way the it is implemented is by deploying to each selenium-grid node a webservice that records the actual screen.
By hooking up into JUnit rules, we're able to query the selenium-grid hub as to where the actual test is running on, allowing us to tell the video recording webservice when it should start and stop.

After testing is done, we download the created video and save it as a surefire-reports folder.


Todo
---

-> Simple way to run jar

-> Record videos through VLC


Internals:

    -> Destroy doesnt work because of fixed filepath

    -> Refactor RemoteSeleniumBackedTest
        -> Better way to handle selenium sessionIds
        -> Revise if ScreenshotTaker, GridNodeLocator and so forth need to receive params through constructor

    -> Create actual restful services
        -> Add capability into the video recording services to being able to detect whether it has been running for too long, ie > 10 minutes

    -> Fix tests with mocking?
