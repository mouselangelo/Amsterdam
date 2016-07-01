package com.deuxlapins.amsterdam;

import com.deuxlapins.amsterdam.model.service.yahoo.YahooWeatherAPI;
import com.deuxlapins.amsterdam.vo.Place;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;


/**
 * Tests the model by mocking the API to return a static JSON response
 * Created by chetan on 01/07/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class YahooAPITests {

    /**
     * static json response to be returned
     */
    private static final String JSON_RESPONSE = "{\"query\":{\"count\":1,\"created\":\"2016-07-01T11:48:39Z\",\"lang\":\"en-us\",\"results\":{\"channel\":{\"units\":{\"distance\":\"mi\",\"pressure\":\"in\",\"speed\":\"mph\",\"temperature\":\"F\"},\"title\":\"Yahoo! Weather - Amsterdam, NH, NL\",\"link\":\"http://us.rd.yahoo.com/dailynews/rss/weather/Country__Country/*https://weather.yahoo.com/country/state/city-727232/\",\"description\":\"Yahoo! Weather for Amsterdam, NH, NL\",\"language\":\"en-us\",\"lastBuildDate\":\"Fri, 01 Jul 2016 01:48 PM CEST\",\"ttl\":\"60\",\"location\":{\"city\":\"Amsterdam\",\"country\":\"Netherlands\",\"region\":\" NH\"},\"wind\":{\"chill\":\"63\",\"direction\":\"225\",\"speed\":\"18\"},\"atmosphere\":{\"humidity\":\"82\",\"pressure\":\"1011.0\",\"rising\":\"0\",\"visibility\":\"16.1\"},\"astronomy\":{\"sunrise\":\"5:24 am\",\"sunset\":\"10:5 pm\"},\"image\":{\"title\":\"Yahoo! Weather\",\"width\":\"142\",\"height\":\"18\",\"link\":\"http://weather.yahoo.com\",\"url\":\"http://l.yimg.com/a/i/brand/purplelogo//uh/us/news-wea.gif\"},\"item\":{\"title\":\"Conditions for Amsterdam, NH, NL at 12:00 PM CEST\",\"lat\":\"52.373119\",\"long\":\"4.89319\",\"link\":\"http://us.rd.yahoo.com/dailynews/rss/weather/Country__Country/*https://weather.yahoo.com/country/state/city-727232/\",\"pubDate\":\"Fri, 01 Jul 2016 12:00 PM CEST\",\"condition\":{\"code\":\"39\",\"date\":\"Fri, 01 Jul 2016 12:00 PM CEST\",\"temp\":\"63\",\"text\":\"Scattered Showers\"},\"forecast\":[{\"code\":\"39\",\"date\":\"01 Jul 2016\",\"day\":\"Fri\",\"high\":\"65\",\"low\":\"60\",\"text\":\"Scattered Showers\"},{\"code\":\"39\",\"date\":\"02 Jul 2016\",\"day\":\"Sat\",\"high\":\"63\",\"low\":\"57\",\"text\":\"Scattered Showers\"},{\"code\":\"30\",\"date\":\"03 Jul 2016\",\"day\":\"Sun\",\"high\":\"65\",\"low\":\"56\",\"text\":\"Partly Cloudy\"},{\"code\":\"30\",\"date\":\"04 Jul 2016\",\"day\":\"Mon\",\"high\":\"69\",\"low\":\"57\",\"text\":\"Partly Cloudy\"},{\"code\":\"30\",\"date\":\"05 Jul 2016\",\"day\":\"Tue\",\"high\":\"68\",\"low\":\"58\",\"text\":\"Partly Cloudy\"},{\"code\":\"30\",\"date\":\"06 Jul 2016\",\"day\":\"Wed\",\"high\":\"65\",\"low\":\"58\",\"text\":\"Partly Cloudy\"},{\"code\":\"28\",\"date\":\"07 Jul 2016\",\"day\":\"Thu\",\"high\":\"66\",\"low\":\"57\",\"text\":\"Mostly Cloudy\"},{\"code\":\"30\",\"date\":\"08 Jul 2016\",\"day\":\"Fri\",\"high\":\"66\",\"low\":\"58\",\"text\":\"Partly Cloudy\"},{\"code\":\"39\",\"date\":\"09 Jul 2016\",\"day\":\"Sat\",\"high\":\"65\",\"low\":\"57\",\"text\":\"Scattered Showers\"},{\"code\":\"28\",\"date\":\"10 Jul 2016\",\"day\":\"Sun\",\"high\":\"67\",\"low\":\"58\",\"text\":\"Mostly Cloudy\"}],\"description\":\"<![CDATA[<img src=\\\"http://l.yimg.com/a/i/us/we/52/39.gif\\\"/>\\n<BR />\\n<b>Current Conditions:</b>\\n<BR />Scattered Showers\\n<BR />\\n<BR />\\n<b>Forecast:</b>\\n<BR /> Fri - Scattered Showers. High: 65Low: 60\\n<BR /> Sat - Scattered Showers. High: 63Low: 57\\n<BR /> Sun - Partly Cloudy. High: 65Low: 56\\n<BR /> Mon - Partly Cloudy. High: 69Low: 57\\n<BR /> Tue - Partly Cloudy. High: 68Low: 58\\n<BR />\\n<BR />\\n<a href=\\\"http://us.rd.yahoo.com/dailynews/rss/weather/Country__Country/*https://weather.yahoo.com/country/state/city-727232/\\\">Full Forecast at Yahoo! Weather</a>\\n<BR />\\n<BR />\\n(provided by <a href=\\\"http://www.weather.com\\\" >The Weather Channel</a>)\\n<BR />\\n]]>\",\"guid\":{\"isPermaLink\":\"false\"}}}}}}";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).httpsPort(8443));

    /**
     * Tests the Yahoo Weather API and verifies the correct data is returned
     * @throws Exception
     */
    @Test
    public void testAPI() throws Exception {
        // using WireMock to stub API call and return static JSON response.
        stubFor(get(anyUrl())
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(JSON_RESPONSE)
                ));

        YahooWeatherAPI api = new YahooWeatherAPI();
        Place place = api.getWeather();
        assertEquals(place.getName(), "Amsterdam");
        assertEquals(place.getCurrentCondition(), "Scattered Showers");
        assertEquals(place.getCurrentTempInF(),63d, 0.0001);
    }

}