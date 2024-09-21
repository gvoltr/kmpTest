package gvoltr.kmptest.data.api

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import org.koin.dsl.module

val apiModule = module {
    single {
        val myClient = HttpClient()
        val ktorfit = Ktorfit.Builder().httpClient(myClient).build()

    }
}