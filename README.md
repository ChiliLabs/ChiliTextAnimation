# ChiliTextAnimation

Made with ❤️ by [Chili Labs](https://chililabs.io).

Easy customizable animated text change library.

* Implemented as extension methods for TextView so it can be easily integrated into existing projects
* Bundles 6 pre-defined [animations] (https://github.com/ChiliLabs/ChiliTextAnimation/tree/master/chilitextanimation/src/main/java/lv/chi/chilitextanimation/animation)
* Provides easy way to use complex animations simply by [configuring properties] (https://github.com/ChiliLabs/ChiliTextAnimation/blob/master/chilitextanimation/src/main/java/lv/chi/chilitextanimation/configuration/CharacterChangeAnimationConfig.kt)
* Provides possibility to write you own animation by implementing [CharacterChangeAnimation] (https://github.com/ChiliLabs/ChiliTextAnimation/blob/master/chilitextanimation/src/main/java/lv/chi/chilitextanimation/configuration/CharacterChangeAnimation.kt)

<img src="img/demo.gif" width="337" height="600">

## Setup

Gradle:

Add Jitpack to your root `build.gradle` file:

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
        ...
    }
}
```

Add dependency to application `build.gradle` file, where `x.y.z` is the latest [release version](https://github.com/ChiliLabs/ChiliTextAnimation/releases):

[![](https://jitpack.io/v/ChiliLabs/ChiliTextAnimation.svg)](https://jitpack.io/#ChiliLabs/ChiliTextAnimation)

```
implementation "com.github.ChiliLabs:ChiliTextAnimation:x.y.z"
```

## Usage

### Pre-defined animations

Call `animateCharacterChange` with preferred animation and duration in milliseconds. 
* `animation` - required. Pre-defined animation or your [CharacterChangeAnimation] (https://github.com/ChiliLabs/ChiliTextAnimation/blob/master/chilitextanimation/src/main/java/lv/chi/chilitextanimation/configuration/CharacterChangeAnimation.kt) implementation
* `duration` - not required. Animation duration, default value - 1 second

```kotlin
textView.animateCharacterChange(
    animation = FadeCharacterAnimation(),
    duration = 500L
)
```

### Custom animations

Call `animateCharacterChange` overload with lambda and specify animation [configuration] (https://github.com/ChiliLabs/ChiliTextAnimation/blob/master/chilitextanimation/src/main/java/lv/chi/chilitextanimation/configuration/CharacterChangeAnimationConfig.kt)

|     Property     | Recommended value | Default value |                Description                |
|:----------------:|:-----------------:|:---------:|:---------------------------------------------:|
|      `alpha`     |       0f..1f      |    1f   | End value for character to be replaced, start value for new character |
|  `textSizeCoef`  |       0f..1f      |    1f   | `newTextSize = textSize * textSizeCoef`. End value for character to be replaced, start value for new character |
|  `charWidthCoef` |       0f..1f      |    1f   | `textScaleX = charWidthCoef` End value for character to be replaced, start value for new character |
|`translationXCoef`|      -1f..1f      |    0f   | `xOffset = symbolWidth * translationXCoef`. Positive value - slide right, negative - left |
|`translationYCoef`|      -1f..1f      |    0f   | `yOffset = height * translationYCoef`. Positive value - slide up, negative - down |
|    `duration`    |          N        |  1000L  | Animation duration in milliseconds |

But despite the recommendations, you definitely should play around with property values to get some extraordinary animations

```kotlin
textView.animateCharacterChange {
    alpha = 0f
    charWidthCoef = 0f
    translationXCoef = 1f
    duration = 1000L
}
```

## License

```
Copyright 2020 Chili Labs

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```