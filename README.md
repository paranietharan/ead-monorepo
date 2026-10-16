# EAD E-Commerce

Microservices e-commerce platform (EAD assignment) in a single mono repo.

```
.
├── docker-compose.yml     # whole stack in one command
├── .env.example           # copy to .env
├── frontend/              # React (CRA + MUI) — served by nginx in Docker
└── services/
    ├── config-server/     # Spring Cloud Config (native, :8888)
    ├── discovery/         # Eureka server (:8761)
    ├── gateway/           # Spring Cloud Gateway + JWT filter (:8222)
    ├── customer/          # Auth / users — MySQL (:8090)
    ├── product/           # Products, categories, cart, wishlist — MySQL (:8070)
    ├── order/             # Orders — MySQL (:7080)
    └── payment/           # Stripe payments — Node/Express + MongoDB (:8010)
```

## Run with Docker

Requires Docker with Compose v2.

```bash
cp .env.example .env      # then edit the passwords / Stripe key / mail settings
docker compose up -d --build
```

| What | URL |
| --- | --- |
| Frontend | http://localhost:3000 |
| API gateway | http://localhost:8222 (also proxied at http://localhost:3000/api/) |
| Eureka dashboard | http://localhost:8761 |
| MySQL customer DB (from host) | `localhost:3307` (`ead_user_db`), user `root` |
| MySQL product DB (from host) | `localhost:3308` (`productdb`), user `root` |
| MySQL order DB (from host) | `localhost:3309` (`orders`), user `root` |
| MongoDB (from host) | `localhost:27018` |

Each Spring data service (customer, product, order) has its own MySQL container and volume.
Startup is ordered by health checks: databases → config-server → discovery → services → gateway → frontend.
The first build downloads Maven/npm dependencies and takes a few minutes.

```bash
docker compose logs -f gateway     # follow one service
docker compose down                # stop (keeps data)
docker compose down -v             # stop and wipe databases
```

## Configuration

All settings come from `.env` (see `.env.example`):

| Variable | Purpose |
| --- | --- |
| `MYSQL_ROOT_PASSWORD` | Root password for the three MySQL containers (customer / product / order) |
| `MONGO_USER`, `MONGO_PASSWORD` | MongoDB credentials for the payment service |
| `JWT_SECRET` | Base64 HMAC key shared by gateway and customer service |
| `STRIPE_SECRET_KEY` | Stripe secret key for the payment service |
| `MAIL_*` | SMTP settings for the customer service |
| `FRONTEND_ORIGIN` | Origin allowed by gateway CORS |

The config-server files in `services/config-server/src/main/resources/configurations/`
use `${VAR:default}` placeholders; the defaults are the original `localhost` values,
so running the services directly on your machine still works.

## Running without Docker

Start MySQL (`root`/`root`, with schemas `ead_user_db`, `productdb`, `orders`) and MongoDB, then in order:
`config-server` → `discovery` → `customer`, `product`, `order`, `gateway` (`./mvnw spring-boot:run` in each),
`services/payment` (`npm install && npm start`, needs `config/config.env` with `DB_LOCAL_URL`, `STRIPE_SECRET_KEY`, `PORT`),
and `frontend` (`npm install && npm start`).
