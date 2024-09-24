package gvoltr.kmptest.view.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.CoroutineScope

@Composable
fun <T> rememberMutableStateOf(
    value: T,
    key: Any? = null,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
) = remember(key) { mutableStateOf(value, policy) }

@Composable
fun rememberFocusRequester() = remember { FocusRequester() }

@Composable
fun OneTime(callback: suspend CoroutineScope.() -> Unit) = LaunchedEffect(Unit, callback)

@Composable
fun OneTime(key: Any?, callback: suspend CoroutineScope.() -> Unit) = LaunchedEffect(key, callback)