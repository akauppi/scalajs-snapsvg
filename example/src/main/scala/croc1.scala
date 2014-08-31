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

import org.scalajs.dom.{alert, clearTimeout, setTimeout, console}

import akauppi.scalajs.snapsvg._

// BUG: the close animation is more restless than in the original JavaScript demo.
//    Especially the exclamation mark ('#symbol') moves into the throat of the croc,
//    which it does not in the JavaScript demo. Not sure what causes this. AK310814

object Croc1App extends js.JSApp /*with WindowTimers*/ {    // tbd. how are we supposed to use the 'dom.WindowTimers'? 
  //val alert = js.Dynamic.global.alert   // debugging (or: 'val alert = dom.alert' if 'org.scalajs.dom' was importable)
  //val clearTimeout = js.Dynamic.global.clearTimeout
  //val setTimeout = js.Dynamic.global.setTimeout
    
  def main(): Unit = {
    val croc = Snap.select("#crocodile")
    val (head, jaw, symbol) = (croc.select("#upper-head"), croc.select("#upper-jaw"), croc.select("#symbol"))
    
    var timer: Int = -1

    case class xy( x: Int, y: Int ) {
      override
      def toString = s"$x,$y"   // being used for Snap transforms, must be this format
    }
    
    val pivot_head = xy(44,147)
    val pivot_jaw = xy(92,126)

    def close() = {
      clearTimeout(timer)
      console.log("close"); 

      head.animate( js.Dynamic.literal(
          "transform" -> s"r8,${pivot_head}"
      ), 500, mina.backin _)    // tbd. Scalaesque: let's make it so the '_' are not needed 

      jaw.animate( js.Dynamic.literal(
          "transform" -> s"r37,${pivot_jaw}"
      ), 500, mina.backin _)

      timer = setTimeout( () => {
        symbol.animate( js.Dynamic.literal(
          "transform" -> "t-70,40r40"
        ), 100 )
      }, 400 )
    }

    def open() = {
      clearTimeout(timer)
      console.log( "open" )

      head.animate( js.Dynamic.literal(
          "transform" -> s"r0,${pivot_head}"
      ), 700, mina.elastic _)

      jaw.animate( js.Dynamic.literal(
          "transform" -> s"r0,${pivot_jaw}"
      ), 700, mina.elastic _)

      symbol.animate( js.Dynamic.literal(
          "transform" -> "t0,0r0"
      ), 500, mina.elastic _)
    }

    timer = setTimeout(close, 50)

    // Note: The 'out' callback seems to get called even when moving the cursor
    //      from the upper jaw to the lower (also in the original JavaScript demo).
    //      This cannot be the intention (might be a bug in Snap.svg itself) - however,
    //      the JavaScript  
    //
    croc.hover( open _,   // tbd. is there a way not to need the trailing '_' for 'open'? AK310814
      // The second callback gets called when cursor moves out of the hover area, not before
      () => { 
        console.log( "out" )
        timer = setTimeout(close, 200) 
      }
    ) 
  }
}

