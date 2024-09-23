package gvoltr.kmptest.data.api

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import gvoltr.kmptest.data.api.profile.ProfileAPI
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val apiModule = module {

    single<HttpClient> {
        HttpClient() {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true; explicitNulls = false })
            }
        }
    }

    single<Ktorfit> {
        Ktorfit.Builder().httpClient(get<HttpClient>())
            .converterFactories(ResponseConverterFactory())
            .baseUrl("https://api.unstoppabledomains.com/")
            .build()
    }
    single {
        get<Ktorfit>().create<ProfileAPI>()
    }
}