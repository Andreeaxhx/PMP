package Lab12

import com.cra.figaro.algorithm.factored._
import com.cra.figaro.language._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.algorithm.sampling._

object Ex3
{
  def main(args: Array[String]): Unit = {

    val x_prim = Apply(Normal(0.75, 0.3), (d: Double) => d.max(0).min(1))
    val y_prim = Apply(Normal(0.4, 0.2), (d: Double) => d.max(0).min(1))
    val x = Flip(x_prim)
    val y = Flip(y_prim)
    val z = If(x === y, Flip(0.8), Flip(0.2))
    z.observe(false)
    val veAnswer = VariableElimination.probability(y, true)
    for {i <- 10000 to 100000 by 10000} {
      var totalSquaredError = 0.0
      for {j <- 1 to 100} {
        val err= Flip(0.9)
        val imp = Importance(i, err)
        imp.start()
        val impAnswer = imp.probability(err, false)
        val diff = veAnswer - impAnswer
        totalSquaredError += diff * diff
      }
      val rmse = math.sqrt(totalSquaredError / 100)
      println(i + " samples: RMSE = " + rmse)
    }
  }

}