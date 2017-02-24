# landing-strip [![](https://ci.novoda.com/buildStatus/icon?job=landing-strip)](https://ci.novoda.com/job/landing-strip/lastBuild/console) [![Download](https://api.bintray.com/packages/novoda/maven/landing-strip/images/download.svg) ](https://bintray.com/novoda/maven/landing-strip/_latestVersion) [![](https://raw.githubusercontent.com/novoda/novoda/master/assets/btn_apache_lisence.png)](LICENSE.txt)

Your simple sliding viewpager tab strip: a landing strip without the fluff!

## Description

This is a tab strip born from other tabs strips, the key difference it **YOU** the client do all the hardwork. The library will handle state, lifecycle callbacks and the correct selection states.
The client will provide the layout to inflate, handling the view updating, the different selection states.


## Demo

![FixedWithTabActivity](/demo-videos/FixedWithTabActivity.gif?raw=true)

[more demos ...](/demo-videos/README.md)


## Adding to your project

To start using this library, add these lines to the `build.gradle` of your project:

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.novoda:landing-strip:<latest-version>'
}
```


## Simple usage

The tab item layout

```xml
R.layout.tab_simple_text

<TextView
  android:layout_width="wrap_content"
  android:layout_height="match_parent"
  android:textSize="18dp"
  android:gravity="center" />
```

The tab strip which will contain the tab items

```xml
<com.novoda.landingstrip.LandingStrip
  android:id="@+id/landing_strip"
  android:layout_height="50dp"
  android:layout_width="match_parent"
  android:background="@android:color/holo_orange_dark"
  app:tabLayoutId="@layout/tab_simple_text" />
```

Attaching the `ViewPager` to the `LandingStrip`

```java
LandingStrip landingStrip = (LandingStrip) findViewById(R.id.landing_strip);
landingStrip.setViewPager(viewPager, viewPager.getAdapter());
```

More info on the available properties and other usages in the [Github Wiki](https://github.com/novoda/landing-strip/wiki).


## Links

Here are a list of useful links:
 * Demo Video Link of Landing Strip : [Click Here To Watch](http://bit.ly/landingstripdemo) 
 * We always welcome people to contribute new features or bug fixes, [here is how](https://github.com/novoda/novoda/blob/master/CONTRIBUTING.md)
 * If you have a problem check the [Issues Page](https://github.com/novoda/landing-strip/issues) first to see if we are working on it
 * For further usage or to delve more deeply checkout the [Project Wiki](https://github.com/novoda/landing-strip/wiki)
 * Looking for community help, browse the already asked [Stack Overflow Questions](http://stackoverflow.com/questions/tagged/support-landing-strip) or use the tag: `support-landing-strip` when posting a new question  
