Selenium-recorder
===

This is a simple groovlet based project that provides basic controls for video recording on a single selenium-node





Todo
---

-> Simple way to run jar

-> Record videos through VLC?



\vlc\vlc.exe screen:// :screen-fps=4  :sout=#transcode{vcodec=h264,venc=x264{profile=baseline,level=3.0,nocabac,nobframes,ref=1},deinterlace,vb=200,scale=1}:file{dst='tmp\test_result_video.mp4'}
