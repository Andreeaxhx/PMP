package Lab4

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.atomic.discrete._

object Ex6 {
  def main(args: Array[String]) {
    def doubles1 = {
      val die1 = FromRange(1, 7)
      val die2 = FromRange(1, 7)
      die1 === die2
    }

    def doubles2 = {
      val die1 = FromRange(1, 7)
      val die2 = FromRange(1, 7)
      die1 === die2 && !(die1 === 1)
    }

    def doubles3 = {
      val die1 = FromRange(1, 7)
      val die2 = FromRange(1, 7)
      die1 === die2 && !(die1 === 1) && !(die1 === 2)
    }
    val jail = doubles1 && doubles2 && doubles3
    println(VariableElimination.probability(jail, true))
  }
}