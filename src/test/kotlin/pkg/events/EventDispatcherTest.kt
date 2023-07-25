package pkg.events

import internal.event.EventHandlerInterface
import internal.event.EventInterface
import internal.event.impl.EventDispatcher
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class EventDispatcherTest {

    private val mockEvent = mockk<EventInterface>()

    @Test
    fun `should validate event register`() {
        // Given
        val eventDispatcher = EventDispatcher()
        every { mockEvent.getName() } returns "test"
        var expected = false

        // When
        eventDispatcher.register("test", object : EventHandlerInterface {
            override fun handle(event: EventInterface) {
                expected = true
            }
        })

        // Then
        eventDispatcher.dispatch(mockEvent)
        assertTrue(expected)
    }
}