/*
* SnapSvgScalaesque.scala
*
* Scala-specific adaptations on top of 'org.scalajs.snapsvg'. 
*
* As an application user, you might well decide to only use this. We've
* cut the API bridging it two to keep the basic bridging apart from abstraction
* level lifts and other Scala niceties that can be possible.
*
* Any application should be possible to code with either the underlying
* 'raw' bridge of this (hopefully slimmer and classier) one.
*
* NOTE: THIS WORK IS COMPLETELY UNDONE. AK030814
*/
package akauppi.scalajs.snapsvg_scalaesque

import akauppi.scalajs.snapsvg._
