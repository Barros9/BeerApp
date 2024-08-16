package com.barros.beerapp.libraries.beer.data.network

import com.barros.beerapp.libraries.beer.domain.BeerFake
import com.barros.beerapp.libraries.beer.utils.FileReader.readFileFromResources
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class BeerModelApiTest {

    private val mockWebServer = MockWebServer()
    private lateinit var client: OkHttpClient
    private lateinit var api: BeerApi
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Before
    fun setup() {
        mockWebServer.start()

        client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(BeerApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch beer list correctly giving success response`() = runBlocking {
        // Given
        val listOfBeerNetworkModel = BeerFake.listOfBeerNetworkModel
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(readFileFromResources("beers.json"))

        // When
        mockWebServer.enqueue(mockResponse)
        val response = api.getBeers(beerName = "", page = 1, perPage = 20)

        // Then
        assertEquals(listOfBeerNetworkModel, response)
    }
}
