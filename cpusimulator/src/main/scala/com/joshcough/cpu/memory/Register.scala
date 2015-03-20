/*
 * Created by IntelliJ IDEA.
 * User: joshcough
 * Date: May 31, 2009
 * Time: 9:01:58 AM
 */
package com.joshcough.cpu.memory

import electric.PowerSource
import cpu.BitList
import cpu.BitsToList._

case class Register(data: BitList, writeBit: PowerSource) {

  // could easily make this one line, but i think its more readable as 2.
  val flipFlops = data.map( FlipFlop(_, writeBit ) )
  val output = BitList(flipFlops.map(_.output))
}
