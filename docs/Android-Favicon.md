# Android Chrome and its favicon

Favicon is more or less complex from a platform to another. I think Chrome for Android is the trickiest.

With iOS, things are not that easy but clear: Apple defined the Apple Touch icon, a proprietary mechanism. This approach makes sense. After all, when iOS was introduced, it required new, high resolution icons that did not exist anywhere else. As a result, we have a somehow proprietary markup with a specific apple-touch-icon relation attribute. The Apple Touch icon comes with an advantage: because it is specified for iOS, Apple can enforce design guidelines. Solid background, rounded corners, etc.

Unlike iOS Safari, Android Chrome (Chrome for short) does not define any proprietary mechanism. That surely sounds good. No one likes vendor-specific stuff. But this approach comes with a cost. More of this below.

As indicated in the specs, Chrome relies on two sets of icons:

* Apple Touch icon. Yep, the primary choice for Chrome is (… was) the icon defined for its competitor. This is a sensible behavior: with so few support for high resolution icon, supporting the famous Apple Touch icon gives Chrome the best chance to find a suitable icon.
* Two 192×192 and 128×128 PNG icons, with a preference for the 192×192 icon. These icons are based on standard HTML5 markups.
It is easy to figure out Google’s strategy: introduce a dedicated 192×192 high resolution icon and fallback to the Apple Touch icon when not available. Pragmatic, as it should be. But the devil is in the details, and when you try to create the ideal favicon, things become quite tough.

## 192×192 or 196×196

A small detail as a starter. I started to study Chrome’s icon for almost a year. In the beginning, it was a 196×196 icon. When I started this article, I re-read the specs, something I had not done for a while. I realize there was a change: 196×196 became 192×192.

As we will see below, this is a major change because today’s Chrome (version 37) does not use the 196×196 PNG icons that were designed especially for it.

## Apple Touch icon or PNG icon

Since Chrome supports two kind of icons, which one does it use? Looking at the compatibility page of RealFaviconGenerator:

| Android Chrome version | Favicon | Bookmark icon | Home screen icon |
|---|---|---|---|
| 31 | 16×16 PNG icon | 152×152 Apple Touch icon | 152×152 Apple Touch icon |
| 32 | 196×196 PNG icon | 152×152 Apple Touch icon | 196×196 PNG icon |
| 35 | 32×32 PNG icon | 152×152 Apple Touch icon | 152×152 Apple Touch icon |
| 36 | 160×160 PNG icon | 160×160 PNG icon | 160×160 PNG icon |
| 37 |(latest version at time of writing) | 160×160 PNG icon | 160×160 PNG icon | 160×160 PNG icon |

Apparently, Google finally set its preference to the PNG icon, if it can find one.

This brings consistency: let’s design a PNG icon and Chrome will not pick anything from Apple. But now comes the trick. Sure we can design an icon for Chrome. But as the PNG icons are declared with standard HTML5, other browsers can use it, too. This is what happens with the current pictures and code generated with RealFaviconGenerator (version 0.7). Because the Chrome PNG icon is quite large, some browsers, such as IE11, consider it as a good choice. This is no good news when the author of RealFaviconGenerator decides that the Chrome icon must be designed after the iOS icon.

Which PNG icon?

According to the specs, Chrome picks the 192×192 icon if it finds it, the 128×128 icon otherwise. Is that the case? To demonstrate this, I generated all PNG icons from 16×16 to 256×256 and declared them:


<link rel="icon" type="image/png" href="/16.png" sizes=16x16>
<link rel="icon" type="image/png" href="/17.png" sizes=17x17>
<link rel="icon" type="image/png" href="/18.png" sizes=18x18>
...
<link rel="icon" type="image/png" href="/255.png" sizes=255x255>
<link rel="icon" type="image/png" href="/256.png" sizes=256x256>

With this code, Android Chrome uses the 192×192 icon, as expected. Good.

What if it is not present? Chrome selects the icon just below: 191×191. This make sense, although the specs say nothing about this. And it goes on and on. Delete everything from 129×129 to 192×192 and Chrome uses the “low resolution” 128×128 icon. Delete it and Chromes takes the 127×127 icon. In the end, if you offer Chrome a 16×16 icon and icons larger than 192×192 (eg. 193×193, 194×194, etc.), it uses the 16×16 icon.

Thus, this general rule about Android Chrome 37: Chrome uses the same icon as the favicon, bookmark icon and “Add to home screen” icon. It picks the widest PNG icon it can find which is no larger than 192×192.

The goal of this post is to prepare a fix for issue #26. In the next version of RealFaviconGenerator, Chrome’s icon and classic PNG icons will be correctly dispatched.