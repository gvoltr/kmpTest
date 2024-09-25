package gvoltr.kmptest.view.viewArch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.Syntax

abstract class BaseViewModel<ViewState : Any, SideEffect : Any, UserAction : Any>(
    initialViewState: ViewState
) : ViewModel(), ContainerHost<ViewState, SideEffect> {

    private val _userActions = Channel<UserAction>(Channel.BUFFERED)

    override val container: Container<ViewState, SideEffect> = viewModelScope.container(
        initialState = initialViewState,
        onCreate = {
            intent {
                _userActions.consumeEach { action ->
                    processUserAction(action)
                }
            }

            onCreate()
        }
    )

    protected abstract fun processUserAction(action: UserAction)

    protected open fun onCreate() {}

    fun updateState(state: ViewState) = updateState { state }

    fun updateState(reducer: (ViewState) -> ViewState) = intent {
        reduceState(reducer)
    }

    protected suspend fun Syntax<ViewState, SideEffect>.reduceState(
        reducer: (ViewState) -> ViewState
    ) = reduce {
        reducer(this.state).also { newState ->
            log(elementName = "ViewState", mviElement = newState)
        }
    }

    protected suspend fun Syntax<ViewState, SideEffect>.sendEffect(effect: SideEffect) {
        log(elementName = "SideEffect", mviElement = effect)

        postSideEffect(effect)
    }

    fun sendAction(action: UserAction) = intent {
        log(elementName = "UserAction", mviElement = action)

        _userActions.send(action)
    }

    protected inline fun <reified T : ViewState> ViewState.asStateOrNull(): T? = this as? T

    protected inline fun <reified T : ViewState> Syntax<ViewState, SideEffect>.onState(
        callback: (T) -> Unit,
    ) = state.asStateOrNull<T>()?.let(callback) ?: logUnexpectedState<T>(state)

    protected suspend inline fun <reified T : ViewState> Syntax<ViewState, SideEffect>.reduceOnState(
        noinline reducer: (T) -> ViewState
    ) = reduceState { state ->
        state.asStateOrNull<T>()?.let(reducer) ?: logUnexpectedState<T>(state).run { state }
    }

    protected fun <T : ViewState> logUnexpectedState(state: ViewState) {
        log("Unexpected state", state)
    }

    /**
     * Observe latest value from target [Flow]
     *
     * Note: Collection from [Flow] performed only while [container]'s [Container.stateFlow]
     * or [Container.sideEffectFlow] is observed
     *
     * @param action Called on each item emitted by target [Flow]
     */
    protected fun <T> Flow<T>.observeLatest(
        action: suspend Syntax<ViewState, SideEffect>.(T) -> Unit
    ) = intent {
        repeatOnSubscription {
            collectLatest { action(it) }
        }
    }

    /**
     * Observe new emitted value from [NavResultProvider] for specified [key]
     *
     * @param key Key used for identifying navigation result emitted by [NavResultProvider]
     * @param action Called on each item emitted by [NavResultProvider]
     */
    protected fun NavResultProvider.observeLatestNavResult(
        key: String,
        action: suspend Syntax<ViewState, SideEffect>.() -> Unit
    ) = intent {
        resultFor(key).collectLatest { action() }
    }

    /**
     * Observe latest typed emitted value from [NavResultProvider] for specified [key]
     *
     * @param key Key used for identifying navigation result emitted by [NavResultProvider]
     * @param action Called on each item emitted by [NavResultProvider]
     */
    protected inline fun <reified T : Any> NavResultProvider.observeLatestTypedNavResult(
        key: String,
        noinline action: suspend Syntax<ViewState, SideEffect>.(T) -> Unit
    ) = intent {
        typedResultFor<T>(key).collectLatest { action(it) }
    }

    private fun log(elementName: String, mviElement: Any) {
        // For now only class name is logged as there may be a lot of data inside and it may be private.
        println("VM: $elementName: $mviElement")
    }
}

@Composable
public fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectAsState(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED
): State<STATE> {
    val stateFlow = container.stateFlow
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    val stateFlowLifecycleAware = remember(stateFlow, lifecycleOwner) {
        stateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, lifecycleState)
    }

    // Need to access the initial value to convert to State - collectAsState() suppresses this lint warning too
    val initialValue = stateFlow.value
    return stateFlowLifecycleAware.collectAsState(initialValue)
}

@Composable
public fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectSideEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit)
) {
    val sideEffectFlow = container.sideEffectFlow
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    LaunchedEffect(sideEffectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffectFlow.collect { sideEffect(it) }
        }
    }
}