# landing-strip [![](https://ci.novoda.com/buildStatus/icon?job=landing-strip)](https://ci.novoda.com/job/landing-strip/lastBuild/console) [![Download](https://api.bintray.com/packages/novoda/maven/landing-strip/images/download.svg) ](https://bintray.com/novoda/maven/landing-strip/_latestVersion) [![Apache 2.0 Licence](https://img.shields.io/github/license/novoda/landing-strip.svg)](https://github.com/novoda/landing-strip/blob/master/LICENSE)

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
  android:layout_width="match_parent" />
```

A `TabAdapter` implementation to set the tab titles from `PagerAdapter.getPageTitle`

```java
static class TitledAdapter extends TabAdapter<TextView> {

    private final ViewPager viewPager;

    public TitledAdapter(LayoutInflater layoutInflater, int itemLayoutId, LandingStrip landingStrip, ViewPager viewPager) {
        super(layoutInflater, itemLayoutId, landingStrip, viewPager);
        this.viewPager = viewPager;
    }

    @Override
    protected void bindView(TextView view, int position) {
        super.bindView(view, position);
        bindPageTitleToView(view, position);
    }

    private void bindPageTitleToView(TextView itemView, int position) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            return;
        }
        itemView.setText(adapter.getPageTitle(position));
    }

}
```

Attach the `TabAdapter` to the `LandingStrip` and the `LandingStrip` to the `ViewPager`.

```java
LandingStrip landingStrip = (LandingStrip) findViewById(R.id.landing_strip);
TabAdapter tabAdapter = new TitledAdapter(
        getLayoutInflater(),
        R.layout.tab_simple_text,
        landingStrip,
        viewPager
);
landingStrip.setAdapter(tabAdapter);
viewPager.addOnPageChangeListener(landingStrip);
```

More info on the available properties and other usages in the [Github Wiki](https://github.com/novoda/landing-strip/wiki).


## Links

Here are a list of useful links:

 * We always welcome people to contribute new features or bug fixes, [here is how](https://github.com/novoda/novoda/blob/master/CONTRIBUTING.md)
 * If you have a problem check the [Issues Page](https://github.com/novoda/landing-strip/issues) first to see if we are working on it
 * For further usage or to delve more deeply checkout the [Project Wiki](https://github.com/novoda/landing-strip/wiki)
 * Looking for community help, browse the already asked [Stack Overflow Questions](http://stackoverflow.com/questions/tagged/support-landing-strip) or use the tag: `support-landing-strip` when posting a new question  
