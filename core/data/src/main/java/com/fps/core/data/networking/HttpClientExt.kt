package com.fps.core.data.networking

import com.fps.core.data.BuildConfig
import com.fps.core.domain.util.DataError
import com.fps.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.CancellationException
import kotlinx.serialization.SerializationException

/** This is a GET http call, which we call from repositories, data sources etc. in order to execute
 *  the actual http get call. This passes (sort of filters) the actual api call to the other function
 *  of safeCall, which in its turn parses the response with the responseToResult
 **/
suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

/**
 * This is  an HTTP Call, that is wrapped inside of the try catch block and catches some additional errors that are
 * not related to the actual response, but there are related to the network communication. These kind of errors might
 * be the internet connectivity, some serialization error(some fields might be null or changed etc. so we do not have
 * to make the Api Response fields all nullables).
 * With the 'execute' lambda, we are passing to safeCall the actual network http call that we want to execute and get an
 * HttpResponse as a result
 **/
suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

/**
 * This function handles the HTTP status code that gets returned from the request, to a corresponding DataError}
 * inline & reified are needed to know the Generic class info during compile time.
 *
 * With this wrapping, we are mapping the response to a Domain level Error class, which we'll use across our app
 * without coupling the domain layer w/ the data layer
 */
suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Network> {
    return when (response.status.value) {
        // we now wrap the response if it's a success or an error, into a Result Success/Error
        in 200..299 -> Result.Success(response.body<T>())
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

// this function will construct the whole route, having the BASE_URL plus the route
fun constructRoute(route: String): String {
    return when {
        route.contains((BuildConfig.BASE_URL)) -> route // if it is whole route, just return it
        route.startsWith("/") -> BuildConfig.BASE_URL + route // if it is just a specific route, construct it by adding base_url
        else -> BuildConfig.BASE_URL + "/$route"
    }
}