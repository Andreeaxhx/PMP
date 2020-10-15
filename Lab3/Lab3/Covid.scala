package Lab3
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.language._
import com.cra.figaro.library.compound._

class Covid{
  Universe.createNew()
  private val tuse = Flip(0.04)
  private val febra = Flip(0.06)

  private val covid = CPD(tuse, febra,
    (false, false) -> Flip(0.25),
    (false, true) -> Flip(0.4),
    (true, false) -> Flip(0.5),
    (true, true) -> Flip(0.8))

  private val aiCovid = CPD(covid,
    false -> Flip(0.25),
    true -> Flip(0.75))

  def main(args: Array[String]) {
    aiCovid.observe(true)
    val alg = VariableElimination(tuse, febra)
    alg.start()
    println("Probability of tuse: " + alg.probability(tuse, true))
    println("Probability of tuse: " + alg.probability(febra, true))
    alg.kill
  }
}