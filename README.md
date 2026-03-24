Use Case 11: Concurrent Booking Simulation
Goal

Demonstrate how concurrent access to shared resources can lead to inconsistent system state and show how synchronization ensures correctness under multi-user conditions.

Actor

Multiple Guests – submit booking requests concurrently.
Concurrent Booking Processor – processes booking requests in a multi-threaded environment.

Flow
Multiple guests submit booking requests simultaneously.
Requests are added to a shared booking queue.
Threads retrieve requests using synchronized access.
Room allocation and inventory updates are performed inside critical sections.
The system completes allocations without conflicts or inconsistencies.
Key Concepts Used

Race Conditions
Race conditions occur when multiple threads access and modify shared data simultaneously. The final system state becomes dependent on execution timing rather than logic.

Thread Safety
Thread safety ensures that shared resources behave correctly when accessed by multiple threads. This is critical in systems handling concurrent user actions.

Shared Mutable State
The booking queue and inventory are shared across threads. Uncontrolled access to shared mutable data can corrupt system state.

Critical Sections
Critical sections are blocks of code that must be executed by only one thread at a time. Synchronization ensures exclusive access to these sections.

Synchronized Access
Synchronization mechanisms are used to protect shared resources. This prevents interleaving operations that could lead to double allocation.

Concurrency vs. Parallelism
Concurrency focuses on correctness when tasks overlap in time. This use case emphasizes correctness over performance optimization.

Key Requirements
Simulate multiple booking requests occurring at the same time.
Use shared data structures for booking requests and inventory.
Ensure inventory updates are performed in a thread-safe manner.
Prevent double allocation under concurrent execution.
Maintain consistent system state under load.
Key Benefits
Demonstrates real-world concurrent booking behavior
Prevents double allocation through synchronization
Maintains correct inventory under simultaneous access
Introduces thread safety in a practical scenario
Drawbacks of Previous Use Case
Previous use case handled cancellation and rollback in a single-threaded flow.
It did not address multiple users acting at the same time.
Shared booking and inventory state could become inconsistent under concurrent access without synchronization.