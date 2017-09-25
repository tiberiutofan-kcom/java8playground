package java8playground;

import java.util.Random;

public class LongRunningOperations {

    private static Random random = new Random();

    public static Weather weatherFromAccuweather(String city) {
        System.out.println("Accuweather called!");
        try {
            Thread.sleep(random.nextInt(1000));
            return new Weather(city, 21.5, 70.0, "Accuweather says: Mostly sunny");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Accuweather finished!");
        }
    }

    public static Weather weatherFromWUnderground(String city) {
        System.out.println("Weather Underground called!");
        try {
            Thread.sleep(random.nextInt(1000));
            return new Weather(city, 20.0, 50.0, "Weather Underground says: Mostly sunny");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Weather Underground finished!");
        }
    }

    public static boolean orderAnUmbrella() {
        try {
            Thread.sleep(random.nextInt(1000));
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Weather {
        private String city;
        private Double temperature;
        private Double chanceOfPrecipitation;
        private String description;

        private Weather(String city, Double temperature, Double chanceOfPrecipitation, String description) {
            this.city = city;
            this.temperature = temperature;
            this.chanceOfPrecipitation = chanceOfPrecipitation;
            this.description = description;
        }

        public String getCity() {
            return city;
        }

        public Double getTemperature() {
            return temperature;
        }

        public Double getChanceOfPrecipitation() {
            return chanceOfPrecipitation;
        }

        public String getDescription() {
            return description;
        }
    }
}
