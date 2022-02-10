[![Build](https://github.com/fintecy/ecb-client/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/fintecy/ecb-client/actions/workflows/gradle.yml)

# European Central Bank (ECB) Client

Java client based on new HttpClient (java 11+)

## Usage
### Add dependency
https://mvnrepository.com/artifact/org.fintecy.md/ecb-client/1.0.0
#### Gradle
```
implementation 'org.fintecy.md:ecb-client:1.0.0'
```
#### Maven
```
<dependency>
    <groupId>org.fintecy.md</groupId>
    <artifactId>ecb-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Simple client creation
```
EcbApi client = EcbClient.api();
```
### Complex client configuration
```
var client = ecbClient()
    .useClient(HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .priority(10)
        .connectTimeout(Duration.ofMillis(500))
        .executor(Executors.newSingleThreadExecutor())
        .build())
    .with(CircuitBreaker.ofDefaults())
    .with(RateLimiter.smoothBuilder(Duration.ofMillis(100))
        .build())
    .with(RetryPolicy.ofDefaults())
    .with(Timeout.of(Duration.ofMillis(400)))
    .rootPath("https://www.ecb.europa.eu/stats/eurofxref") -- just to use stub in tests
    .build();
```

### Get latest rates
```
var client = EcbClient.api();
int rates = client.rates();
```

### Get supported currencies
```
var client = EcbClient.api();
var currencies = client.supportedCurrencies();
```

## Dependencies
- Java 11+
- FailSafe
- Slf4j api
- jackson
- WireMock (tests)
- Junit5 (tests)

## Author
Anton Batiaev <anton@batiaev.com>
