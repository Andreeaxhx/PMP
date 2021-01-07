package Lab12

import com.cra.figaro.algorithm.sampling.{Importance}
import com.cra.figaro.language._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.algorithm.factored._

object Ex1 {
  def main(args: Array[String]): Unit = {
  val x_prim = Apply(Normal(0.75, 0.3), (d: Double) => d.max(0).min(1))
  val y_prim = Apply(Normal(0.4, 0.2), (d: Double) => d.max(0).min(1))
  val x = Flip(x_prim)
  val y = Flip(y_prim)
  val z = If(x === y, Flip(0.8), Flip(0.2))

  z.observe(false)
  val veAnswer = VariableElimination.probability(y, true)
  for { i <- 1000 to 10000 by 1000 } {
    var totalSquaredError = 0.0
    for { j <- 1 to 100 } {
      val imp = Importance(i, y)
      imp.start()
      val impAnswer = imp.probability(y, true)
      val diff = veAnswer - impAnswer
      totalSquaredError += diff * diff
    }
    val rmse = math.sqrt(totalSquaredError / 100)
    println(i + " samples: RMSE = " + rmse)
  }}

}