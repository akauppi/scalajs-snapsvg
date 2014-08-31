/*
* Clock.scala
*
* Adaptation of the Snap.svg demo:
*   https://github.com/adobe-webplatform/Snap.svg/blob/master/demos/clock/index.html
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

import akauppi.scalajs.snapsvg._

import Math.{PI,sin,cos}

object ClockApp extends js.JSApp {
  //val alert = js.Dynamic.global.alert   // debugging 
  
  def main(): Unit = {
    val s = Snap(600, 600)
    var path = ""

    val nums = s.text( 300, 300, ((1 to 12).map(_.toString)).toJSArray ).attr( js.Dictionary[js.Any](   // tbd: why not just 'js.Dictionary( ...' ? (note: many such places)
          "font" -> "300 40px Helvetica Neue",
          "textAnchor" -> "middle"
        ))

    for( i <- 0 to 71 ) {
      val r = if (i % 6 == 0) 230 else if (i % 3 == 0) 240 else 247
      val y = sin( Snap.rad(5*i) )
      val x = cos( Snap.rad(5*i) )
      
      path += s"M${300+250*x},${300+250*y}L${300+r*x},${300+r*y}"
      if (i % 6 == 0) {
        val rad= Snap.rad( 5*i - 60 )

        nums.select(s"tspan:nth-child(${i/6+1})")
        
        nums.select(s"tspan:nth-child(${i/6+1})").attr( js.Dictionary[js.Any](
          "x" -> (300 + 200 * cos(rad)),
          "y" -> (300 + 200 * sin(rad) + 15)
        ))
      }
    }

    val table = s.g(nums, s.path(path).attr( js.Dictionary[js.Any](
      "fill" -> "none",
      "stroke" -> "#000",
      "strokeWidth" -> 2
    ))).attr( js.Dictionary[js.Any](
      "transform" -> "t0,210"
    ))
    s.g(table).attr( js.Dictionary[js.Any](
      "clip" -> s.circle(300, 300, 100)
    ))
    val hand = s.line(300, 200, 300, 400).attr( js.Dictionary[js.Any](
      "fill" -> "none",
      "stroke" -> "#f63",
      "strokeWidth" -> 2
    ))
    val circ= s.circle(300, 300, 100).attr( js.Dictionary[js.Any](
      "stroke" -> "#000",
      "strokeWidth" -> 10,
      "fillOpacity" -> 0
    ))
    
    circ.click{ () =>
      Snap.animate(0, 2*PI, (rad: Double) => {
        val t= s"t${210*cos(rad+PI/2)},${210*sin(rad+PI/2)}"
        table.transform( t )
        hand.transform( s"r${ Snap.deg(rad) },300,300")
      } , 12000 )
    }
  }    
}

