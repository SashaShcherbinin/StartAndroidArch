package base.compose.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform

interface Mvi<INTENT, EVENT, STATE> {
    val state: Flow<STATE>
    val event: Flow<EVENT>

    fun process(intent: INTENT)
}

interface Intent
interface Effect
interface Event
interface State

interface Processor<INTENT : Intent, EFFECT : Effect, STATE : State> {
    fun process(intent: INTENT, state: STATE): Flow<EFFECT>
}

interface Reducer<EFFECT : Effect, STATE : State> {
    fun reduce(effect: EFFECT, state: STATE): STATE?
}

interface Publisher<EFFECT : Effect, EVENT : Event> {
    fun publish(event: EFFECT): EVENT?
}

open class MviViewModel<INTENT : Intent, EFFECT : Effect, EVENT : Event, STATE : State>(
    defaultState: STATE,
    private val processor: Processor<INTENT, EFFECT, STATE>,
    private val reducer: Reducer<EFFECT, STATE>,
    private val publisher: Publisher<EFFECT, EVENT>,
) : ViewModel(), Mvi<INTENT, EVENT, STATE> {

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(defaultState)
    private val _event: MutableStateFlow<List<EVENT>> = MutableStateFlow(emptyList())

    private val intentJobMap = mutableMapOf<INTENT, Job>()

    override val state = _state.asStateFlow()
    override val event = _event
        .transform { eventList ->
            eventList.isNotEmpty().takeIf { it }?.let {
                _event.value = emptyList()
                eventList.forEach {
                    emit(it)
                }
            }
        }.shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 0,
        )
        .buffer(Int.MAX_VALUE)

    override fun process(intent: INTENT) {
        intentJobMap[intent]?.cancel()
        val job = processor.process(intent, _state.value)
            .onEach { effect: EFFECT ->
                reducer.reduce(effect, _state.value)?.let { _state.value = it }
                publisher.publish(effect)?.let { event ->
                    _event.value += event
                }
            }
            .launchIn(viewModelScope)
        intentJobMap[intent] = job
    }
}
