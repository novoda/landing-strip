
# Demo video for each demo activity

![CustomLandingStrip](/demo-videos/CustomLandingStrip.gif?raw=true)

![CustomTabActivity](/demo-videos/CustomTabActivity.gif?raw=true)

![FixedWithTabActivity](/demo-videos/FixedWithTabActivity.gif?raw=true)

![MultipleListenersActivity](/demo-videos/MultipleListenersActivity.gif?raw=true)

![NoFragmentsSimpleTextActivity](/demo-videos/NoFragmentsSimpleTextActivity.gif?raw=true)

![SimpleTextTabActivity](/demo-videos/SimpleTextTabActivity.gif?raw=true)


### How to create video

1. run demo in aindroid emulator
2. use screen capture tool, like e.g. Camtasia Studio (free version)
3. convert to gif using ffmpeg

```
ffmpeg -i demo.avi  -r 12 -pix_fmt rgb24 demo.gif
```