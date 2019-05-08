# Android-Slices-Demo

### This project explains how to use Android Jetpack to create Slices.

Topic covered: 

1.  __Basic slices__ shared data through slices with activity

2.  __Advance Slices__ : 
  - Live List 
  - Grid 
  - Header 
  - Toggle Switches 
  - Multiple Actions passing 
  - Live Slider 
  - Live Progress Bar & 
  - Complex Slices

Follow the steps to get started with slices from blog: http://hiteshsahu.com/blogs/android_slices

# Android Slices Tutorial


>  ### Continuing in last post here I gone explain how to create Slices for your application. If you have not read it please go through my previous post [Understanding Slices](http://hiteshsahu.com/blogs/android_slices) to get your setup ready.


## First Get your setup ready

You need to install [__Slice Viewer__](https://github.com/googlesamples/android-SliceViewer/releases) apk to view your slices. Download latest relase of slice viewer apk from [https://github.com/googlesamples/android-SliceViewer/releases](https://github.com/googlesamples/android-SliceViewer/releases) & install it using command:

> `adb install -r -t slice-viewer.apk`

where :
 - -r: replaces existing application
 - -t: allow test packages


Now you can view your Slice by running the following command:

> `adb shell am start -a android.intent.action.VIEW -d slice-content://com.hiteshsahu.slicedemo/`

It is pretty much like loading different pages of a website by changing URLS

For example:
 - __content://com.hiteshsahu.slicedemo/__       : will gone load home slice
 - __content://com.hiteshsahu.slicedemo/about__   : will gone load about slice
 - __content://com.hiteshsahu.slicedemo/contact__ : will gone load contact slice

and so on see the result below.

####  Every time you gone launch a new slice with this adb command then that slice will be automatically get added in Slice Viewer demo app. Which you can see any time later. 

 Same can be achieve with the help of Android studio but I prefer command line tool. COmmand line tool is less complicate and less overhead.  
 
 
 ### Uninstall sliceviewer
 
 If you have uninstalled _sliceviewer.apk_ by drag and drop in Launcher app  then chances are you might no longer will be able to install it back via adb command. This is a weird bug I found. In that case you will need to uninstall sliceviewer using this command. 
 
     adb  uninstall com.example.android.sliceviewer

## Building Custom Slices


As already described in my previous post [Understanding SLices](http://hiteshsahu.com/blogs/android_slices) you can use Android Studio to create Slices as shown in pic below:

<img src="http://hiteshsahu.com/assets/img/blogs/slices/create_slice.png" />


## Steps to build slices

1. Install SliceViewer APK:

>      adb install -r -t slice-viewer.apk
    
2. Download, Build, Install & Launch this project.


<p align="center">
<img src="https://github.com/hiteshsahu/Android-Slices-Demo/blob/master/art/home.png" width="80%">
</p>

3. Execute this command from adb termainal:


>       adb shell am start -a android.intent.action.VIEW -d slice-<uri_from_below>
 
 For example to Launch Home screen from Slice execute. Note the "-" after slice.

>       adb shell am start -a android.intent.action.VIEW -d slice-content://com.hiteshsahu.slicedemo/


<p align="center">
<img src="https://github.com/hiteshsahu/Android-Slices-Demo/blob/master/art/slice_home.png" width="80%">
</p>


### Basic SLices

`BasicSliceProvider` class contain example of how we can create aslice and use Peending Intent to  send data to  the App

Similarly replace <uri_from_below> with this uris to launch Slice mapped to screens. 

- home

          content://com.hiteshsahu.slicedemo/ 
         
- about

         content://com.hiteshsahu.slicedemo/about 
         
- contact    

          content://com.hiteshsahu.slicedemo/contact  
          
- ERROR 404  

          content://com.hiteshsahu.slicedemo/etc : 
          
<p align="center">
<img src="https://github.com/hiteshsahu/Android-Slices-Demo/blob/master/art/basic_usage.png" width="80%">
</p>
          
          
### Advance Slices

 Use this uri to launch complex slices with live data

- __SLICE WITH HEADER__ : this uri launch a Slice with a header and 3 custom Actions. Each action can be send to App with the help of Pending Intents. Click on Actions to see how App handle them.  

          content://com.hiteshsahu.slicedemo.advance/ 
         
-  __SLICE WITH TOGGLE__ Show how to use toggle switch to turn on/off Wifi

         content://com.hiteshsahu.slicedemo.advance/toggle 
         
 <p align="center">
<img src="https://github.com/hiteshsahu/Android-Slices-Demo/blob/master/art/adv1.png" width="80%">
</p>
       
         
-  __SLICE WITH RANGE SLIDER & PROGRESS BAR__ Show how to show progress bar and Slider in Slices & control Media Volume of the device 

          content://com.hiteshsahu.slicedemo.advance/slider  
          
          
<p align="center">
<img src="https://github.com/hiteshsahu/Android-Slices-Demo/blob/master/art/slider.png" width="80%">
</p>
          
-  __SLICE WITH GRID__ Show how to show Grid of Images in Slices

          content://com.hiteshsahu.slicedemo.advance/grid  
          
          
<p align="center">
<img src="https://github.com/hiteshsahu/Android-Slices-Demo/blob/master/art/grid.png" width="80%">
</p>
       
-  __SLICE WITH WIFI LIST__ Show how to show List of Wifi along with show more option. 

          content://com.hiteshsahu.slicedemo.advance/list  
          
<p align="center">
<img src="https://github.com/hiteshsahu/Android-Slices-Demo/blob/master/art/wifi.png" width="80%">
</p>
          
                 
-  __COMPLEX SLICE__: Complex slice with more than one grid and row

            content://com.hiteshsahu.slicedemo.advance/combine  
         
         
<p align="center">
<img src="https://github.com/hiteshsahu/Android-Slices-Demo/blob/master/art/combine.png" width="100%">
</p>
          
          
