/*
* Croc1.scala
*
* Adaptation of the Snap.svg demo: 
*   https://github.com/adobe-webplatform/Snap.svg/blob/master/demos/snap-mascot/crocodile-1.html
*
* Copyright © 2013 Adobe Systems Incorporated. All rights reserved.
* Copyright (c) 2014, Asko Kauppi
* 
* Licensed under the Apache License, Version 2.0 (the “License”);
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
* http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an “AS IS” BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package akauppi.scalajs.snapsvg.example

import scala.scalajs.js
import js.JSConverters._

/*
* Note: Using 'org.scalajs.dom.WindowTimers' did not work: 
* <<
*   [error] /Users/asko/Git/scalajs-snapsvg/example/src/main/scala/croc1.scala:31: illegal inheritance; superclass Object
*   [error]  is not a subclass of the superclass Object
*   [error]  of the mixin trait WindowTimers
*   [error] object Croc1App extends js.JSApp with WindowTimers {
*   [error]                                       ^
* <<
*/
//import org.scalajs.dom.WindowTimers

import akauppi.scalajs.snapsvg._

object Croc1App extends js.JSApp /*with WindowTimers*/ {
  //val alert = js.Dynamic.global.alert   // debugging 
  // or:
  //val alert = dom.alert
  
  val clearTimeout = js.Dynamic.global.clearTimeout
  val setTimeout = js.Dynamic.global.setTimeout

  // tbd. we shouldn't have this hack, 'mina.backin' and 'mina.elastic' should just work. Right? AK310814
  //
  /***
  val mina = new {
    def backin = SnapMina.backin
    def elastic = SnapMina.elastic
  }
  ***/
    
  def main(): Unit = {
    val croc = Snap.select("#crocodile")
    val (head, jaw, symbol) = (croc.select("#upper-head"), croc.select("#upper-jaw"), croc.select("#symbol"))
    
    var timer: js.Dynamic /*Int*/ = null

    case class xy( x: Int, y: Int ) {
      def toString = s"$x,$y"   // being used for Snap transforms, must be this format
    }
    
    val pivot_head = xy(44,147)
    val pivot_jaw = xy(92,126)

    def close {
      clearTimeout(timer)

      head.animate( js.Dictionary[js.Any](
          "transform" -> s"r8,${pivot_head}"
      ), 500, mina.backin)

      jaw.animate( js.Dictionary[js.Any](
          "transform" -> s"r37,${pivot_jaw}"
      ), 500, mina.backin)

      timer = setTimeout( () => {
        symbol.animate( js.Dictionary[js.Any](
          "transform" -> "t-70,40r40"
        ), 100 )
      }, 400 )
    }

    def open {
      clearTimeout(timer)

      head.animate( js.Dictionary[js.Any](
          "transform" -> s"r0,${pivot_head}"
      ), 700, mina.elastic)

      jaw.animate( js.Dictionary[js.Any](
          "transform" -> s"r0,${pivot_jaw}"
      ), 700, mina.elastic)

      symbol.animate( js.Dictionary[js.Any](
          "transform" -> "t0,0r0"
      ), 500, mina.elastic)
    }

    timer = setTimeout(close, 50)

    croc.hover( open, () => { timer = setTimeout(close, 200) } )
  }
}

