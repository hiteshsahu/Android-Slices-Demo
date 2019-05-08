# Android-Slices-Demo

This project explains how to use Android Jetpack to create Slices.

Follow the steps to get started with slices http://hiteshsahu.com/blogs/android_slices

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
<img src="https://github.com/hiteshsahu/Android-Slices-Demo/blob/master/art/combine.png" width="100%"
     height="400%>
</p>
          
          
