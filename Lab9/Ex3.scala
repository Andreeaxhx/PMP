package Lab9

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.library.collection
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.compound.{*, OneOf, RichCPD}
import com.cra.figaro.language.Element._
import com.cra.figaro.language.ElementCollection


object Ex3 {

  // 3 array-uri pentru capitolele de invatat, teste si note
  val nr_chapters = 10
  val nr_tests = 4;
  val chapters: Array[Element[Boolean]] = Array.fill(nr_chapters)(Constant(false))
  val tests: Array[Element[Boolean]] = Array.fill(nr_tests)(Constant(false))
  val grades: Array[Element[Int]] = Array.fill(nr_tests)(Constant(0))



  // studenta poate invata primul capitol cu o probabilitate de 97%
  // pe masura ce invata capitolele, gradul de dificultate creste,
  // asa ca probabilitatea de a invata un capitol, scade
  chapters(0) = Flip(0.97)
  for { chapter <- 1 until nr_chapters } {
    chapters(chapter) = If(chapters(chapter - 1), Flip(0.95 - (chapter/100)), Flip(0.5 - (chapter/100)))

  }
  // testele sunt date din 2 in 2 capitole
  for { test <- 0 until nr_tests } {
    tests(test) = If(chapters(test*2), Flip(0.7), Flip(0.3))
  }


  def main(args: Array[String]): Unit = {
    tests(0).observe(true)
    tests(1).observe(true)
    tests(2).observe(true)

    println("Probability of passing the 4th test: " + VariableElimination.probability(tests(3), true))

    tests(0).unobserve
    tests(1).unobserve
    tests(2).unobserve


  }
}