import akka.actor.{Actor, ActorLogging, Props}
import akka.actor.ActorSystem
import scala.io.StdIn

object MainDude {
	def props(): Props = Props(new MainDude)
}

class MainDude extends Actor with ActorLogging {

	override def preStart() {
		log.info("just started!")
	}

	override def postStop() {
		log.info("just stopped..")
	}

	def receive: Receive = {
		case "ok" => log.info("OKKKK")
		case "no" => throw new Exception("nooooooo")
	}
}

object Hi {
	def main(args: Array[String]) {
		val system = ActorSystem("mansss")
		try {
			val dude = system.actorOf(MainDude.props(), "thedude")
			dude ! "ok"
			var input = StdIn.readLine()
			while (true) {
				dude ! input
				input = StdIn.readLine()
			}
		} finally {
			system.terminate()
		}
	}
}
