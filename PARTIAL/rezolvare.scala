package Examen
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.factored._
object rezolvare {
      private val uitatSetatAlarma = Flip (0.1) //probabilitatea cu care uit sa pun alarma
      private val amanatAlarma = Flip(0.1) //probabilitatea cu care aman alarma
      private val trezitTarziu = CPD(uitatSetatAlarma, amanatAlarma,
        (false, true) -> Flip(0.1),
        (true, false) -> Flip(0.9),
        (true, true) -> Flip(0.9),
        (false, false) -> Flip(0))
      //variabila trezitTarziu memoreaza probabilitatea e a ma trezi tarziu avand in vedere alte lucruri care se pot intampla:
      //am uitat sa pun alarma sau am pus-o, dar am amanat-o

      val autobuzulIntarzie = Flip(0.2) //variabila autobuzulIntarzie memoreaza probabilitatea cu care autobuzul intarzie

      //intarzieLaMunca este variabila care memoreaza probabilitatea de a intarzia la munca, tinand cont de
      //celelalte lucruri care se pot intampla: sa ma trezesc tarziu, sa intarzie autobuzul
      val intarzieLaMunca =  CPD(trezitTarziu, autobuzulIntarzie,
        (true, true) -> Flip(0.1),
        (true, false) -> Flip(0.3),
        (false, true) -> Flip(0.2),
        (false, false) -> Flip(0.9))
      def main(args: Array[String]){

      amanatAlarma.observe(true) //presupunem ca am amanat alarma
      println("Care este probabilitatea sa ajungi la serviciu la timp, avand in vedere ca ai adormit?\n"+VariableElimination.probability(intarzieLaMunca, false))
      amanatAlarma.unobserve()

      intarzieLaMunca.observe(false) presupunem ca nu am intarziat la munca==am ajuns la timp
      println("Avand in vedere ca ai ajuns la timp la serviciu, care este probabilitatea să iti fi setat alarma?\n"+VariableElimination.probability(uitatSetatAlarma, false))
      amanatAlarma.unobserve()

      println("Care este probabilitatea sa adormi?\n"+VariableElimination.probability(trezitTarziu, true)) //probabilitatea de a ma trezi tarziu


    }

}
