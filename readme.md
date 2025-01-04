# Crypto Portfolio Tracker

A Spring Boot application for tracking cryptocurrency portfolios with real-time valuations and historical data visualization.

## Features

- **Portfolio Management**
  - Track multiple cryptocurrency holdings
  - View total portfolio value and performance
  - Monitor profit/loss calculations
  - Visualize portfolio distribution

- **Asset Management**
  - Support for major cryptocurrencies (Bitcoin, Ethereum, Solana)
  - Historical price tracking
  - Price history visualization
  - Asset performance metrics

- **Transaction Tracking**
  - Record buy/sell transactions
  - Transaction history
  - Price tracking at time of transaction

- **User Management**
  - Secure authentication
  - Role-based access control (Admin/User roles)
  - Protected portfolio data

## Technology Stack

- **Backend**
  - Java 21
  - Spring Boot 3.4.1
  - Spring Security
  - Spring Data JPA
  - H2 Database

- **Frontend**
  - Thymeleaf
  - TailwindCSS
  - Chart.js

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven
- Git

### Installation

1. Clone the repository
```bash
git clone <repository-url>
cd kppro_2
```

2. Configure application properties
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Edit application.properties with your settings
```

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

### Default Users

The application comes with two default users:
- Admin: username: `admin`, password: `heslo`
- User: username: `user`, password: `heslo`

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── cz/uhk/kppro/
│   │       ├── config/      # Configuration classes
│   │       ├── controller/  # MVC Controllers
│   │       ├── model/      # Entity classes
│   │       ├── repository/ # Data repositories
│   │       └── service/    # Business logic
│   └── resources/
│       ├── static/        # Static assets
│       ├── templates/     # Thymeleaf templates
│       └── application.properties
```

## Key Features Implementation

### Portfolio Tracking
- Real-time portfolio valuation using latest price data
- Historical portfolio value tracking
- Asset distribution visualization
- Profit/Loss calculations

### Price History
- Daily price records for supported cryptocurrencies
- Historical price charts
- Price tracking at transaction times
- Support for multiple currencies

### Transaction Management
- Buy/Sell transaction recording
- Transaction history viewing
- Impact on portfolio calculations

## Security

- Spring Security integration
- Password encryption
- Role-based access control
- Protected API endpoints

## API Documentation

### Portfolio Endpoints
- `GET /portfolio` - View portfolio summary
- `GET /portfolio/{id}` - Get specific portfolio entry
- `POST /portfolio/save` - Create/Update portfolio entry
- `GET /portfolio/delete/{id}` - Delete portfolio entry

### Transaction Endpoints
- `GET /transactions` - View all transactions
- `GET /transactions/{id}` - Get specific transaction
- `POST /transactions/save` - Record new transaction
- `GET /transactions/delete/{id}` - Delete transaction

### Price History Endpoints
- `GET /price-history` - View price history
- `GET /price-history/{id}` - Get specific price record
- `POST /price-history/save` - Add price record

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Spring Boot team for the excellent framework
- TailwindCSS for the styling utilities
- Chart.js for the charting capabilities