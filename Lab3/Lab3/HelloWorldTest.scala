package Lab3
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
object HelloWorldTest{
  def main(args: Array[String]){
    val helloWorldElement = Constant("Hello world!")
    val sampleHelloWorld = Importance(1000, helloWorldElement)

    sampleHelloWorld.start()

    println("Probability of Hello world:")
    println(sampleHelloWorld.probability(helloWorldElement, "Hello world!"))
    println("Probability of Goodbye world:")
    println(sampleHelloWorld.probability(helloWorldElement, "Goodbye world!"))
  }
}