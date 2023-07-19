package pkg.events

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import java.util.*
import kotlin.test.assertTrue

internal class EventDispatcherTest {

    private val mockEvent = mockk<Event>()

    @Test
    fun `should validate event register`() {
        // Given
        val eventDispatcher = EventDispatcher()
        every { mockEvent.getName() } returns "test"
        var expected = false

        // When
        eventDispatcher.register("test", object : EventHandlerInterface {
            override fun handle(event: Event) {
                expected = true
            }
        })

        // Then
        eventDispatcher.dispatch(mockEvent)
        assertTrue(expected)
    }
}