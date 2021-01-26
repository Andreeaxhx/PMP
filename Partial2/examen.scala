package Partial2
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.library.collection
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.compound.{*, OneOf, RichCPD}
import com.cra.figaro.language.Element._
import com.cra.figaro.language.ElementCollection
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.library.collection
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.compound.{*, OneOf, RichCPD}
import com.cra.figaro.language.Element._
import com.cra.figaro.language.ElementCollection


object examen {

  val nrOre = 5 //lungimea lantului==numarul de ore pe care vrem sa le monitorizam
  val stareaVremii: Array[Element[String]] = Array.fill(length)(Constant("")) //vectorul care cuprinde starea vremii pentru fiecare ora monitorizata

  val iauUmbrela: Array[Element[Boolean]] = Array.fill(length)(Constant(false)) //vectorul care cuprinde valoarea de adevar a afirmatiei "iau umbrela",
                                                                                // pentru fiecare ora monitorizata

  stareaVremii()=Select(0.5->'insorit, 0.3->'innorat, 0,2->'ploios) //din "Start", starea vremii poate fi "insorit", "innorat", "ploios" cu probabilitatile
                                                                    //0.5, 0.3, respectiv 0.2

  for { ora <- 1 until nrOre } {
    //pentru fiecare ora pe care o monitorizam aplicam CPD pentru a obtine probabilitatile de a "trece" de la un fenomen la altul
    stareaVremii(ora) = CPD(stareaVremii(ora - 1), 'insorrit->Select(0.6 -> 'insorit, 0.3 -> 'innorat, 0.1 -> 'ploios),
      'innorat -> Select(0.15 -> 'insorit, 0.5 -> 'innorat, 0.35 -> 'ploios),
      'ploios -> Select(0.15 -> 'insorit, 0.4 -> 'innorat, 0.45 -> 'ploios))
  }
  for { ora <- 0 until nrOre } {
    //apoi pentru fiecare ora pe care dorim sa o monitorizam, incercam sa aflam probabilitatea cu care trebuie sa luam umbrela
    {
      iauUmbrela(ora) = CPD(stareaVremii(ora),
        'insorit -> Flip(0.15),
        'innorat ->  Flip(0.65),
        'ploios ->  Flip(0.75))
    }
  }

  def main(args: Array[String]): Unit = {
    for {ora <- 1 until 5} {
      {
        //pentru fiecare ora, ai=fisam observatiile facute
        println("Ora: " + ora)
        println(VariableElimination.probability(stareaVremii(ora), 'insorit))
        println(VariableElimination.probability(stareaVremii(ora), 'innorat))
        println(VariableElimination.probability(stareaVremii(ora), 'ploios))
      }
    }

    stareaVremii(4).observe('ploios) //daca stim ca in ora 4 a fostt ploios
    stareaVremii(5).observe('ploios) //si in ora 5 la fel
    println(VariableElimination.probability(iauUmbrela(6), true)) //calculam probabilitatea de a lua umbrela in ora 6

  }

}