![PatternLockView](https://github.com/Lisdong/PatternLockView/blob/master/screenshots/Screenshot_2019-05-07-15-48-32-45.png)
![PatternLockView](https://github.com/Lisdong/PatternLockView/blob/master/screenshots/Screenshot_2019-05-07-15-48-08-96.png)
# PatternLockView

### Step 1

Place the view in your XML layout file.

```xml
    <com.andrognito.patternlockview.PatternLockView
        android:id="@+id/pattern_lock_view"
        android:layout_width="280dp"
        android:layout_height="280dp"/>
```

This is enough to get the view rendered in your layout. But you would certainly want to add a callback listener to listen to pattern changes.

### Step 2

Reference the view in code and add a listener to it.

```java
mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
mPatternLockView.addPatternLockListener(mPatternLockViewListener);
```

Implement the listener interface as follows,

```java
private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };
```

And that's it! Your PatternLockView is ready to rock. You might also want to remove the listeners when not needed,         `removePatternLockListener(mPatternLockViewListener);`


### Step 3 (Optional: ReactiveX Interface)

For the RxJava fanboys, this library supports RxJava 2 view bindings. You can subscribe to this view to get a stream of pattern change updates.

```java
RxPatternLockView.patternChanges(mPatternLockView)
                .subscribe(new Consumer<PatternLockCompoundEvent>() {
                    @Override
                    public void accept(PatternLockCompoundEvent event) throws Exception {
                        if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_STARTED) {
                            Log.d(getClass().getName(), "Pattern drawing started");
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_PROGRESS) {
                            Log.d(getClass().getName(), "Pattern progress: " +
                                    PatternLockUtils.patternToString(mPatternLockView, event.getPattern()));
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                            Log.d(getClass().getName(), "Pattern complete: " +
                                    PatternLockUtils.patternToString(mPatternLockView, event.getPattern()));
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_CLEARED) {
                            Log.d(getClass().getName(), "Pattern has been cleared");
                        }
                    }
                });
```

If you are not interested in getting the compound event, you should subscribe to `patternComplete()` and/or `patternProgress()` for the specific updates. Have a detailed look [here](https://github.com/aritraroy/PatternLockView/blob/master/patternlockview-rxadapter/src/main/java/com/andrognito/rxpatternlockview/RxPatternLockView.java).

# Customization

There are several customization options available which you can use to completely change the look-and-feel and functionality of this view to match your needs.

### XML (Quick and Easy)

You can add various attributes to the PatternLockView from your XML layout.

```xml
  app:dotCount="3"                                        // Change the no.of dots in a row (or column)
  app:dotNormalSize="12dp"                                // Change the size of the dots in normal state
  app:dotSelectedSize="24dp"                              // Change the size of the dots in selected state
  app:pathWidth="4dp"                                     // Change the width of the path
  app:aspectRatioEnabled="true"                           // Set if the view should respect custom aspect ratio
  app:aspectRatio="square"                                // Set between "square", "width_bias", "height_bias"
  app:normalStateColor="@color/white"                     // Set the color of the pattern view in normal state
  app:correctStateColor="@color/primary"                  // Set the color of the pattern view in correct state
  app:wrongStateColor="@color/pomegranate"                // Set the color of the pattern view in error state     
  app:dotAnimationDuration="200"                          // Change the duration of the animating dots
  app:pathEndAnimationDuration="100"                      // Change the duration of the path end animaiton
```

### JAVA (Programatically)

You can also programatically change the properties of the view, thereby having more control over it.

```java
mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);       // Set the current viee more 
mPatternLockView.setInStealthMode(true);                                     // Set the pattern in stealth mode (pattern drawing is hidden)
mPatternLockView.setTactileFeedbackEnabled(true);                            // Enables vibration feedback when the pattern is drawn
mPatternLockView.setInputEnabled(false);                                     // Disables any input from the pattern lock view completely

mPatternLockView.setDotCount(3);
mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
mPatternLockView.setAspectRatioEnabled(true);
mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS); 
mPatternLockView.setNormalStateColor(ResourceUtils.getColor(this, R.color.white));
mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.primary));
mPatternLockView.setWrongStateColor(ResourceUtils.getColor(this, R.color.pomegranate));
mPatternLockView.setDotAnimationDuration(150);
mPatternLockView.setPathEndAnimationDuration(100);

```

