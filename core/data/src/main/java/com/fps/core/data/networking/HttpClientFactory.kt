package com.fps.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

// We use a Factory Class which has a responsibility to construct complex objects
class HttpClientFactory {

    fun build(): HttpClient {
        return HttpClient(CIO) { // Ktor engine
            // plugins are a set of functionality we want to add to the http client
            install(ContentNegotiation) {// everything that has to do w/ parsing data and converting them to JSON
                json(
                    json = Json {
                        prettyPrint = true
                        isLenient = true // this is used to parse the list of lists successfully
                        ignoreUnknownKeys = false
                    }
                )
            }

            // installing the http timeout plugin to have our own values
            install(HttpTimeout) {
                requestTimeoutMillis = 20000
                connectTimeoutMillis = 20000
                socketTimeoutMillis = 20000
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }
            // we set a default type which we will be able to override later, that some default values(headers etc.)
            // can be added once so they will be used for every request
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}