import akka.actor.{Actor, ActorLogging, Props}
import akka.actor.ActorSystem
import scala.io.StdIn

object MainDude {
	def props(): Props = Props(new MainDude)
}

class MainDude extends Actor with ActorLogging {
	def receive: Receive = {
		case "ok" => log.info("OKKKK")
	}
}

object Hi {
	def main(args: Array[String]) {
		val system = ActorSystem("mansss")
		try {
			val dude = system.actorOf(MainDude.props(), "thedude")
			dude ! "ok"
			while (StdIn.readLine() == "") {
				dude ! "ok"
			}
		} finally {
			system.terminate()
		}
	}
}
