import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import java.time.Instant

class UniqueServer(_salt_string: String) {
	val salt_string = _salt_string

	def random(): String = {
		val sha3 = new SHA3.Digest256()
		val plainstring = s"${salt_string}${Instant.now().toString}${System.nanoTime()}"
		val digest = Hex.toHexString(sha3.digest(plainstring.getBytes()))
		println(s"sha3(${plainstring}) = ${digest}")
		digest
	}
}

object UniqueServer {

	def main(args: Array[String]) {

		implicit val system = ActorSystem("my-system")
		implicit val materializer = ActorMaterializer()
		// needed for the future flatMap/onComplete in the end
		implicit val executionContext = system.dispatcher

		val salt_string = if (args.size > 0) args(0) else "nullseed"
		val server = new UniqueServer(salt_string)

		println(s"starting unique server with salt ${salt_string}")

		val route =
			path("unique") {
				get {
					complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, server.random()))
				}
			}

		val bindingFuture = Http().bindAndHandle(route, "localhost", 9090)

		println(s"Server online at http://localhost:9090/\nPress RETURN to stop....")
		StdIn.readLine()							// let it run until user presses return
		
		bindingFuture
			.flatMap(_.unbind())					// trigger unbinding from the port
			.onComplete(_ => system.terminate())	// and shutdown when done
	}
}