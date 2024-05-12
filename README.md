# colorpicker
A color picker module for Android

## Download
Gradle implemetation
```gradle
implementation 'dev.ohjiho:colorpicker:1.0.0'
```

## Methods

```kotlin
fun setColumnAmount(amount: Int) // Sets the number of columns per row
fun setColors(resID: Int) // Sets the colors using the ID of an array resource containing ColorInts
fun setColors(vararg colorInts: Int) // Sets the colors using a variable argument of ColorInts
fun createView(): FrameLayout // Returns the view (FrameLayout) containing the color picker
```
