package java8playground;

import java8playground.LongRunningOperations.Weather;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static java.lang.Boolean.FALSE;

public class CompletableFutureTest {

	@Test
	public void printAccuweatherWeatherDesciption() throws Exception {
		CompletableFuture<Void> future =
				CompletableFuture
						.supplyAsync(() -> LongRunningOperations.weatherFromAccuweather("Bucharest"))
						.thenApply(Weather::getDescription)
						.thenAccept(System.out::println);

		future.get();
	}

	@Test
	public void printTheFasterReceivedWeather() throws Exception {
		CompletableFuture<Weather> accuweather = CompletableFuture.supplyAsync(() -> LongRunningOperations.weatherFromAccuweather("Bucharest"));
		CompletableFuture<Weather> wUnderground = CompletableFuture.supplyAsync(() -> LongRunningOperations.weatherFromWUnderground("Bucharest"));

		CompletableFuture<Void> future =
				accuweather.applyToEither(wUnderground, Weather::getTemperature).thenAccept(System.out::println);

		future.get();
	}

	@Test
	public void orderAnUmbrellaIfRains() throws Exception {
		CompletableFuture<Weather> accuweather = CompletableFuture.supplyAsync(() -> LongRunningOperations.weatherFromAccuweather("Bucharest"));
		CompletableFuture<Weather> wUnderground = CompletableFuture.supplyAsync(() -> LongRunningOperations.weatherFromWUnderground("Bucharest"));

		CompletableFuture<Void> future = accuweather
				.thenCombine(wUnderground, (w1, w2) -> w1.getChanceOfPrecipitation() + w2.getChanceOfPrecipitation() / 2)
				.thenCompose(this::orderUmbrellaIfItRains)
				.thenAccept(b -> System.out.printf("Umbrella ordered? %b", b));

		future.get();
	}

	private CompletableFuture<Boolean> orderUmbrellaIfItRains(double chanceOfPrecipitation) {
		if (chanceOfPrecipitation > 80) {
			return CompletableFuture.supplyAsync(LongRunningOperations::orderAnUmbrella);
		} else {
			return CompletableFuture.completedFuture(FALSE);
		}
	}


}
