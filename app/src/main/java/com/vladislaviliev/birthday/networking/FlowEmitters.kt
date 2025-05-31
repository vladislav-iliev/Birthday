package com.vladislaviliev.birthday.networking

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.yield

/** Allow same-thread concurrent operations to see the emission.
 *
 * Necessary for single-thread tests. Example scenario:
 * 1. Observer of a StateFlow suspends waiting for updates.
 * 2. The network client emits.
 * 3. The Observer is notified an emission happened, and is
 *    scheduled on the thread for resumption.
 * 4. The network client emits again without suspending (as StateFlow
 *    doesn't suspend on buffer overflows).
 * 5. The Observer has now lost the emission by the time it becomes
 *    the running coroutine.
 **/
suspend fun FlowCollector<State>.emitAndYield(state: State) {
    emit(state)
    yield()
}