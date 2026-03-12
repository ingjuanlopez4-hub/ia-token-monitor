AI Token Monitor

A high-performance observability and tokenomics system designed to intercept, account for, and analyze Large Language Model (LLM) token consumption in real-time.

Project Objective

This project provides a middleware/backend layer developed entirely in Java, giving organizations complete visibility into the usage and costs of their AI API integrations (OpenAI, Anthropic, Google Gemini, etc.). It guarantees security, quota management per user/API Key, and cost calculation without adding significant latency to the application's main flow.

Architecture and Design

The system is built on SOLID principles and Clean Architecture, prioritizing decoupling and high performance:

Traffic Interception: Utilizes Spring WebFlux `ExchangeFilterFunction` to non-blockingly capture requests and responses.
Strategy Pattern: Facilitates the seamless integration of new LLM providers. Each provider implements its own strategy for token parsing and cost calculation.
Asynchronous Processing: Metrics persistence is delegated to reactive streams, ensuring the AI's response time is strictly unaffected (Thread-safe).

Tech Stack

Language: Java 17+ (Heavy use of `Records` and immutability).
Framework: Spring Boot 3.x (WebFlux).
Persistence:PostgreSQL with TimeScaleDB extension.
Caching: Redis for real-time rate limiting.
Testing: JUnit 5, Mockito, Testcontainers.

Cost Calculation (Tokenomics)

The billing engine calculates the monetary cost of each interaction based on the selected provider's pricing model. The implemented mathematical formula is:

$$C_{total} = (T_{input} \times P_{in}) + (T_{output} \times P_{out})$$

Where:
$C_{total}$: Total cost of the transaction.
$T_{input}$: Number of input tokens (Prompt).
$P_{in}$: Price per input token for the specific model.
$T_{output}$: Number of output tokens (Completion).
$P_{out}$: Price per output token for the specific model.


Core Architecture Examples

Our system heavily relies on immutability and the Strategy pattern to parse and calculate costs dynamically.

1. Immutable Data Structures (Java Records)
We use Java 17 Records to ensure thread-safe data handling across our reactive streams.

java
/
Represents the token usage extracted from an LLM API response.
 /
public record TokenUsage(
    int inputTokens, 
    int outputTokens, 
    String modelName
) {
    public int totalTokens() {
        return inputTokens + outputTokens;
    }
}

2. The Strategy Interface

/
Strategy pattern interface for handling different LLM providers.
 /
public interface LlmProviderStrategy {
    
    /
     Identifies the provider (e.g., "OPENAI", "GEMINI").
     /
    String getProviderId();

    /
     Parses the raw JSON response to extract token usage.
     @param rawResponse The HTTP response body from the LLM.
     @return A thread-safe TokenUsage record.
     /
    TokenUsage extractUsage(String rawResponse);

    /
     Calculates the cost based on the specific model's pricing.
     @param usage The extracted token usage.
     @return The calculated cost in USD.
     /
    double calculateCost(TokenUsage usage);
}

Prerequisites

    JDK 17 or higher.

    Docker and Docker Compose.

    Maven 3.8+.

Installation and Execution

    Clone the repository:

    git clone [https://github.com/ai-token-monitor.git](https://github.com/ai-token-monitor.git)
cd ai-token-monitor

Spin up local infrastructure:

docker-compose up -d

Run the application:

mvn spring-boot:run

Testing

This project enforces strict test coverage. To run the test suite:

mvn clean test
