package Lab8

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.library.collection
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.compound.{RichCPD, OneOf, *}

object Ex1 {

  class Finances{
    val budget = 10 //bugetul companiei
  }

  class HumanResources{

    //numarul de angajati pe fiecare departament
    val employees_hr=100
    val employees_fin=25
    val employees_res=40
    val employees_sales=70
    val employees_prod=125
  }

  case class Research_and_Development(finances : Finances, hr : HumanResources) {
    //nivelul sanatatii si rating ul
    var rating = 0
    var health = 0
    if (finances.budget > 7 && hr.employees_res >= 40) {
      health = 5
      rating = 5
    }
    else if (finances.budget > 7 && hr.employees_res < 40)
    {
      health = 4
      rating = 4
    }
    else if (finances.budget < 7 && hr.employees_res > 40)
    {
      health = 3
      rating = 3
    }
    else if (finances.budget < 7 && hr.employees_res < 40)
    {
      health = 2
      rating = 2
    }
  }

  class Production(finances : Finances, hr : HumanResources, r_and_d : Research_and_Development){


  }

  class Sales(finances : Finances, production: Production){

  }

  def main(args: Array[String]): Unit = {
    val finances = new Finances
    val hr = new HumanResources
    println(hr.employees_fin + " employees in Finances.")
    println(hr.employees_hr + " employees in Human Resources.")
    println(hr.employees_res + " employees in Research and Development.")
    println(hr.employees_prod + " employees in Production.")
    println(hr.employees_sales + " employees in Sales.")
    val r_and_d = new Research_and_Development(finances,hr)
    if (r_and_d.health==5) println("The health level is very good")
    else if (r_and_d.health==4) println("The health level is good")
    else if (r_and_d.health==3) println("The health level is medium")
    else if (r_and_d.health==2) println("The health level is bad")

  }


}