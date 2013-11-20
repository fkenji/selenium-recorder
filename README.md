Selenium-recorder
===

This is a simple groovlet based project that provides basic controls for video recording on a single selenium-node





Todo
---

-> Simple way to run jar

-> Record videos through VLC?



\vlc\vlc.exe screen:// :screen-fps=4  :sout=#transcode{vcodec=h264,venc=x264{profile=baseline,level=3.0,nocabac,nobframes,ref=1},deinterlace,vb=200,scale=1}:file{dst='tmp\test_result_video.mp4'}



Internals:

    -> Destroy doesnt work because of fixed filepath

    -> Refactor RemoteSeleniumBackedTest
        -> Better way to handle selenium sessionIds
        -> Revise if ScreenshotTaker, GridNodeLocator and so forth need to receive params through constructor

    -> Create actual restful services
        -> Add capability into the video recording services to being able to detect whether it has been running for too long, ie > 10 minutes

    -> Fix tests with mocking?
