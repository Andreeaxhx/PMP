package Lab11

import com.cra.figaro.language.{Flip, Select}
import com.cra.figaro.library.compound.{CPD,^^}
import com.cra.figaro.algorithm.factored.VariableElimination

object Ex1 {
  val isPresident = Flip(0.000000025)
  val isLeftHanded = CPD(isPresident,
    true -> Flip(0.5),
    false-> Flip(0.1)
  )

  val wentHarvard = CPD(isPresident,
    true  -> Flip(0.15),
    false -> Flip(0.0005)
  )

  val becamePresident = CPD(isLeftHanded,wentHarvard,
    (true,true) -> Flip(0.0025),
    (true,false) -> Flip(0),
    (false,true) -> Flip(0),
    (false,false) -> Flip(0)
  )

  def main(args: Array[String]) {
    isLeftHanded.observe(true)
    println("---What is the probability someone became the president of the United States, given that he or she is left-handed?")
    println(VariableElimination.probability(isPresident, true))
    isLeftHanded.unobserve()

    becamePresident.observe(true)
    println("\n---What is the probability that someone became the president of the United States, given that he or she went to Harvard?")
    println(VariableElimination.probability(isPresident, true))
    becamePresident.unobserve()

    wentHarvard.observe(true)
    println("\n---Assuming left-handedness and going to Harvard are conditionally independent,\ngiven whether someone became president, what’s the probability that\nsomeone became the president of the United States, given that he or she is\nleft-handed and went to Harvard?")
    println(VariableElimination.probability(isPresident, true))
    wentHarvard.unobserve()
    println()


  }


}