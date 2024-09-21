package gvoltr.kmptest.viewArch

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class NavResultProvider {

    private val events = MutableSharedFlow<Pair<String, Any>>()

    /**
     * Sending result for a [key] listener. If there is no listener for a [key] result won't be delivered or cached.
     */
    suspend fun sendResult(key: String, data: Any) {
        val event = Pair(key, data)
        println("Sending result {$key, $data}")
        events.emit(event)
    }

    fun resultFor(key: String): Flow<Any> = events
        .filter { it.first == key }
        .map { it.second }

    inline fun <reified T : Any> typedResultFor(key: String) = resultFor(key)
        .mapNotNull { it as? T }
}