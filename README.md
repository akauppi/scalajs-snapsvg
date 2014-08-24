scala-js-snapsvg
===

Static types for the [Snap.svg](http://snapsvg.io) SVG vector graphics API for [Scala.js](http://www.scala-js.org/) programs.

> DISCLAIMER: THE PROJECT IS IN ITS EARLY STAGES (early alpha). It's been publicized in GitHub to gather momentum, contributions and simply to help coordinate efforts (s.a. creating issues, maybe). NOT FOR PRODUCTION USE, yet, so please don't waste your time nagging about that but ask "what I can do" instead - or simply wait some 4-6 months and let us get it finished, polished, optimized and gorgeous! Thanks. AK230814


State and Intention
---

My intention is to get a full API bridge up and running, with Snap.svg samples reproduced in Scala. There will be two levels of abstraction: the basic JavaScript -> Scala language bridging (bringing types but keeping the API otherwise pretty unmodified) and a higher level 'Scalaesque' API that tries to make working with vector graphics in a browser as effortless and natural for Scala coders as possible, and does not mind looking unlike Snap.svg on JavaScript.

Pros of the basic bridge:

- easy
- focus well defined
- ability to reuse existing JavaScript Snap.svg code with minimum alterations

Pros of the Scalaesque bridge:

- if doing one's own projects where compatibility with the JavaScript API is not important, allows for a more natural and maybe concise expression

Maybe some concepts from the Scalaesque API will find their way back to mainstream Snap.svg (JavaScript side) as well.


Compiling
---
<pre>
$ sbt
&gt; fastOptJS
</pre>

That pulls in the dependencies, compiles the bridge and the demos. 


Demos
---

There are demo samples in the `example/` side project. These are 1-to-1 adaptations of the JavaScript demos in Snap.svg project. They get compiled alongside the normal project.

- open `example/clock.html` in a browser

You should see this (click on the clock face):

![](example/images/clock.png)

More demos will be made available, see the TODO.md.


Usage in your own code
---

[ Needs to be edited once the code is ready, and available at a repository. ]

<!-- TBD
Add the following to your sbt build definition:

    libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-snapsvg" % "0.01"

then enjoy the types available in `org.scalajs.snapsvg`.

scalajs-snapsvg 0.01 is built and published for Scala.js 0.5.0 and following in
the 0.5.x series, with ~~both Scala 2.10 and~~ 2.11 (not tested for Scala 2.10.x).
-->


Credits
---
- Scala.js is mindboggling. Thanks, [Sébastien](https://github.com/sjrd)
- [Dmitry Baranovskiy](http://dmitry.baranovskiy.com) for Snap.svg and good demos - a library worth bridging
- Build system is based on that of [scalajs-react](https://github.
com/japgolly/scalajs-react) by [David Barri](https://github.com/japgolly)

In addition:

- Thanks to Martin and Typesafe for [Scala](https://github.com/scala/scala)
- [Everyone at Github](https://github.com/about/team) for a world-changing collaboration mechanism


